package com.project01_rent_a_car.rentacarapi.entities;

import java.math.BigDecimal;

public class Car {
    private int id;
    private String brand;
    private String model;
    private BigDecimal pricePerDay;
    private String location;

    private int isAvailable;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(BigDecimal pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int isAvailable() {
        return isAvailable;
    }

    public void setAvailable(int available) {
        isAvailable = available;
    }
}
