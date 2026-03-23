# 💰 Personal Expense Tracker (PET)

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.4-brightgreen)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow)](LICENSE)

A high-performance, full-stack Personal Expense Tracker built with **Spring Boot 3**, **Spring Security**, and **MySQL**. Manage your finances with ease, visualize your spending, and keep your data secure with enterprise-grade encryption.

---

## ✨ Features

### 🔐 Security & Identity
- **Stateful JWT Authentication:** Secure login and signup with stateless session management.
- **Enterprise Encryption:** 128-bit **AES Encryption** for sensitive transaction descriptions.
- **BCrypt Password Hashing:** One-way salted hashing for user credentials.
- **CORS Support:** Integrated Cross-Origin Resource Sharing for seamless frontend-backend communication.

### 📊 Dashboard & Analytics
- **Real-Time Overview:** Instantly view your Net Balance, Total Income, and Total Expenses.
- **Interactive Visualizations:** Dynamic charts powered by **Chart.js** for category-wise spending analysis.
- **Recent Activity:** Quick access to your latest transactions.

### 💸 Transaction Management
- **Full CRUD Operations:** Effortlessly add, edit, or delete income and expense entries.
- **Smart Filtering:** Search and filter transactions by date range and categories.
- **Rich Categorization:** Over 8+ pre-defined categories (Food, Rent, Travel, etc.) with custom category support.

### 📄 Reports & Export
- **Monthly Insights:** Detailed monthly breakdowns to track your spending habits.
- **PDF Export:** Generate professional financial reports in PDF format using **OpenPDF**.
- **Data Integrity:** Cascading deletes ensure no orphaned records remain.

---

## 🛠️ Technology Stack

| Layer | Technologies |
| :--- | :--- |
| **Backend** | Java 17, Spring Boot 3, JPA (Hibernate), Spring Security |
| **Database** | MySQL 8.0 |
| **Frontend** | HTML5, CSS3 (Glassmorphism), Vanilla JavaScript, Chart.js |
| **Auth** | JSON Web Tokens (JJWT), BCrypt |
| **Reporting** | OpenPDF |
| **Development** | Maven Wrapper, Lombok, Spring DevTools |

---

## 🚀 Quick Start

### 📋 Prerequisites
1.  **Java Development Kit (JDK) 17+**
2.  **MySQL Server 8.0+**
3.  **Maven 3.6+** (optional, as `mvnw` is included)

### 1️⃣ Database Setup
Create the database and initialize the schema:
```powershell
# Open MySQL Shell and run:
mysql -u your_user -p -e "SOURCE src/main/resources/schema.sql;"
```

### 2️⃣ Configuration
Update `src/main/resources/application.properties` with your credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/expense_tracker
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### 3️⃣ Run the Application
You can use the provided script or Maven Wrapper:
```powershell
# Using Maven Wrapper
./mvnw.cmd spring-boot:run
```
The application will be accessible at: `http://localhost:8082`

### 4️⃣ Access the Dashboard
Open your browser and navigate to:
- **Login:** `http://localhost:8082/pages/login.html`
- **Signup:** `http://localhost:8082/pages/signup.html`

---

## 📁 Project Structure

```text
Personal Expense Tracker/
├── .mvn/                  # Maven Wrapper configuration
├── src/
│   ├── main/
│   │   ├── java/com/pet/expensetracker/
│   │   │   ├── config/    # Security & JWT Configuration
│   │   │   ├── controller/# REST API Endpoints
│   │   │   ├── dto/       # Data Transfer Objects
│   │   │   ├── entity/    # JPA Persistence Models
│   │   │   ├── repository/# Database Repositories (Spring Data JPA)
│   │   │   ├── service/   # Business Logic Implementations
│   │   │   └── util/      # AES Encryption & Utility Classes
│   │   └── resources/
│   │       ├── static/    # Frontend Assets (HTML, CSS, JS)
│   │       ├── schema.sql # MySQL Database Schema
│   │       └── application.properties
│   └── test/              # Unit & Integration Tests
├── pom.xml                # Project Object Model
└── queries.sql            # Sample MySQL Queries
```

---

## 🔒 Security Summary
*   **Encrypted Storage:** All sensitive transaction descriptions are automatically encrypted before being stored in the database.
*   **Access Control:** Every API endpoint is protected by Spring Security, requiring a valid JWT issued upon successful authentication.

---
*Developed with focus on Performance and Financial Excellence.*
