package ru.develgame.gpsserver.backend.security.jwt.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Getter
@Service
public class JwtSecretManagerService {
    private final String secret;

    public JwtSecretManagerService(@Value("${jwt.secret}") String secret) {
        this.secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }
}
