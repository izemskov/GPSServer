package ru.develgame.gpsserver.backend.integration.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import ru.develgame.gpsserver.backend.entity.GPSDisableToken;
import ru.develgame.gpsserver.backend.integration.configuration.BaseRepositoryIT;
import ru.develgame.gpsserver.backend.repository.GPSDisableTokenRepository;

import java.util.UUID;

class GPSDisableTokenRepositoryIT extends BaseRepositoryIT {
    @Autowired
    private GPSDisableTokenRepository gpsDisableTokenRepository;

    @AfterEach
    void cleanUp() {
        gpsDisableTokenRepository.deleteAll();
    }

    @Test
    void should_findByToken() {
        String uuid1 = UUID.randomUUID().toString();
        String uuid2 = UUID.randomUUID().toString();

        GPSDisableToken gpsDisableToken = new GPSDisableToken();
        gpsDisableToken.setExpiredAt(1000000L);
        gpsDisableToken.setToken(uuid1);
        gpsDisableToken = gpsDisableTokenRepository.save(gpsDisableToken);

        GPSDisableToken gpsDisableToken1 = new GPSDisableToken();
        gpsDisableToken1.setExpiredAt(2000000L);
        gpsDisableToken1.setToken(uuid2);
        gpsDisableToken1 = gpsDisableTokenRepository.save(gpsDisableToken1);

        GPSDisableToken actual = gpsDisableTokenRepository.findByToken(uuid1);

        Assertions.assertEquals(gpsDisableToken.getId(), actual.getId());
    }

    @Test
    void shoild_checkMaxTokenLength() {
        String token1 = "a".repeat(255);
        String token2 = "b".repeat(256);

        GPSDisableToken gpsDisableToken = new GPSDisableToken();
        gpsDisableToken.setExpiredAt(1000000L);
        gpsDisableToken.setToken(token1);
        gpsDisableToken = gpsDisableTokenRepository.save(gpsDisableToken);

        GPSDisableToken gpsDisableToken1 = new GPSDisableToken();
        gpsDisableToken1.setExpiredAt(2000000L);
        gpsDisableToken1.setToken(token2);
        Assertions.assertThrowsExactly(DataIntegrityViolationException.class,
                () -> gpsDisableTokenRepository.save(gpsDisableToken1));
    }
}
