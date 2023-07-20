package ru.develgame.gpsviewer.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.develgame.gpsdomain.GPSViewerUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SecurityUserDetails implements UserDetails {
    private GPSViewerUser userEntity;

    public SecurityUserDetails(GPSViewerUser userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auth = new ArrayList<>();
        if (userEntity != null)
            auth.add(new SimpleGrantedAuthority("USER"));
        return auth;
    }

    public GPSViewerUser getUserEntity() {
        return userEntity;
    }

    @Override
    public String getPassword() {
        return userEntity.getPwd();
    }

    @Override
    public String getUsername() {
        return userEntity.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
