package ru.develgame.gpsserver.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "GPSDATA")
@Getter
@Setter
public class GPSData {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @ManyToOne
    private GPSUser gpsUser;

    private double latitude;
    private double longitude;
    private Date date;
    private long timestamp;

    /* --- Equals and HashCode ---*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GPSData gpsData = (GPSData) o;
        return id == gpsData.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
