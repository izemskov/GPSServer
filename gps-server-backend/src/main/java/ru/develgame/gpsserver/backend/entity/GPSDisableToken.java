package ru.develgame.gpsserver.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "GPS_DISABLED_TOKEN")
@Getter
@Setter
public class GPSDisableToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private Long expiredAt;

    @Column(nullable = false, unique = true, length = 255)
    private String token;
}
