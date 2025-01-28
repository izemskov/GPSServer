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

class GPSDataRepositoryIT extends BaseRepositoryIT {
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
    void should_createGPSData() {
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
        gpsData = gpsDataRepository.save(gpsData);

        Assertions.assertEquals(1, gpsDataRepository.findAll().size());
        Assertions.assertEquals(gpsData.getId(), gpsDataRepository.findAll().get(0).getId());
    }

    @Test
    void should_notCreateGPSData_whenLatitudeIsNull() {
        GPSUser gpsUser = new GPSUser();
        gpsUser.setName("test");
        gpsUser.setPwd("testPwd");
        gpsUser = gpsUserRepository.save(gpsUser);

        GPSData gpsData = new GPSData();
        gpsData.setLongitude(2.0d);
        gpsData.setDate(new Date(1000000L));
        gpsData.setTimestamp(1000000L);
        gpsData.setGpsUser(gpsUser);

        DataIntegrityViolationException actual = Assertions.assertThrowsExactly(DataIntegrityViolationException.class,
                () -> gpsDataRepository.save(gpsData));
        Assertions.assertEquals("could not execute statement; SQL [n/a]; constraint [null]",
                actual.getMessage());

        Assertions.assertEquals(0, gpsDataRepository.findAll().size());
    }

    @Test
    void should_notCreateGPSData_whenLongitudeIsNull() {
        GPSUser gpsUser = new GPSUser();
        gpsUser.setName("test");
        gpsUser.setPwd("testPwd");
        gpsUser = gpsUserRepository.save(gpsUser);

        GPSData gpsData = new GPSData();
        gpsData.setLatitude(1.0d);
        gpsData.setDate(new Date(1000000L));
        gpsData.setTimestamp(1000000L);
        gpsData.setGpsUser(gpsUser);

        DataIntegrityViolationException actual = Assertions.assertThrowsExactly(DataIntegrityViolationException.class,
                () -> gpsDataRepository.save(gpsData));
        Assertions.assertEquals("could not execute statement; SQL [n/a]; constraint [null]",
                actual.getMessage());

        Assertions.assertEquals(0, gpsDataRepository.findAll().size());
    }

    @Test
    void should_notCreateGPSData_whenDateIsNull() {
        GPSUser gpsUser = new GPSUser();
        gpsUser.setName("test");
        gpsUser.setPwd("testPwd");
        gpsUser = gpsUserRepository.save(gpsUser);

        GPSData gpsData = new GPSData();
        gpsData.setLatitude(1.0d);
        gpsData.setLongitude(2.0d);
        gpsData.setTimestamp(1000000L);
        gpsData.setGpsUser(gpsUser);

        DataIntegrityViolationException actual = Assertions.assertThrowsExactly(DataIntegrityViolationException.class,
                () -> gpsDataRepository.save(gpsData));
        Assertions.assertEquals("could not execute statement; SQL [n/a]; constraint [null]",
                actual.getMessage());

        Assertions.assertEquals(0, gpsDataRepository.findAll().size());
    }

    @Test
    void should_notCreateGPSData_whenTimestampIsNull() {
        GPSUser gpsUser = new GPSUser();
        gpsUser.setName("test");
        gpsUser.setPwd("testPwd");
        gpsUser = gpsUserRepository.save(gpsUser);

        GPSData gpsData = new GPSData();
        gpsData.setLatitude(1.0d);
        gpsData.setLongitude(2.0d);
        gpsData.setDate(new Date(1000000L));
        gpsData.setGpsUser(gpsUser);

        DataIntegrityViolationException actual = Assertions.assertThrowsExactly(DataIntegrityViolationException.class,
                () -> gpsDataRepository.save(gpsData));
        Assertions.assertEquals("could not execute statement; SQL [n/a]; constraint [null]",
                actual.getMessage());

        Assertions.assertEquals(0, gpsDataRepository.findAll().size());
    }

    @Test
    void should_notCreateGPSData_whenGpsUserIsNull() {
        GPSData gpsData = new GPSData();
        gpsData.setLatitude(1.0d);
        gpsData.setLongitude(2.0d);
        gpsData.setDate(new Date(1000000L));
        gpsData.setTimestamp(1000000L);

        DataIntegrityViolationException actual = Assertions.assertThrowsExactly(DataIntegrityViolationException.class,
                () -> gpsDataRepository.save(gpsData));
        Assertions.assertEquals("could not execute statement; SQL [n/a]; constraint [null]",
                actual.getMessage());

        Assertions.assertEquals(0, gpsDataRepository.findAll().size());
    }

    @Test
    void should_deleteGPSData() {
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
        gpsData = gpsDataRepository.save(gpsData);

        gpsDataRepository.delete(gpsData);

        Assertions.assertEquals(0, gpsDataRepository.findAll().size());
        Assertions.assertEquals(1, gpsUserRepository.findAll().size());
    }
}
