package com.project01_rent_a_car.rentacarapi.entities;

import java.math.BigDecimal;

public class Offer {
    private int id;
    private Customer customer;
    private Car car;
    private int rentalDays;
    private BigDecimal totalPrice;
    private int isAccepted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setCustomerId(int id){

    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int isAccepted() {
        return isAccepted;
    }

    public void setAccepted(int accepted) {
        isAccepted = accepted;
    }
}
