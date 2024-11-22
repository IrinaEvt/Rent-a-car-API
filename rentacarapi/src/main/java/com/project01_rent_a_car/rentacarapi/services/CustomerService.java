package com.project01_rent_a_car.rentacarapi.services;

import com.project01_rent_a_car.rentacarapi.entities.Car;
import com.project01_rent_a_car.rentacarapi.entities.Customer;
import com.project01_rent_a_car.rentacarapi.mappers.CarRowMapper;
import com.project01_rent_a_car.rentacarapi.mappers.CustomerRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomerService {
    private final JdbcTemplate db;



    public CustomerService(JdbcTemplate jdbc) {
        this.db = jdbc;

    }

    public boolean createCustomer(Customer customer){

        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO td_customers (first_name, last_name, address, phone, age, has_accidents)")
                .append("VALUES (?, ?, ?, ?, ?, ?)");

        int resultCount = this.db.update(query.toString(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getAddress(),
                customer.getPhone(),
                customer.getAge(),
                customer.hasAccidents());


        return resultCount == 1;
    }

    public Customer getCustomer(int id) {

        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM td_customers WHERE is_active = 1 AND id = " + id);
        ArrayList<Customer> collection = (ArrayList<Customer>) this.db.query(query.toString(), new CustomerRowMapper());

        if(collection.isEmpty()) {
            return null;
        }

        return collection.get(0);
    }
}
