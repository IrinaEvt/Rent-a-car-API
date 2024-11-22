package com.project01_rent_a_car.rentacarapi.entities;

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private int age;
    private int hasAccidents;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int hasAccidents() {
        return hasAccidents;
    }

    public void setHasAccidents(int hasAccidents) {
        this.hasAccidents = hasAccidents;
    }


}
