package com.project01_rent_a_car.rentacarapi.exceptions;

public class OfferNotFoundException extends RuntimeException {
    public OfferNotFoundException(String message) {
        super(message);
    }
}
