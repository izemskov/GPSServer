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
import java.util.stream.Collectors;

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
        try {
            double lat = Double.parseDouble(latitude);
            double longit = Double.parseDouble(longitude);
            GPSData gpsData = new GPSData();
            gpsData.setLatitude(lat);
            gpsData.setLongitude(longit);
            gpsData.setDate(new java.sql.Date(new Date().getTime()));
            gpsData.setTimestamp(new Date().getTime());

            gpsDataRepository.save(gpsData);

            System.out.println("lat = " + latitude + "; long = " + longitude);
        }
        catch (NumberFormatException ex) {
            // TODO - add log message
        }

        return ResponseEntity.ok(null);
    }

    @GetMapping("/data")
    public ResponseEntity<List<GPSReceivedData>> data() {
        List<GPSData> all = gpsDataRepository.findAll();
        return ResponseEntity.ok(all.stream()
                .map(t -> new GPSReceivedData(t.getLatitude(), t.getLongitude(), new Date(t.getTimestamp())))
                .collect(Collectors.toList()));
    }

    @GetMapping("/getAllDates")
    public ResponseEntity<List<String>> getAllDates() throws SQLException {
        List<String> res = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT date FROM gpsdata GROUP BY date")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        res.add(resultSet.getString(1));
                    }
                }
            }
        }

        return ResponseEntity.ok(res);
    }
}
