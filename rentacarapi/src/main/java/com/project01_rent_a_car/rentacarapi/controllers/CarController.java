package com.project01_rent_a_car.rentacarapi.controllers;

import com.project01_rent_a_car.rentacarapi.entities.Car;
import com.project01_rent_a_car.rentacarapi.exceptions.CarNotFoundException;
import com.project01_rent_a_car.rentacarapi.exceptions.CustomerNotFoundException;
import com.project01_rent_a_car.rentacarapi.exceptions.UnsupportedLocationException;
import com.project01_rent_a_car.rentacarapi.http.AppResponse;
import com.project01_rent_a_car.rentacarapi.services.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class CarController {
    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    //Добавяне на нов автомобил
    @PostMapping("/cars")
    public ResponseEntity<?> createNewCar(@RequestBody Car car) {

        HashMap<String, Object> response = new HashMap<>();

        if (this.carService.createCar(car)) {

            return AppResponse.success()
                    .withMessage("Car created successfully")
                    .build();
        }

        return AppResponse.error()
                .withMessage("Car could not be created")
                .build();
    }

    //Листване на всички автомобили, на базата на локацията на клиента
    @GetMapping("/cars/customer/{customerId}")
    public ResponseEntity<?> fetchCarsByLocation(@PathVariable int customerId) {

        try {
            ArrayList<Car> collection = (ArrayList<Car>) this.carService.getAllCarsByLocation(customerId);

            return AppResponse.success()
                    .withData(collection)
                    .build();

        } catch (CustomerNotFoundException e) {
            return AppResponse.error()
                    .withMessage("Customer not found")
                    .withCode(HttpStatus.NOT_FOUND)
                    .build();
        } catch(UnsupportedLocationException e){
            return AppResponse.error()
                .withMessage(e.getMessage())
                .withCode(HttpStatus.BAD_REQUEST)
                .build();
        }
}

//Листване на конкретен автомобил по ID
@GetMapping("/cars/{id}")
public ResponseEntity<?> getSingleCar(@PathVariable int id) {

    try{
        Car car = carService.getSingleCar(id);
        return AppResponse.success()
                .withDataAsArray(car)
                .build();}
    catch (CarNotFoundException e){
        return AppResponse.error()
                .withMessage(e.getMessage())
                .withCode(HttpStatus.NOT_FOUND)
                .build();
    }
}

//Актуализация на съществуващ автомобил
@PutMapping("/cars")
public ResponseEntity<?> updateCar(@RequestBody Car car) {
    boolean isUpdated = carService.updateCar(car);

    if (!isUpdated) {
        return AppResponse.error()
                .withMessage("Car data not found or update failed")
                .build();
    }

    return AppResponse.success()
            .withMessage("Car updated successfully")
            .build();
}

//Изтриване на автомобил от системата
@DeleteMapping("/cars/{id}")
public ResponseEntity<?> deleteCar(@PathVariable int id) {
    boolean isDeleted = carService.deleteCar(id);

    if (isDeleted) {
        return AppResponse.success()
                .withMessage("Car deleted successfully")
                .build();
    }

    return AppResponse.error()
            .withMessage("Car could not be deleted")
            .build();
}
}
