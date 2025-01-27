package ru.develgame.gpsserver.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.develgame.gpsserver.backend.dto.GPSReceivedData;
import ru.develgame.gpsserver.backend.entities.GPSData;
import ru.develgame.gpsserver.backend.repositories.GPSDataRepository;
import ru.develgame.gpsserver.backend.security.SecurityUserDetails;

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

//    @GetMapping("/log")
//    public ResponseEntity<Void> log(@RequestParam(name = "lat") String latitude,
//                                    @RequestParam(name = "longitude") String longitude)
//    {
//        SecurityUserDetails principal = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        double lat = Double.parseDouble(latitude);
//        double longit = Double.parseDouble(longitude);
//        GPSData gpsData = new GPSData();
//        gpsData.setLatitude(lat);
//        gpsData.setLongitude(longit);
//        gpsData.setDate(new java.sql.Date(new Date().getTime()));
//        gpsData.setTimestamp(new Date().getTime());
//        gpsData.setUser(principal.getUserEntity());
//
//        gpsDataRepository.save(gpsData);
//
//        return ResponseEntity.ok(null);
//    }
//
//    @GetMapping("/data")
//    public ResponseEntity<List<GPSReceivedData>> data(@RequestParam(name = "date") java.sql.Date date, @RequestParam(name = "user_id") Long userId) throws SQLException {
//        List<GPSReceivedData> res = new ArrayList<>();
//
//        try (Connection connection = dataSource.getConnection()) {
//            try (PreparedStatement statement = connection.prepareStatement("SELECT latitude, longitude, timestamp FROM gpsdata WHERE date=? AND user_id=?")) {
//                statement.setDate(1, date);
//                statement.setLong(2, userId);
//                try (ResultSet resultSet = statement.executeQuery()) {
//                    while (resultSet.next()) {
//                        res.add(new GPSReceivedData(
//                                resultSet.getDouble(1),
//                                resultSet.getDouble(2),
//                                new Date(resultSet.getLong(3))));
//                    }
//                }
//            }
//        }
//
//        return ResponseEntity.ok(res);
//    }
//
//    @GetMapping("/getAllDates")
//    public ResponseEntity<List<java.sql.Date>> getAllDates(@RequestParam(name = "user_id") Long userId) throws SQLException {
//        List<java.sql.Date> res = new ArrayList<>();
//
//        try (Connection connection = dataSource.getConnection()) {
//            try (PreparedStatement statement = connection.prepareStatement("SELECT date FROM gpsdata WHERE user_id=? GROUP BY date")) {
//                statement.setLong(1, userId);
//                try (ResultSet resultSet = statement.executeQuery()) {
//                    while (resultSet.next()) {
//                        res.add(resultSet.getDate(1));
//                    }
//                }
//            }
//        }
//
//        return ResponseEntity.ok(res);
//    }
}
