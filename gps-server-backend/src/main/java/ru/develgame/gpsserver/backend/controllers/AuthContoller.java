package ru.develgame.gpsserver.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.develgame.gpsserver.backend.dto.auth.AuthRequestDto;
import ru.develgame.gpsserver.backend.dto.auth.AuthResponseDto;

@RestController
@RequestMapping("/auth")
public class AuthContoller {
    @PostMapping
    public ResponseEntity<AuthResponseDto> createToken(@RequestBody AuthRequestDto authRequestDto) {
        return null;
    }
}
