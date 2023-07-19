package ru.develgame.gpsreceiver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.develgame.gpsdomain.GPSViewerUser;
import ru.develgame.gpsreceiver.entities.User;
import ru.develgame.gpsreceiver.repositories.UserRepository;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("getByName")
    public ResponseEntity<GPSViewerUser> getUserIdByNameAndPwd(@RequestParam(name = "name") String name) {
        User user = userRepository.findByName(name).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new GPSViewerUser(user.getId(), user.getName(), user.getPwd()));
    }
}
