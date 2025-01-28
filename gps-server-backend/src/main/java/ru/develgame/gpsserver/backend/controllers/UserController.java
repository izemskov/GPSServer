package ru.develgame.gpsserver.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.develgame.gpsserver.backend.dto.GPSViewerUser;
import ru.develgame.gpsserver.backend.entity.GPSUser;
import ru.develgame.gpsserver.backend.repository.GPSUserRepository;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private GPSUserRepository GPSUserRepository;

    @GetMapping("getByName")
    public ResponseEntity<GPSViewerUser> getUserIdByNameAndPwd(@RequestParam(name = "name") String name) {
        GPSUser GPSUser = GPSUserRepository.findByName(name).orElse(null);
        if (GPSUser == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new GPSViewerUser(GPSUser.getId(), GPSUser.getName(), GPSUser.getPwd()));
    }
}
