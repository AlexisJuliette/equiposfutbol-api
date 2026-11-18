package com.unicartagena.equiposfutbol.domain.ports.out;

import java.util.List;
import java.util.Optional;
import com.unicartagena.equiposfutbol.domain.models.Usuario;
import com.unicartagena.equiposfutbol.domain.valueobjects.EmailAddress;
import com.unicartagena.equiposfutbol.domain.valueobjects.UserId;
import com.unicartagena.equiposfutbol.domain.valueobjects.Username;

public interface UserRepositoryPort {
    void save(Usuario usuario);

    Optional<Usuario> findById(UserId userId);

    Optional<Usuario> findByUsername(Username username);

    Optional<Usuario> findByEmail(EmailAddress email);

    List<Usuario> findAll();

    List<Usuario> findByNombreContaining(String fragmento);

    void deleteById(UserId userId);    
}
