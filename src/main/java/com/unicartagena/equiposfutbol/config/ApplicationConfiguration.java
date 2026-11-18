package com.unicartagena.equiposfutbol.config;

import com.unicartagena.equiposfutbol.application.service.EquipoService;
import com.unicartagena.equiposfutbol.application.service.mapper.EquipoDomainMapper;
import com.unicartagena.equiposfutbol.domain.ports.out.EquipoRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Clock;
import com.unicartagena.equiposfutbol.application.service.UserService;
import com.unicartagena.equiposfutbol.application.service.mapper.UserDomainMapper;
import com.unicartagena.equiposfutbol.domain.ports.out.NotificationPort;
import com.unicartagena.equiposfutbol.domain.ports.out.PasswordEncoderPort;
import com.unicartagena.equiposfutbol.domain.ports.out.PasswordGeneratorPort;
import com.unicartagena.equiposfutbol.domain.ports.out.UserRepositoryPort;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    public EquipoDomainMapper equipoDomainMapper() {
        return new EquipoDomainMapper();
    }

    @Bean
    public EquipoService equipoService(
            EquipoRepositoryPort equipoRepository,
            Clock clock,
            EquipoDomainMapper equipoDomainMapper) {
        return new EquipoService(equipoRepository, clock, equipoDomainMapper);
    }

    @Bean
    UserDomainMapper userDomainMapper() {
        return new UserDomainMapper();
    }

    @Bean
    UserService userService(
            UserRepositoryPort userRepository,
            PasswordEncoderPort passwordEncoder,
            PasswordGeneratorPort passwordGenerator,
            NotificationPort notification,
            Clock clock,
            UserDomainMapper userDomainMapper) {
        return new UserService(
                userRepository, passwordEncoder, passwordGenerator, notification, clock, userDomainMapper);
    }
}
