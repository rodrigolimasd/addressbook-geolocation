package com.stoom.addressbook.exception;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(String message) {
        super(message);
    }
}
