package com.project01_rent_a_car.rentacarapi.controllers;

import com.project01_rent_a_car.rentacarapi.entities.Customer;
import com.project01_rent_a_car.rentacarapi.http.AppResponse;
import com.project01_rent_a_car.rentacarapi.services.CarService;
import com.project01_rent_a_car.rentacarapi.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CustomerController {

    private CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customers")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        boolean isCreated = customerService.createCustomer(customer);

        if (isCreated) {
            return AppResponse.success()
                    .withMessage("Customer created successfully")
                    .build();
        } else {
            return AppResponse.error()
                    .withMessage("Customer could not be created")
                    .build();
        }
    }
}
