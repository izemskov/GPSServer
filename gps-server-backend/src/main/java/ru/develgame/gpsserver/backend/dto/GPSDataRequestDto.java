package ru.develgame.gpsserver.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GPSDataRequestDto(@JsonProperty("lat") String lat,
                                @JsonProperty("lon") String lon) {
}
