package ru.develgame.gpsserver.backend.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record AuthRequestDto(@JsonProperty("login") String login,
                             @JsonProperty("password") String password,
                             @JsonProperty("expireDays") int expireDays) {
}
