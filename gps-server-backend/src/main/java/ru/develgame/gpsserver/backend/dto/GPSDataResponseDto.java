package ru.develgame.gpsserver.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.sql.Date;

@Builder
public record GPSDataResponseDto(@JsonProperty("id") long id,
                                 @JsonProperty("latitude") double latitude,
                                 @JsonProperty("longitude") double longitude,
                                 @JsonProperty("date") Date date,
                                 @JsonProperty("timestamp") long timestamp) {
}

