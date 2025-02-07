package ru.develgame.gpsserver.backend.integration.service;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.develgame.gpsserver.backend.dto.auth.AuthRequestDto;
import ru.develgame.gpsserver.backend.entity.GPSDisableToken;
import ru.develgame.gpsserver.backend.entity.GPSUser;
import ru.develgame.gpsserver.backend.integration.configuration.BaseServiceIT;
import ru.develgame.gpsserver.backend.repository.GPSDisableTokenRepository;
import ru.develgame.gpsserver.backend.repository.GPSUserRepository;
import ru.develgame.gpsserver.backend.security.jwt.auth.JwtAuthService;
import ru.develgame.gpsserver.backend.security.jwt.provider.JwtProviderService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.develgame.gpsserver.backend.security.jwt.util.JwtHttpConverterService.AUTH_HEADER;
import static ru.develgame.gpsserver.backend.security.jwt.util.JwtHttpConverterService.TOKEN_PREFIX;

class JwtAuthServiceIT extends BaseServiceIT {
    @Autowired
    private JwtAuthService jwtAuthService;

    @Autowired
    private GPSDisableTokenRepository gpsDisableTokenRepository;

    @Autowired
    private JwtProviderService jwtProviderService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GPSUserRepository gpsUserRepository;

    @AfterEach
    void cleanUp() {
        gpsUserRepository.deleteAll();
        gpsDisableTokenRepository.deleteAll();
    }

    @Test
    void should_checkNullOrEmptyToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        JwtException actual = Assertions.assertThrowsExactly(JwtException.class,
                () -> jwtAuthService.getAuth(null));
        Assertions.assertEquals("Token not found", actual.getMessage());
        actual = Assertions.assertThrowsExactly(JwtException.class, () -> jwtAuthService.getAuth(request));
        Assertions.assertEquals("Token not found", actual.getMessage());
    }

    @Test
    void should_checkBadToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(AUTH_HEADER)).thenReturn("testjwt");

        JwtException actual = Assertions.assertThrowsExactly(JwtException.class,
                () -> jwtAuthService.getAuth(request));
        Assertions.assertEquals("Token not found", actual.getMessage());
    }

    @Test
    void should_disabledToken() {
        GPSDisableToken gpsDisableToken = new GPSDisableToken();
        gpsDisableToken.setToken("testjwt");
        gpsDisableToken.setExpiredAt(2000000L);
        gpsDisableTokenRepository.save(gpsDisableToken);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(AUTH_HEADER)).thenReturn(TOKEN_PREFIX + "testjwt");

        JwtException actual = Assertions.assertThrowsExactly(JwtException.class,
                () -> jwtAuthService.getAuth(request));
        Assertions.assertEquals("JWT token is disabled", actual.getMessage());
    }

    @Test
    void should_createAuth() {
        GPSUser gpsUser = new GPSUser();
        gpsUser.setName("test");
        gpsUser.setPwd(passwordEncoder.encode("pwd"));
        gpsUser = gpsUserRepository.save(gpsUser);

        String token = jwtProviderService.checkAuthAndCreateToken(AuthRequestDto.builder()
                .login("test")
                .password("pwd")
                .expireDays(1)
                .build());

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(AUTH_HEADER)).thenReturn(TOKEN_PREFIX + token);

        Authentication actual = jwtAuthService.getAuth(request);

        Assertions.assertEquals(gpsUser.getName(), actual.getName());
        Assertions.assertEquals(gpsUser.getId(), ((GPSUser) actual.getPrincipal()).getId());
    }
}
