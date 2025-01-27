package ru.develgame.gpsserver.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.develgame.gpsserver.backend.entities.GPSUser;

import java.util.Optional;

public interface GPSUserRepository extends JpaRepository<GPSUser, Long> {
    Optional<GPSUser> findByName(String name);
}
