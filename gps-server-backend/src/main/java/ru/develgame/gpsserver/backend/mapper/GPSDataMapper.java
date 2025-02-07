package ru.develgame.gpsserver.backend.mapper;

import org.springframework.stereotype.Component;
import ru.develgame.gpsserver.backend.dto.GPSDataResponseDto;
import ru.develgame.gpsserver.backend.entity.GPSData;

@Component
public class GPSDataMapper {
    public GPSDataResponseDto toDto(GPSData dbBean) {
        return GPSDataResponseDto.builder()
                .id(dbBean.getId())
                .latitude(dbBean.getLatitude())
                .longitude(dbBean.getLongitude())
                .date(dbBean.getDate())
                .timestamp(dbBean.getTimestamp())
                .build();
    }
}
