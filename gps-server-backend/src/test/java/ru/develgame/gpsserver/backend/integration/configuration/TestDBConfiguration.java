package ru.develgame.gpsserver.backend.integration.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@TestConfiguration
@EntityScan("ru.develgame.gpsserver.backend.entity")
@EnableJpaRepositories("ru.develgame.gpsserver.backend.repository")
public class TestDBConfiguration {
}
