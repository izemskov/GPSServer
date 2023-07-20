package ru.develgame.gpsviewer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.develgame.gpsdomain.GPSViewerUser;

@Component
public class GPSServerUserDetailsService implements UserDetailsService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ResponseEntity<GPSViewerUser> responseEntity = restTemplate.getForEntity("http://127.0.0.1:8111/user/getByName?name=" + username, GPSViewerUser.class);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new UsernameNotFoundException("");
        }

        return new SecurityUserDetails(responseEntity.getBody());
    }
}
