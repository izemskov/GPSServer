package ru.develgame.gpsserver.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "GPSUSER")
@Getter
@Setter
public class GPSUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String pwd;

    @OneToMany(mappedBy = "gpsUser", fetch = FetchType.LAZY)
    private List<GPSData> gpsData = new ArrayList<>();

    /* --- Equals and HashCode ---*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GPSUser GPSUser = (GPSUser) o;
        return id == GPSUser.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
