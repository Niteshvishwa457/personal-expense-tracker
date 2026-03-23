# Personal Expense Tracker

A full-stack web application designed to track daily income and expenses with premium aesthetics and enterprise-grade security.

## 🚀 Features
- **User Authentication:** Secure signup and login using Spring Security and JWT.
- **Dashboard:** Real-time summary of total balance, income, and expenses with interactive charts.
- **Transaction Management:** Full CRUD operations on income and expense entries.
- **Filtering:** Search and filter transactions by date range and category.
- **Monthly Reports:** Detailed monthly analysis and category-wise breakdown.
- **PDF Export:** Generate and download professional PDF reports.
- **Data Security:** End-to-end encryption principles using AES for sensitive transaction descriptions.
- **Premium UI:** Modern, responsive design with glassmorphism and smooth animations.

## 🛠️ Tech Stack
- **Backend:** Java 17, Spring Boot 3, Spring Security, JWT, JPA (Hibernate).
- **Database:** MySQL.
- **Frontend:** HTML5, CSS3, JavaScript (Vanilla), Chart.js.
- **Libraries:** Lombok, OpenPDF, JJWT.

## ⚙️ Setup Instructions

### Prerequisites
1. **Java 17+** installed.
2. **Maven** installed.
3. **MySQL Server** installed and running.

### 1. Database Setup
Create the database and required tables using the provided SQL script:
```sql
-- Run this command in your MySQL terminal
SOURCE src/main/resources/schema.sql;
```

### 2. Backend Configuration
Update `src/main/resources/application.properties` with your MySQL credentials:
```properties
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### 3. Run the Application
Navigate to the project root and run:
```bash
mvn spring-boot:run
```
The application will start on `http://localhost:8080`.

### 4. Access the Frontend
Open your browser and go to:
`http://localhost:8080/index.html`

## 📁 Project Structure
```text
src/main/java/com/pet/expensetracker/
├── config/        # Security, JWT, and App Config
├── controller/    # REST API Controllers
├── dto/           # Data Transfer Objects
├── entity/        # JPA Entities (User, Category, Transaction)
├── repository/    # JPA Repositories
├── service/       # Business Logic Layer
└── util/          # Encryption (AES) and helper utilities

src/main/resources/
├── static/        # Frontend (HTML, CSS, JS)
└── schema.sql     # Database Schema
```

## 🔒 Security Summary
- **JWT:** Stateless session management.
- **BCrypt:** One-way hashing for user passwords.
- **AES:** 128-bit encryption for sensitive transaction descriptions before database storage.
- **CORS:** Controlled cross-origin access (enabled for local testing).

---
*Created with ❤️ for Personal Financial Excellence.*
