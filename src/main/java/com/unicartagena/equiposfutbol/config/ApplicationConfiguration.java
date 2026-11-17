package com.unicartagena.equiposfutbol.config;

import com.unicartagena.equiposfutbol.application.service.EquipoService;
import com.unicartagena.equiposfutbol.application.service.mapper.EquipoDomainMapper;
import com.unicartagena.equiposfutbol.domain.ports.out.EquipoRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

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
}
