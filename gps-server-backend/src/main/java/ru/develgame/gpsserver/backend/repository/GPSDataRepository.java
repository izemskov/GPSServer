package ru.develgame.gpsserver.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.develgame.gpsserver.backend.entity.GPSData;

public interface GPSDataRepository extends JpaRepository<GPSData, Long> {
}
