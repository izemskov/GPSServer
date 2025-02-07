package ru.develgame.gpsserver.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.develgame.gpsserver.backend.entity.GPSUser;

import java.util.Optional;

@Repository
public interface GPSUserRepository extends JpaRepository<GPSUser, Long> {
    Optional<GPSUser> findByName(String name);
}
