package com.project01_rent_a_car.rentacarapi.mappers;


import com.project01_rent_a_car.rentacarapi.entities.Car;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarRowMapper implements RowMapper {
    @Override
    public Car mapRow(ResultSet rs, int rowNum) throws SQLException {
        Car car = new Car();
        car.setId(rs.getInt("id"));
        car.setBrand(rs.getString("brand"));
        car.setModel(rs.getString("model"));
        car.setPricePerDay(rs.getBigDecimal("price_per_day"));
        car.setLocation(rs.getString("location"));

        return car;
    }
}
