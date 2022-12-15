package ru.develgame.gpsreceiver.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GPSController {
    @GetMapping("/log")
    public ResponseEntity<Void> log(@RequestParam(name = "lat") String latitude,
                                    @RequestParam(name = "long") String longitude)
    {
        System.out.println("lat = " + latitude + "; long = " + longitude);
        return ResponseEntity.ok(null);
    }
}
