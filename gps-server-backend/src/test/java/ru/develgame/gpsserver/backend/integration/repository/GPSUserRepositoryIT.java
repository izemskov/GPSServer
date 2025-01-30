package ru.develgame.gpsserver.backend.integration.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import ru.develgame.gpsserver.backend.entity.GPSData;
import ru.develgame.gpsserver.backend.entity.GPSUser;
import ru.develgame.gpsserver.backend.integration.configuration.BaseRepositoryIT;
import ru.develgame.gpsserver.backend.repository.GPSDataRepository;
import ru.develgame.gpsserver.backend.repository.GPSUserRepository;

import java.sql.Date;

class GPSUserRepositoryIT extends BaseRepositoryIT {
    @Autowired
    private GPSUserRepository gpsUserRepository;

    @Autowired
    private GPSDataRepository gpsDataRepository;

    @AfterEach
    void cleanUp() {
        gpsUserRepository.deleteAll();
        gpsDataRepository.deleteAll();
    }

    @Test
    void should_createUser() {
        GPSUser gpsUser = new GPSUser();
        gpsUser.setName("test");
        gpsUser.setPwd("testPwd");
        gpsUser = gpsUserRepository.save(gpsUser);

        Assertions.assertEquals(1, gpsUserRepository.findAll().size());
        Assertions.assertEquals(gpsUser.getId(), gpsUserRepository.findAll().get(0).getId());
    }

    @Test
    void should_notCreateUser_whenNameNull() {
        GPSUser gpsUser = new GPSUser();
        gpsUser.setPwd("testPwd");
        Assertions.assertThrowsExactly(DataIntegrityViolationException.class, () -> gpsUserRepository.save(gpsUser));
    }

    @Test
    void should_notCreateUser_whenPwdNull() {
        GPSUser gpsUser = new GPSUser();
        gpsUser.setName("test");
        Assertions.assertThrowsExactly(DataIntegrityViolationException.class, () -> gpsUserRepository.save(gpsUser));
    }

    @Test
    void should_cascadeDeleteDataWithUser() {
        GPSUser gpsUser = new GPSUser();
        gpsUser.setName("test");
        gpsUser.setPwd("testPwd");
        gpsUser = gpsUserRepository.save(gpsUser);

        GPSData gpsData = new GPSData();
        gpsData.setLatitude(1.0d);
        gpsData.setLongitude(2.0d);
        gpsData.setDate(new Date(1000000L));
        gpsData.setTimestamp(1000000L);
        gpsData.setGpsUser(gpsUser);
        gpsDataRepository.save(gpsData);

        gpsUserRepository.deleteById(gpsUser.getId());

        Assertions.assertEquals(0, gpsUserRepository.findAll().size());
        Assertions.assertEquals(0, gpsDataRepository.findAll().size());
    }
}
