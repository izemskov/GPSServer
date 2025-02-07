package ru.develgame.gpsserver.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name = "GPS_DATA")
@Getter
@Setter
public class GPSData {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @ManyToOne(optional = false)
    private GPSUser gpsUser;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private Long timestamp;
}
