package com.project01_rent_a_car.rentacarapi.mappers;


import com.project01_rent_a_car.rentacarapi.entities.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();

        customer.setId(rs.getInt("id"));
        customer.setFirstName(rs.getString("first_name"));
        customer.setLastName(rs.getString("last_name"));
        customer.setAddress(rs.getString("address"));
        customer.setPhone(rs.getString("phone"));
        customer.setAge(rs.getInt("age"));
        customer.setHasAccidents(rs.getInt("has_accidents"));

        return customer;
    }
}
