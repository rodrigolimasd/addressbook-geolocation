package com.stoom.addressbook.exception;

public class GeolocationNotFound extends RuntimeException {
    public GeolocationNotFound(String message) {
        super(message);
    }
}
