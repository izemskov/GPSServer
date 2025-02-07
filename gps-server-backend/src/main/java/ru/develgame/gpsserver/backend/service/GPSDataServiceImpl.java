package ru.develgame.gpsserver.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.develgame.gpsserver.backend.dto.GPSDataRequestDto;
import ru.develgame.gpsserver.backend.entity.GPSData;
import ru.develgame.gpsserver.backend.entity.GPSUser;
import ru.develgame.gpsserver.backend.exception.InvalidArgumentException;
import ru.develgame.gpsserver.backend.repository.GPSDataRepository;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class GPSDataServiceImpl implements GPSDataService {
    @Autowired
    private GPSDataRepository gpsDataRepository;

    @Override
    public GPSData createData(GPSDataRequestDto data) {
        if (data.lat() == null || data.lat().isBlank()) {
            throw new InvalidArgumentException("Lat cannot be empty");
        }

        if (data.lon() == null || data.lon().isBlank()) {
            throw new InvalidArgumentException("Lon cannot be empty");
        }

        GPSUser principal = (GPSUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        GPSData gpsData = new GPSData();

        try {
            gpsData.setLatitude(Double.parseDouble(data.lat()));
        } catch (NumberFormatException ex) {
            throw new InvalidArgumentException("Cannot parse lat %s".formatted(data.lat()));
        }

        try {
            gpsData.setLongitude(Double.parseDouble(data.lon()));
        } catch (NumberFormatException ex) {
            throw new InvalidArgumentException("Cannot parse lon %s".formatted(data.lon()));
        }

        Date current = new Date();
        gpsData.setDate(new java.sql.Date(current.getTime()));
        gpsData.setTimestamp(current.getTime());
        gpsData.setGpsUser(principal);

        return gpsDataRepository.save(gpsData);
    }
}
