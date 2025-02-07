package ru.develgame.gpsserver.backend.integration.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
@ComponentScan({"ru.develgame.gpsserver.backend.security.jwt.provider",
                "ru.develgame.gpsserver.backend.security.jwt.util",
                "ru.develgame.gpsserver.backend.security.jwt.auth",
                "ru.develgame.gpsserver.backend.service"})
public class TestServiceConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
