package ru.develgame.gpsserver.backend.integration.configuration;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import ru.develgame.gpsserver.backend.configuration.errorhandling.RestExceptionHandlingConfiguration;

@TestConfiguration
@ComponentScan({"ru.develgame.gpsserver.backend.controller",
                "ru.develgame.gpsserver.backend.mapper"})
@ImportAutoConfiguration(RestExceptionHandlingConfiguration.class)
public class TestRestControllerConfiguration {
}
