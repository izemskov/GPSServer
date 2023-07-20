package ru.develgame.gpsviewer.security;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class GPSServerDaoAuthenticationProvider extends DaoAuthenticationProvider {
    private final UserDetailsService gpsServerUserDetailsService;

    private final PasswordEncoder gpsServerPasswordEncoder;

    public GPSServerDaoAuthenticationProvider(UserDetailsService gpsServerUserDetailsService, PasswordEncoder gpsServerPasswordEncoder) {
        this.gpsServerUserDetailsService = gpsServerUserDetailsService;
        this.gpsServerPasswordEncoder = gpsServerPasswordEncoder;
        this.setUserDetailsService(gpsServerUserDetailsService);
        this.setPasswordEncoder(gpsServerPasswordEncoder);
    }
}
