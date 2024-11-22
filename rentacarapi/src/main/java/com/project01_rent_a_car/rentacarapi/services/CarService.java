package com.project01_rent_a_car.rentacarapi.services;


import com.project01_rent_a_car.rentacarapi.entities.Car;
import com.project01_rent_a_car.rentacarapi.entities.Customer;
import com.project01_rent_a_car.rentacarapi.mappers.CarRowMapper;
import com.project01_rent_a_car.rentacarapi.exceptions.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {
    private JdbcTemplate db;
    private final CustomerService customerService;

    public CarService(JdbcTemplate jdbc, CustomerService customerService){
        this.db = jdbc;
        this.customerService = customerService;
    }

    public boolean createCar(Car car){

            StringBuilder query = new StringBuilder();
            query.append("INSERT INTO td_cars (brand, model, price_per_day, location)")
                    .append("VALUES (?, ?, ?, ?)");

            int resultCount = this.db.update(query.toString(),
                    car.getBrand(),
                    car.getModel(),
                    car.getPricePerDay(),
                    car.getLocation());

            return resultCount == 1;
    }

    public List<Car> getAllCarsByLocation(int customerId) {

        Customer customer = customerService.getCustomer(customerId);

        if (customer == null) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " does not exist.");
        }
        String customerLocation = customer.getAddress();


        if (!isSupportedLocation(customerLocation)) {
            throw new UnsupportedLocationException("Unsupported location. Supported locations: Sofia, Plovdiv, Varna, Burgas.");
        }
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM td_cars WHERE is_active = 1 AND is_available = 1").append("AND location = ?");

        return this.db.query(query.toString(), new CarRowMapper(), customerLocation);
    }

    public Car getSingleCar(int id) {

        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT * FROM td_cars WHERE is_active = 1 AND id = " + id);
            ArrayList<Car> collection = (ArrayList<Car>) this.db.query(query.toString(), new CarRowMapper());

            if (collection.isEmpty()) {
                throw new CarNotFoundException("Car with ID " + id + " does not exist.");
            }
            return collection.get(0);
        } catch (DataAccessException e) {
            throw new CarNotFoundException("Car with ID " + id + " does not exist.");
        }
    }

    public boolean updateCar(Car car) {

        StringBuilder query = new StringBuilder();
        query.append("UPDATE td_cars ")
                .append("SET brand = ?,")
                .append("model = ?,")
                .append("price_per_day = ?,")
                .append("location = ? ")
                .append("WHERE is_active = 1")
                .append(" AND is_available = 1")
                .append(" AND id = ?");


        int resultCount = this.db.update(query.toString(),
                car.getBrand(),
                car.getModel(),
                car.getPricePerDay(),
                car.getLocation(),
                car.getId());

        if(resultCount > 1) {
            throw new RuntimeException("More than one cars with same id exists");
        }

        return resultCount == 1;
    }

    public boolean deleteCar(int id) {

        StringBuilder query = new StringBuilder();
        query.append("UPDATE td_cars ")
                .append("SET is_active = 0 ")
                .append("WHERE id = ? AND is_active = 1");


        int resultCount = this.db.update(query.toString(), id);

        return resultCount == 1;
    }


    private boolean isSupportedLocation(String location) {
        return "Sofia".equalsIgnoreCase(location) ||
                "Plovdiv".equalsIgnoreCase(location) ||
                "Varna".equalsIgnoreCase(location) ||
                "Burgas".equalsIgnoreCase(location);
    }


}
