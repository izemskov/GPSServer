package ru.develgame.gpsreceiver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.develgame.gpsreceiver.entities.User;
import ru.develgame.gpsreceiver.repositories.UserRepository;

import java.util.Optional;

@Component
public class GPSServerUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byId = userRepository.findByName(username);
        if (byId.isPresent()) {
            return new SecurityUserDetails(byId.get());
        }
        else
            throw new UsernameNotFoundException("");
    }
}
