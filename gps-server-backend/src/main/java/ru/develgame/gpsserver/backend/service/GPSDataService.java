package ru.develgame.gpsserver.backend.service;

import ru.develgame.gpsserver.backend.dto.GPSDataRequestDto;
import ru.develgame.gpsserver.backend.entity.GPSData;

public interface GPSDataService {
    GPSData createData(GPSDataRequestDto data);
}
