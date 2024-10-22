# Group-6 Auto Shop Application

## Overview
The Auto Shop Application is designed to manage and track orders for an auto shop. It allows users to perform various tasks based on their roles: Admin, Seller, or Customer. Key functionalities include managing car parts inventory, processing orders, and maintaining customer and vehicle information.

## Technologies Used
- **Database**: PostgreSQL hosted on Amazon RDS.
- **Backend**: Java 17+ with Spring Boot 3.3.1
    - Spring Framework
    - Spring Data JPA for database communication
    - Spring Web (MVC) for RESTful API
- **Frontend**: React with JavaScript.
- **Documentation**: Comprehensive README (this) for both UI and API components.

## How to Run the Application
### Prerequisites
- PostgreSQL installed and running.
- Java 17+ installed.
- Node.js and npm (Node Package Manager) installed.

### Backend (API)
1. Clone the repository:
   ```bash
   git clone https://github.com/2406-Ryan-Java-FS/Group-6.git
   ```
3. Navigate to the backend directory:
   ```bash
   cd Group-6/backend
   ```
3. Set up the database:
   - Ensure PostgreSQL is running and accessible.
   - Update the application.properties file with your database credentials.
4. Build and run the application:
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

### Frontend (UI)
1. Navigate to the frontend directory:
   ```bash
   cd Group-6/frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Run the application:
   ```bash
   npm start
   ```
### Accessing the Application
- The backend API will be available at http://localhost:8080.
- The frontend UI will be available at http://localhost:3000.

## Database Schema
```sql
-- Users Table
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role VARCHAR(20) CHECK (role IN ('Customer', 'Seller', 'Admin')) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Vehicle Makes and Models Table
CREATE TABLE vehicle_makes_models (
    make_model_id SERIAL PRIMARY KEY,
    make VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    year INT NOT NULL,
    customer_id INT REFERENCES users(user_id) ON DELETE SET NULL
);

-- Parts Table
CREATE TABLE parts (
    part_id SERIAL PRIMARY KEY,
    part_name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    seller_id INT REFERENCES users(user_id) ON DELETE SET NULL,
    make_model_id INT REFERENCES vehicle_makes_models(make_model_id) ON DELETE SET NULL,
    inventory INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Orders Table
CREATE TABLE orders (
    order_id SERIAL PRIMARY KEY,
    customer_id INT REFERENCES users(user_id) ON DELETE SET NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) CHECK (status IN ('Pending', 'Shipped', 'Delivered', 'Cancelled')) NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    part_id INT REFERENCES parts(part_id) NOT NULL,
    quantity INT NOT NULL
);
```
## Example Data
```sql
-- Insert Users
INSERT INTO users (username, password_hash, email, role) VALUES
('customer1', 'hashed_password_1', 'customer1@example.com', 'Customer'),
('seller1', 'hashed_password_2', 'seller1@example.com', 'Seller'),
('admin', 'hashed_password_3', 'admin@example.com', 'Admin');

-- Insert Vehicle Makes and Models
INSERT INTO vehicle_makes_models (make, model, year, customer_id) VALUES
('Toyota', 'Camry', 2020, 1),
('Ford', 'Mustang', 2019, 2),
('Honda', 'Civic', 2021, 3);

-- Insert Parts
INSERT INTO parts (part_name, description, price, seller_id, make_model_id, inventory) VALUES
('Engine Oil Filter', 'High-quality oil filter for engines', 15.99, 2, 1, 100),
('Car Battery', 'Durable car battery with long life', 120.00, 2, 2, 50),
('Leather Seat Covers', 'Premium leather seat covers', 200.00, 2, 3, 20);

-- Insert Orders
INSERT INTO orders (customer_id, status, total, part_id, quantity) VALUES
(1, 'Pending', 47.97, 1, 3),
(1, 'Pending', 120.00, 2, 1);
```

## User Stories
### All Users
- [x] As a user, I can log into my account.
- [x] As a user, I can log out of my account.
- [x] As a user, I can update my account.
- [x] As a user, I can delete my account.

### Customer
- [x] As a customer, I can register a new account.
- [x] As a customer, I can search for a specific part by name.
- [x] As a customer, I can place an order.
- [x] As a customer, I can view all my orders.
- [ ] As a customer, I can register my vehicle.
- [ ] As a customer, I can return a part.

### Admin
- [x] As an admin, I can log into the Admin page.
- [x] As an admin, I can view all users.
- [x] As an admin, I can lookup and cancel an order by order id.
- [x] As an admin, I can lookup and delete a user by user id.
- [x] As an admin, I can update orders.
- [ ] As an admin, I can view all orders.
- [ ] As an admin, I can update part price and inventory.
- [ ] As an admin, I can promote an account to seller.
- [ ] As an admin, I can promote an account to admin.

### Seller
- [ ] As a seller, I can place an order for a customer.
- [ ] As a seller, I can update part price.
- [ ] As a seller, I can update part inventory.

## Stretch Goals
- [ ] Implement unit and integration testing using Spring Test/JUnit and Postman collections.
- [x] Secure API endpoints using JWTs (JSON Web Tokens).
- [ ] Implement password reset functionality.
- [ ] Add notification features.
- [ ] Integrate with a third-party API.

## Presentation
The project presentation is scheduled for the morning of July 22nd, 2024. Each team member will present a section of the application, accompanied by a PowerPoint slideshow.

## Relevant Information for New Team Members
- Repository Structure: The project repository is divided into backend and frontend directories.
- Coding Standards: Follow standard coding practices and maintain code readability.
- Development Workflow: Use Git for version control, following the GitFlow workflow.
- Communication: Use project management tools like Jira or Trello for task tracking and collaboration.
