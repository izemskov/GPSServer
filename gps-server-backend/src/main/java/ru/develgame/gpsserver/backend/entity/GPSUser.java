package ru.develgame.gpsserver.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "GPS_USER")
@Getter
@Setter
public class GPSUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(nullable = false, length = 255)
    private String pwd;

    @OneToMany(mappedBy = "gpsUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GPSData> gpsData = new ArrayList<>();
}
