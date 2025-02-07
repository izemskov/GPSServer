package ru.develgame.gpsserver.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.develgame.gpsserver.backend.entity.GPSData;

@Repository
public interface GPSDataRepository extends JpaRepository<GPSData, Long> {
}
