package ru.develgame.gpsreceiver.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class GPSServerPasswordEncoder extends BCryptPasswordEncoder {
}
