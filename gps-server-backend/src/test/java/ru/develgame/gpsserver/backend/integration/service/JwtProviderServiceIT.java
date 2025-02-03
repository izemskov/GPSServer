package ru.develgame.gpsserver.backend.integration.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.develgame.gpsserver.backend.dto.auth.AuthRequestDto;
import ru.develgame.gpsserver.backend.entity.GPSUser;
import ru.develgame.gpsserver.backend.exception.AuthFailedException;
import ru.develgame.gpsserver.backend.exception.InvalidArgumentException;
import ru.develgame.gpsserver.backend.integration.configuration.BaseServiceIT;
import ru.develgame.gpsserver.backend.repository.GPSUserRepository;
import ru.develgame.gpsserver.backend.security.jwt.provider.JwtProviderService;
import ru.develgame.gpsserver.backend.security.jwt.util.JwtSecretManagerService;

class JwtProviderServiceIT extends BaseServiceIT {
    @Autowired
    private JwtProviderService jwtProviderService;

    @Autowired
    private GPSUserRepository gpsUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtSecretManagerService jwtSecretManagerService;

    @AfterEach
    void cleanUp() {
        gpsUserRepository.deleteAll();
    }

    @Test
    void should_throwValidateException_whenRequestNull() {
        InvalidArgumentException actual = Assertions.assertThrowsExactly(InvalidArgumentException.class,
                () -> jwtProviderService.checkAuthAndCreateToken(null));

        Assertions.assertEquals("Request cannot be empty", actual.getMessage());
    }

    @Test
    void should_throwValidateException_whenLoginNullOrBlank() {
        InvalidArgumentException actual = Assertions.assertThrowsExactly(InvalidArgumentException.class,
                () -> jwtProviderService.checkAuthAndCreateToken(AuthRequestDto.builder().build()));

        Assertions.assertEquals("Login cannot be empty", actual.getMessage());

        actual = Assertions.assertThrowsExactly(InvalidArgumentException.class,
                () -> jwtProviderService.checkAuthAndCreateToken(AuthRequestDto.builder()
                        .login(" ")
                        .build()));

        Assertions.assertEquals("Login cannot be empty", actual.getMessage());
    }

    @Test
    void should_throwValidateException_whenPasswordNullOrBlank() {
        InvalidArgumentException actual = Assertions.assertThrowsExactly(InvalidArgumentException.class,
                () -> jwtProviderService.checkAuthAndCreateToken(AuthRequestDto.builder()
                        .login("login")
                        .build()));

        Assertions.assertEquals("Password cannot be empty", actual.getMessage());

        actual = Assertions.assertThrowsExactly(InvalidArgumentException.class,
                () -> jwtProviderService.checkAuthAndCreateToken(AuthRequestDto.builder()
                        .login("login")
                        .password(" ")
                        .build()));

        Assertions.assertEquals("Password cannot be empty", actual.getMessage());
    }

    @Test
    void should_throwValidateException_whenExpireInDaysZeroOrLess() {
        InvalidArgumentException actual = Assertions.assertThrowsExactly(InvalidArgumentException.class,
                () -> jwtProviderService.checkAuthAndCreateToken(AuthRequestDto.builder()
                        .login("login")
                        .password("pwd")
                        .expiredDays(0)
                        .build()));

        Assertions.assertEquals("Expires must be more than zero", actual.getMessage());

        actual = Assertions.assertThrowsExactly(InvalidArgumentException.class,
                () -> jwtProviderService.checkAuthAndCreateToken(AuthRequestDto.builder()
                        .login("login")
                        .password("pwd")
                        .expiredDays(-1)
                        .build()));

        Assertions.assertEquals("Expires must be more than zero", actual.getMessage());
    }

    @Test
    void should_throwAuthFailedException_whenUserNotFound() {
        GPSUser gpsUser = new GPSUser();
        gpsUser.setName("login123");
        gpsUser.setPwd(passwordEncoder.encode("pwd"));
        gpsUserRepository.save(gpsUser);

        Assertions.assertThrowsExactly(AuthFailedException.class,
                () -> jwtProviderService.checkAuthAndCreateToken(AuthRequestDto.builder()
                        .login("login")
                        .password("pwd")
                        .expiredDays(1)
                        .build()));
    }

    @Test
    void should_throwAuthFailedException_whenPasswordIncorrect() {
        GPSUser gpsUser = new GPSUser();
        gpsUser.setName("login");
        gpsUser.setPwd(passwordEncoder.encode("pwd"));
        gpsUserRepository.save(gpsUser);

        Assertions.assertThrowsExactly(AuthFailedException.class,
                () -> jwtProviderService.checkAuthAndCreateToken(AuthRequestDto.builder()
                        .login("login")
                        .password("test")
                        .expiredDays(1)
                        .build()));
    }

    @Test
    void should_createToken() {
        GPSUser gpsUser = new GPSUser();
        gpsUser.setName("login");
        gpsUser.setPwd(passwordEncoder.encode("pwd"));
        gpsUser = gpsUserRepository.save(gpsUser);

        String actual = jwtProviderService.checkAuthAndCreateToken(AuthRequestDto.builder()
                .login("login")
                .password("pwd")
                .expiredDays(2)
                .build());

        Claims actualClaims = Jwts.parser()
                .setSigningKey(jwtSecretManagerService.getSecret())
                .parseClaimsJws(actual)
                .getBody();

        Assertions.assertEquals(gpsUser.getId(), Long.parseLong(actualClaims.getSubject()));
        Assertions.assertEquals(actualClaims.getIssuedAt().getTime() + 2L * 24L * 3600L * 1000L,
                actualClaims.getExpiration().getTime());
    }
}
