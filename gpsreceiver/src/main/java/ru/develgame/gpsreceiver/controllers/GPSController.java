package ru.develgame.gpsreceiver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.develgame.gpsdomain.GPSReceivedData;
import ru.develgame.gpsreceiver.entities.GPSData;
import ru.develgame.gpsreceiver.repositories.GPSDataRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class GPSController {
    @Autowired
    private GPSDataRepository gpsDataRepository;

    @Autowired
    private DataSource dataSource;

    @GetMapping("/log")
    public ResponseEntity<Void> log(@RequestParam(name = "lat") String latitude,
                                    @RequestParam(name = "longitude") String longitude)
    {
        double lat = Double.parseDouble(latitude);
        double longit = Double.parseDouble(longitude);
        GPSData gpsData = new GPSData();
        gpsData.setLatitude(lat);
        gpsData.setLongitude(longit);
        gpsData.setDate(new java.sql.Date(new Date().getTime()));
        gpsData.setTimestamp(new Date().getTime());

        gpsDataRepository.save(gpsData);

        return ResponseEntity.ok(null);
    }

    @GetMapping("/data")
    public ResponseEntity<List<GPSReceivedData>> data(@RequestParam(name = "date") java.sql.Date date) throws SQLException {
        List<GPSReceivedData> res = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT latitude, longitude, timestamp FROM gpsdata WHERE date=?")) {
                statement.setDate(1, date);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        res.add(new GPSReceivedData(
                                resultSet.getDouble(1),
                                resultSet.getDouble(2),
                                new Date(resultSet.getLong(3))));
                    }
                }
            }
        }

        return ResponseEntity.ok(res);
    }

    @GetMapping("/getAllDates")
    public ResponseEntity<List<java.sql.Date>> getAllDates() throws SQLException {
        List<java.sql.Date> res = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT date FROM gpsdata GROUP BY date")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        res.add(resultSet.getDate(1));
                    }
                }
            }
        }

        return ResponseEntity.ok(res);
    }
}
