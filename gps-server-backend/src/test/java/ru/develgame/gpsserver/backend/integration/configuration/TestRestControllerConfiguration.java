package ru.develgame.gpsserver.backend.integration.configuration;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import ru.develgame.gpsserver.backend.configuration.errorhandling.RestExceptionHandlingConfiguration;

@TestConfiguration
@ComponentScan({"ru.develgame.gpsserver.backend.controller"})
@ImportAutoConfiguration(RestExceptionHandlingConfiguration.class)
public class TestRestControllerConfiguration {
}
