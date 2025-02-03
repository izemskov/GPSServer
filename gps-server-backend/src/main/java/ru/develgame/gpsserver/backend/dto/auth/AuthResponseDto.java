package ru.develgame.gpsserver.backend.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record AuthResponseDto(@JsonProperty("token") String token) {
}
