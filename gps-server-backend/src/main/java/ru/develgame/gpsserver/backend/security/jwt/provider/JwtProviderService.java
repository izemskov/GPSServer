package ru.develgame.gpsserver.backend.security.jwt.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.develgame.gpsserver.backend.dto.auth.AuthRequestDto;
import ru.develgame.gpsserver.backend.entity.GPSUser;
import ru.develgame.gpsserver.backend.exception.AuthFailedException;
import ru.develgame.gpsserver.backend.exception.InvalidArgumentException;
import ru.develgame.gpsserver.backend.repository.GPSUserRepository;
import ru.develgame.gpsserver.backend.security.jwt.util.JwtSecretManagerService;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtProviderService {
    private final GPSUserRepository gpsUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtSecretManagerService jwtSecretManagerService;

    public String checkAuthAndCreateToken(AuthRequestDto authRequestDto) {
        validateAuthRequestDto(authRequestDto);
        GPSUser user = checkAndGetUserByLoginAndPassword(authRequestDto.login(), authRequestDto.password());
        return createToken(user, authRequestDto.expiredDays());
    }

    private void validateAuthRequestDto(AuthRequestDto authRequestDto) {
        if (authRequestDto == null) {
            throw new InvalidArgumentException("Request cannot be empty");
        }

        if (authRequestDto.login() == null || authRequestDto.login().isBlank()) {
            throw new InvalidArgumentException("Login cannot be empty");
        }

        if (authRequestDto.password() == null || authRequestDto.password().isBlank()) {
            throw new InvalidArgumentException("Password cannot be empty");
        }

        if (authRequestDto.expiredDays() <= 0) {
            throw new InvalidArgumentException("Expires must be more than zero");
        }
    }

    private GPSUser checkAndGetUserByLoginAndPassword(String login, String password) {
        GPSUser user = gpsUserRepository.findByName(login)
                .orElseThrow(() -> new AuthFailedException());

        if (!passwordEncoder.matches(password, user.getPwd())) {
            throw new AuthFailedException();
        }

        return user;
    }

    private String createToken(GPSUser user, int expiredDays) {
        Claims claims = Jwts.claims().setSubject(Long.toString(user.getId()));
        Date now = new Date();
        Date validity = new Date(now.getTime() + (long) expiredDays * 24L * 3600L * 1000L);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, jwtSecretManagerService.getSecret())
                .compact();
    }
}
