package com.task.station.error;

public class StationNotFoundException extends Exception {

    public StationNotFoundException(String message) {
        super(message);
    }

    public StationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
