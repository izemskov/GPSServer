package ru.develgame.gpsserver.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.develgame.gpsserver.backend.entity.GPSDisableToken;

@Repository
public interface GPSDisableTokenRepository extends JpaRepository<GPSDisableToken, Long> {
    GPSDisableToken findByToken(String token);
}
