package ru.develgame.gpsserver.backend.integration.configuration;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Import(TestDBConfiguration.class)
@TestPropertySource(locations = "/application-test.yml")
@ActiveProfiles("test")
public class BaseRepositoryIT {
}
