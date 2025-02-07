package ru.develgame.gpsserver.backend.exception;

public class GPSUserNotFoundException extends RuntimeException {
    public GPSUserNotFoundException(String message) {
        super(message);
    }
}
