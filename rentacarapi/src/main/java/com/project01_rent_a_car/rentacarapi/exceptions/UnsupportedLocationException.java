package com.project01_rent_a_car.rentacarapi.exceptions;

public class UnsupportedLocationException extends RuntimeException {
    public UnsupportedLocationException(String message) {
        super(message);
    }
}
