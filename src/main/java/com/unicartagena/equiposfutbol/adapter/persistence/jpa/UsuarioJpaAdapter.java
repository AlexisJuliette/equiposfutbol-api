package com.unicartagena.equiposfutbol.adapter.persistence.jpa;

import com.unicartagena.equiposfutbol.domain.models.Usuario;
import com.unicartagena.equiposfutbol.domain.ports.out.UserRepositoryPort;
import com.unicartagena.equiposfutbol.domain.valueobjects.EmailAddress;
import com.unicartagena.equiposfutbol.domain.valueobjects.UserId;
import com.unicartagena.equiposfutbol.domain.valueobjects.Username;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UsuarioJpaAdapter implements UserRepositoryPort {

    private final UsuarioJpaRepository jpaRepository;
    private final UsuarioPersistenceMapper mapper;

    public UsuarioJpaAdapter(UsuarioJpaRepository jpaRepository, UsuarioPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(Usuario usuario) {
        jpaRepository.save(mapper.toEntity(usuario));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findById(UserId userId) {
        return jpaRepository.findById(userId.value()).map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findByUsername(Username username) {
        return jpaRepository.findByUsername(username.value()).map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findByEmail(EmailAddress email) {
        return jpaRepository.findByEmail(email.value()).map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return mapper.toDomainList(jpaRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findByNombreContaining(String fragmento) {
        return mapper.toDomainList(jpaRepository.findByNombreContainingIgnoreCase(fragmento));
    }

    @Override
    public void deleteById(UserId userId) {
        jpaRepository.deleteById(userId.value());
    }
}