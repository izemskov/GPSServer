package ru.develgame.gpsreceiver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.develgame.gpsreceiver.entities.GPSData;

public interface GPSDataRepository extends JpaRepository<GPSData, Long> {
}
