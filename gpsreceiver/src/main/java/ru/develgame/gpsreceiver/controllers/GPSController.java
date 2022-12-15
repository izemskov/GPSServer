package ru.develgame.gpsreceiver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.develgame.gpsreceiver.entities.GPSData;
import ru.develgame.gpsreceiver.repositories.GPSDataRepository;

import java.util.Date;

@RestController
public class GPSController {
    @Autowired
    private GPSDataRepository gpsDataRepository;

    @GetMapping("/log")
    public ResponseEntity<Void> log(@RequestParam(name = "lat") String latitude,
                                    @RequestParam(name = "long") String longitude)
    {
        try {
            double lat = Double.parseDouble(latitude);
            double longit = Double.parseDouble(longitude);
            GPSData gpsData = new GPSData();
            gpsData.setLatitude(lat);
            gpsData.setLongitude(longit);
            gpsData.setTime(new Date());

            gpsDataRepository.save(gpsData);

            System.out.println("lat = " + latitude + "; long = " + longitude);
        }
        catch (NumberFormatException ex) {
            // TODO - add log message
        }

        return ResponseEntity.ok(null);
    }
}
