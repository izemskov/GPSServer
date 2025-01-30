package ru.develgame.gpsserver.backend.security.jwt.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JwtHttpConverterService {
    public static final String AUTH_HEADER = "X-Authorization";
    public static final String TOKEN_PREFIX = "Bearer_" ;

    public String extractJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(AUTH_HEADER);
        if (jwtToken == null || !jwtToken.startsWith(TOKEN_PREFIX)) {
            return null;
        }
        return jwtToken.substring(TOKEN_PREFIX.length());
    }
}
