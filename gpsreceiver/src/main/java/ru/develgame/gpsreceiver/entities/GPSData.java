package ru.develgame.gpsreceiver.entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "gpsdata")
public class GPSData {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    private double latitude;
    private double longitude;
    private Date date;
    private long timestamp;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
