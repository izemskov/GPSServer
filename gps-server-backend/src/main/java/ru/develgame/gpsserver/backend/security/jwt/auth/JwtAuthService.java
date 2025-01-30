package ru.develgame.gpsserver.backend.security.jwt.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.develgame.gpsserver.backend.security.jwt.util.JwtHttpConverterService;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class JwtAuthService {
//    private final JwtDisableService jwtDisableService;
//    private final JwtProviderService jwtProviderService;
//    private final JwtSecretManagerService jwtSecretManagerService;

    @Value("${jwt.token.refresh.period.days:6}")
    private final long refreshPeriodDays;

    public Authentication getAuth(HttpServletRequest request, HttpServletResponse response) {
        String jwtToken = JwtHttpConverterService.extractJwtToken(request);
        if (jwtToken == null || jwtToken.isBlank()) {
            return null;
        }

        // TODO
//        if (jwtDisableService.isDisabledToken(jwtToken)) {
//            throw new JwtException("JWT token is expired or invalid.");
//        }

//        Claims claims;
//        try {
//            claims = Jwts.parser().setSigningKey(jwtSecretManagerService.getSecret()).parseClaimsJws(jwtToken).getBody();
//        } catch (ExpiredJwtException ex) {
//            Date nowDate = new Date();
//            Date expirationDate = ex.getClaims().getExpiration();
//            String subject = ex.getClaims().getSubject();
//            long diff = TimeUnit.DAYS.convert(Math.abs(nowDate.getTime() - expirationDate.getTime()), TimeUnit.MILLISECONDS);
//            if (diff < refreshPeriodDays) {
//                jwtToken = jwtProviderService.createToken(subject);
//                jwtHttpConverterService.injectJwtToken(jwtToken, response);
//                return new JwtAuthentication(subject);
//            } else {
//                throw new JwtException("JWT token is expired or invalid.");
//            }
//        }
//        return new JwtAuthentication(claims.getSubject());
        return null;
    }
}
