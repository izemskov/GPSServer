package ru.develgame.gpsserver.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class GPSServerBackend {
    public static void main(String[] args) {
        SpringApplication.run(GPSServerBackend.class, args);
    }
}
