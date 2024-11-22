DROP TABLE td_cарс;
DROP TABLE td_customers;
DROP TABLE td_offers;
DROP TABLE td_tc_car_offer;
DROP TABLE tc_customer_offer;


---автомобили
CREATE TABLE IF NOT EXISTS td_cars(
id INT PRIMARY KEY AUTO_INCREMENT,
    brand VARCHAR(255),
    model VARCHAR(255),
    price_per_day DECIMAL(10, 2),
    location VARCHAR(50),
    is_available INT DEFAULT 1,
    is_active INT DEFAULT 1
);

---клиенти
CREATE TABLE IF NOT EXISTS td_customers(
id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    address VARCHAR(255),
    phone VARCHAR(15),
    age INT,
    has_accidents INT,
    is_active INT DEFAULT 1
);

---оферти
CREATE TABLE IF NOT EXISTS td_offers(
id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    car_id INT,
   rental_days INT,
    total_price DECIMAL(10, 2),
    is_accepted INT DEFAULT 0,
    is_active INT DEFAULT 1,
);

---релация между кола и оферта
CREATE TABLE IF NOT EXISTS tc_car_offer(
car_id INT,
offer_id INT,
PRIMARY KEY(car_id, offer_id)
);

---релация между клиент и оферта
CREATE TABLE IF NOT EXISTS tc_customer_offer(
customer_id INT,
offer_id INT,
PRIMARY KEY(customer_id, offer_id)
);