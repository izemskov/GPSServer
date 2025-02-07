package ru.develgame.gpsserver.backend.security.jwt.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.develgame.gpsserver.backend.entity.GPSUser;
import ru.develgame.gpsserver.backend.exception.GPSUserNotFoundException;
import ru.develgame.gpsserver.backend.repository.GPSDisableTokenRepository;
import ru.develgame.gpsserver.backend.repository.GPSUserRepository;
import ru.develgame.gpsserver.backend.security.jwt.util.JwtHttpConverterService;
import ru.develgame.gpsserver.backend.security.jwt.util.JwtSecretManagerService;

@Service
@RequiredArgsConstructor
public class JwtAuthService {
    private final JwtSecretManagerService jwtSecretManagerService;
    private final GPSDisableTokenRepository gpsDisableTokenRepository;
    private final GPSUserRepository gpsUserRepository;

    public Authentication getAuth(HttpServletRequest request) {
        if (request == null) {
            throw new JwtException("Token not found");
        }

        String jwtToken = JwtHttpConverterService.extractJwtToken(request);
        if (jwtToken == null || jwtToken.isBlank()) {
            throw new JwtException("Token not found");
        }

        if (gpsDisableTokenRepository.findByToken(jwtToken) != null) {
            throw new JwtException("JWT token %s is disabled".formatted(jwtToken));
        }

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecretManagerService.getSecret())
                    .parseClaimsJws(jwtToken)
                    .getBody();

            GPSUser gpsUser = gpsUserRepository.findById(Long.parseLong(claims.getSubject()))
                    .orElseThrow(() -> new GPSUserNotFoundException("GPSUser with id: %s not found"
                            .formatted(claims.getSubject())));
            return new JwtAuthentication(gpsUser);
        } catch (ExpiredJwtException ex) {
            throw new JwtException("JWT token %s is expired".formatted(jwtToken));
        } catch (NumberFormatException ex) {
            throw new JwtException("JWT token %s wrong subject".formatted(jwtToken));
        } catch (Exception ex) {
            throw new JwtException("JWT token %s is invalid".formatted(jwtToken));
        }
    }
}
