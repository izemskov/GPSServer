package ru.develgame.gpsserver.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.develgame.gpsserver.backend.dto.auth.AuthRequestDto;
import ru.develgame.gpsserver.backend.dto.auth.AuthResponseDto;
import ru.develgame.gpsserver.backend.security.jwt.provider.JwtProviderService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtProviderService jwtProviderService;

    @PostMapping
    public ResponseEntity<AuthResponseDto> createToken(@RequestBody AuthRequestDto authRequestDto) {
        return ResponseEntity.ok(AuthResponseDto.builder()
                .token(jwtProviderService.checkAuthAndCreateToken(authRequestDto))
                .build());
    }
}
