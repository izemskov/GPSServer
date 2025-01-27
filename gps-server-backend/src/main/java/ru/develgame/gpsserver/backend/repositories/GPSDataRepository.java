package ru.develgame.gpsserver.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.develgame.gpsserver.backend.entities.GPSData;

public interface GPSDataRepository extends JpaRepository<GPSData, Long> {
}
