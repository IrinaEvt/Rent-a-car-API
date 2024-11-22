package com.project01_rent_a_car.rentacarapi.services;

import com.project01_rent_a_car.rentacarapi.entities.Car;
import com.project01_rent_a_car.rentacarapi.entities.Customer;
import com.project01_rent_a_car.rentacarapi.entities.Offer;
import com.project01_rent_a_car.rentacarapi.exceptions.CustomerNotFoundException;
import com.project01_rent_a_car.rentacarapi.exceptions.OfferAcceptanceException;
import com.project01_rent_a_car.rentacarapi.exceptions.OfferNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
public class OfferService {
    private final JdbcTemplate db;
    private final CustomerService customerService;
    private final CarService carService;


    public OfferService(JdbcTemplate jdbc, CustomerService customerService, CarService carService) {
        this.db = jdbc;
        this.customerService = customerService;
        this.carService = carService;
    }

    //създаване на нова оферта с данни за потребителя, модела на автомобила и дните за наемане
    public boolean createOffer(int customerId, int carId, int rentalDays) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO td_offers (customer_id, car_id, rental_days, total_price, is_accepted) ")
                .append("VALUES (?, ?, ?, ?, ?)");

        Customer customer = customerService.getCustomer(customerId);
        Car car = carService.getSingleCar(carId);

        int hasAccidents = customer.hasAccidents();
        BigDecimal dailyPrice = car.getPricePerDay();


        BigDecimal totalPrice = calculateTotalPrice(dailyPrice, rentalDays, hasAccidents);

        int resultCount = this.db.update(query.toString(),
                customerId,
                carId,
                rentalDays,
                totalPrice,
                0);

        return resultCount == 1;
    }


    //листване на всички оферти за даден потребител
    public List<Offer> listOffersForCustomer(int customerId) {
        String query = "SELECT * FROM td_offers WHERE customer_id = ?";

        Customer customer = customerService.getCustomer(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " does not exist.");
        }

        return db.query(query, (rs, rowNum) -> {
            Offer offer = new Offer();


            offer.setId(rs.getInt("id"));
            offer.setRentalDays(rs.getInt("rental_days"));
            offer.setTotalPrice(rs.getBigDecimal("total_price"));
            offer.setAccepted(rs.getInt("is_accepted"));


            offer.setCustomer(customer);


            int carId = rs.getInt("car_id");
            Car car = carService.getSingleCar(carId);
            offer.setCar(car);

            return offer;
        }, customer.getId());
    }


    //листване на конкретна оферта
    public Offer getSingleOffer(int id) {

        String query = "SELECT * FROM td_offers WHERE is_active = 1 AND id = ?";


        List<Offer> collection = db.query(query, (rs, rowNum) -> {
            Offer offer = new Offer();

            offer.setId(rs.getInt("id"));
            offer.setRentalDays(rs.getInt("rental_days"));
            offer.setTotalPrice(rs.getBigDecimal("total_price"));
            offer.setAccepted(rs.getInt("is_accepted"));

            int customerId = rs.getInt("customer_id");
            Customer customer = customerService.getCustomer(customerId);
            offer.setCustomer(customer);

            int carId = rs.getInt("car_id");
            Car car = carService.getSingleCar(carId);
            offer.setCar(car);


            return offer;
        },id);
        return collection.get(0);
    }

    //изтриване на оферта
    public boolean deleteOffer(int id) {

        StringBuilder query = new StringBuilder();
        query.append("UPDATE td_offers ")
                .append("SET is_active = 0 ")
                .append("WHERE id = ? AND is_active = 1");


        int resultCount = this.db.update(query.toString(), id);

        return resultCount == 1;
    }

    //приемане на оферта - в склучай в които потребителя вземе автомобила
    public boolean acceptOffer(int offerId) {
        try {
            boolean isCarUpdated = this.setCarUnavailable(offerId);
            if(isCarUpdated) {
                StringBuilder query = new StringBuilder();
                query.append("UPDATE td_offers SET is_accepted = 1 WHERE id = ?");
                int resultCount = this.db.update(query.toString(), offerId);
                return resultCount == 1;
            }else {throw new OfferNotFoundException("Error updating car availability");}
        } catch (DataAccessException e) {
            throw new OfferAcceptanceException("Error accepting offer: " + e.getMessage());
        }
    }

    private boolean setCarUnavailable(int offerId){
        Offer offer = this.getSingleOffer(offerId);
        Car car = offer.getCar();
        StringBuilder query = new StringBuilder();
        query.append("UPDATE td_cars SET is_available = 0 WHERE id = ?");
        try {
        int resultCount = this.db.update(query.toString(), car.getId());
        return resultCount == 1;
        } catch (DataAccessException e) {
            throw new OfferAcceptanceException("Error updating car availability: " + e.getMessage());
        }
    }


    private BigDecimal calculateTotalPrice(BigDecimal dailyPrice, int rentalDays, int hasAccidents) {
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (int day = 1; day <= rentalDays; day++) {
            BigDecimal currentDayPrice = dailyPrice;

            // Проверка дали денят е уикенд
            if (isWeekend(day)) {
                currentDayPrice = currentDayPrice.multiply(BigDecimal.valueOf(1.1));
            }

            totalPrice = totalPrice.add(currentDayPrice);
        }

        if (hasAccidents==1) {
            totalPrice = totalPrice.add(BigDecimal.valueOf(200));
        }

        return totalPrice;
    }

    private boolean isWeekend(int daysAhead) {
        LocalDate currentDate = java.time.LocalDate.now();
        LocalDate rentalDate = currentDate.plusDays(daysAhead - 1);

        DayOfWeek dayOfWeek = rentalDate.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
