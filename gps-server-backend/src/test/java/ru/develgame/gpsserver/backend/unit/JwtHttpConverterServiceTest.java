package ru.develgame.gpsserver.backend.unit;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.develgame.gpsserver.backend.security.jwt.util.JwtHttpConverterService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.develgame.gpsserver.backend.security.jwt.util.JwtHttpConverterService.AUTH_HEADER;
import static ru.develgame.gpsserver.backend.security.jwt.util.JwtHttpConverterService.TOKEN_PREFIX;

class JwtHttpConverterServiceTest {
    @Test
    void should_extractJwtToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(AUTH_HEADER)).thenReturn(TOKEN_PREFIX + "testjwt");

        String actual = JwtHttpConverterService.extractJwtToken(request);
        Assertions.assertEquals("testjwt", actual);
    }

    @Test
    void should_extractJwtToken_whenNoPrefix() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(AUTH_HEADER)).thenReturn( "testjwt");

        String actual = JwtHttpConverterService.extractJwtToken(request);
        Assertions.assertNull(actual);
    }
}
