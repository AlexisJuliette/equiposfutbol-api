package com.unicartagena.equiposfutbol.application.port.in;

import java.time.Instant;
import java.util.List;

import com.unicartagena.equiposfutbol.application.service.dto.commands.CreateUserCommand;
import com.unicartagena.equiposfutbol.application.service.dto.commands.UpdateUserCommand;
import com.unicartagena.equiposfutbol.application.service.dto.response.UserResult;
import com.unicartagena.equiposfutbol.domain.valueobjects.UserId;

public interface ManageUsersUseCase {
    UserResult createUser(CreateUserCommand command);

    UserResult getUser(UserId userId);

    List<UserResult> listUsers();

    UserResult updateUser(UserId userId, UpdateUserCommand command);

    void deleteUser(UserId userId);

    List<UserResult> reportePorNombre(String fragmento);

    List<UserResult> reportePorFechaRegistro(Instant desde, Instant hasta);
}   
