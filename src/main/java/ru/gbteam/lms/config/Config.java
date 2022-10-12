package ru.gbteam.lms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gbteam.lms.converter.RoleDtoByStringConverter;
import ru.gbteam.lms.repository.RoleRepository;

@Configuration
public class Config {

    @Bean
    public RoleDtoByStringConverter roleDtoByStringConverter(RoleRepository roleRepository) {
        return new RoleDtoByStringConverter(roleRepository);
    }
}
