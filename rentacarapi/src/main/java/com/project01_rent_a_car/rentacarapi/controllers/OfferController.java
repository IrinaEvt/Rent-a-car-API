package com.project01_rent_a_car.rentacarapi.controllers;

import com.project01_rent_a_car.rentacarapi.entities.Offer;
import com.project01_rent_a_car.rentacarapi.exceptions.CustomerNotFoundException;
import com.project01_rent_a_car.rentacarapi.exceptions.OfferAcceptanceException;
import com.project01_rent_a_car.rentacarapi.exceptions.OfferNotFoundException;
import com.project01_rent_a_car.rentacarapi.http.AppResponse;
import com.project01_rent_a_car.rentacarapi.http.OfferRequest;
import com.project01_rent_a_car.rentacarapi.services.OfferService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class OfferController {
    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping("/offers")
    public ResponseEntity<?> createOffer(@RequestBody OfferRequest offerRequest) {
        int customerId = offerRequest.getCustomerId();
        int carId = offerRequest.getCarId();
        int rentalDays = offerRequest.getRentalDays();

        boolean isCreated = offerService.createOffer(customerId, carId, rentalDays);

        if (isCreated) {
            return AppResponse.success()
                    .withMessage("Offer created successfully")
                    .build();
        } else {
            return AppResponse.error()
                    .withMessage("Offer could not be created")
                    .build();
        }
    }

    @GetMapping("/offers/customer/{customerId}")
    public ResponseEntity<?> listOffersForCustomer(@PathVariable int customerId) {
        try {
            ArrayList<Offer> offers = (ArrayList<Offer>) offerService.listOffersForCustomer(customerId);

            return AppResponse.success()
                    .withData(offers)
                    .build();
        }catch (CustomerNotFoundException e){
            return AppResponse.error()
                    .withMessage("Customer not found")
                    .withCode(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @GetMapping("/offers/{id}")
    public ResponseEntity<?> fetchSingleOffer(@PathVariable int id) {
        Offer offer = offerService.getSingleOffer(id);

        if (offer == null) {
            return AppResponse.error()
                    .withMessage("Offer not found")
                    .build();
        }

        return AppResponse.success()
                .withDataAsArray(offer)
                .build();
    }

    @PutMapping("offers/accept/{offerId}")
    public ResponseEntity<?> acceptOffer(@PathVariable int offerId) {
        try {
            boolean isAccepted = offerService.acceptOffer(offerId);
            if (isAccepted) {
                return AppResponse.success()
                        .withMessage("Offer accepted successfully")
                        .build();
            } else {
                return AppResponse.error()
                        .withMessage("Offer could not be accepted")
                        .build();
            }
        } catch (DataAccessException e) {
            return AppResponse.error()
                    .withMessage(e.getMessage())
                    .build();
        }
    }


    @DeleteMapping("offers/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable int id) {
        boolean isDeleted = offerService.deleteOffer(id);

        if (isDeleted) {
            return AppResponse.success()
                    .withMessage("Offer deleted successfully")
                    .build();
        } else {
            return AppResponse.error()
                    .withMessage("Offer could not be deleted")
                    .build();
        }

    }
}
