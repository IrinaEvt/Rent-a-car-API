package com.project01_rent_a_car.rentacarapi.mappers;


import com.project01_rent_a_car.rentacarapi.entities.Car;
import com.project01_rent_a_car.rentacarapi.entities.Customer;
import com.project01_rent_a_car.rentacarapi.entities.Offer;
import com.project01_rent_a_car.rentacarapi.services.CustomerService;
import org.springframework.jdbc.core.RowMapper;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OfferRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        return null;
    }
}
