package com.unicartagena.equiposfutbol.application.service;

import com.unicartagena.equiposfutbol.application.port.in.AuthUseCase;
import com.unicartagena.equiposfutbol.application.port.in.ManageUsersUseCase;
import com.unicartagena.equiposfutbol.application.service.dto.commands.CreateUserCommand;
import com.unicartagena.equiposfutbol.application.service.dto.commands.RecoverPasswordCommand;
import com.unicartagena.equiposfutbol.application.service.dto.commands.UpdateUserCommand;
import com.unicartagena.equiposfutbol.application.service.dto.queries.LoginQuery;
import com.unicartagena.equiposfutbol.application.service.dto.response.UserResult;
import com.unicartagena.equiposfutbol.application.service.mapper.UserDomainMapper;
import com.unicartagena.equiposfutbol.domain.exceptions.DuplicateUserException;
import com.unicartagena.equiposfutbol.domain.exceptions.InvalidCredentialsException;
import com.unicartagena.equiposfutbol.domain.exceptions.UserNotFoundException;
import com.unicartagena.equiposfutbol.domain.models.Usuario;
import com.unicartagena.equiposfutbol.domain.ports.out.NotificationPort;
import com.unicartagena.equiposfutbol.domain.ports.out.PasswordEncoderPort;
import com.unicartagena.equiposfutbol.domain.ports.out.PasswordGeneratorPort;
import com.unicartagena.equiposfutbol.domain.ports.out.UserRepositoryPort;
import com.unicartagena.equiposfutbol.domain.valueobjects.EmailAddress;
import com.unicartagena.equiposfutbol.domain.valueobjects.PasswordHash;
import com.unicartagena.equiposfutbol.domain.valueobjects.RawPassword;
import com.unicartagena.equiposfutbol.domain.valueobjects.UserId;
import java.time.Clock;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class UserService implements ManageUsersUseCase, AuthUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final PasswordGeneratorPort passwordGenerator;
    private final NotificationPort notification;
    private final Clock clock;
    private final UserDomainMapper userMapper;

    public UserService(
            UserRepositoryPort userRepository,
            PasswordEncoderPort passwordEncoder,
            PasswordGeneratorPort passwordGenerator,
            NotificationPort notification,
            Clock clock,
            UserDomainMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordGenerator = passwordGenerator;
        this.notification = notification;
        this.clock = clock;
        this.userMapper = userMapper;
    }

    @Override
    public UserResult createUser(CreateUserCommand command) {
        verificarUsernameLibre(command.username());
        verificarEmailLibre(command.email(), null);
        Instant ahora = Instant.now(clock);
        Usuario usuario = Usuario.create(
                UserId.random(),
                command.username(),
                passwordEncoder.encode(command.password()),
                command.nombre(),
                command.email(),
                ahora);
        userRepository.save(usuario);
        return userMapper.toResult(usuario);
    }

    @Override
    public UserResult getUser(UserId userId) {
        return userMapper.toResult(buscarUsuario(userId));
    }

    @Override
    public List<UserResult> listUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResult)
                .sorted(Comparator.comparing(UserResult::createdAt))
                .toList();
    }

    @Override
    public UserResult updateUser(UserId userId, UpdateUserCommand command) {
        Usuario actual = buscarUsuario(userId);
        verificarEmailLibre(command.email(), userId);
        Usuario actualizado = actual.update(command.nombre(), command.email(), Instant.now(clock));
        userRepository.save(actualizado);
        return userMapper.toResult(actualizado);
    }

    @Override
    public void deleteUser(UserId userId) {
        buscarUsuario(userId);
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserResult> reportePorNombre(String fragmento) {
        return userMapper.toResults(userRepository.findByNombreContaining(fragmento));
    }

    @Override
    public List<UserResult> reportePorFechaRegistro(Instant desde, Instant hasta) {
        return userRepository.findAll().stream()
                .filter(usuario -> !usuario.getCreatedAt().isBefore(desde))
                .filter(usuario -> !usuario.getCreatedAt().isAfter(hasta))
                .map(userMapper::toResult)
                .sorted(Comparator.comparing(UserResult::createdAt))
                .toList();
    }

    @Override
    public UserResult login(LoginQuery query) {
        Usuario usuario = userRepository.findByUsername(query.username())
                .orElseThrow(InvalidCredentialsException::new);
        if (!passwordEncoder.matches(query.password(), usuario.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }
        return userMapper.toResult(usuario);
    }

    @Override
    public void recuperarPassword(RecoverPasswordCommand command) {
        userRepository.findByEmail(command.email()).ifPresent(usuario -> {
            RawPassword passwordTemporal = passwordGenerator.generate();
            PasswordHash nuevoHash = passwordEncoder.encode(passwordTemporal);
            userRepository.save(usuario.changePassword(nuevoHash, Instant.now(clock)));
            notification.enviarPasswordTemporal(
                    usuario.getEmail(), usuario.getUsername().value(), passwordTemporal.value());
        });
    }

    private Usuario buscarUsuario(UserId userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    private void verificarEmailLibre(EmailAddress email, UserId idPermitido) {
        userRepository.findByEmail(email)
                .filter(usuario -> !Objects.equals(usuario.getId(), idPermitido))
                .ifPresent(usuario -> {
                    throw new DuplicateUserException();
                });
    }

    private void verificarUsernameLibre(com.unicartagena.equiposfutbol.domain.valueobjects.Username username) {
        userRepository.findByUsername(username).ifPresent(usuario -> {
            throw new DuplicateUserException();
        });
    }
}