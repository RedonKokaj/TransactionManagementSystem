# Banking System Backend

A robust **Spring Boot REST API** designed to simulate a core banking system. This application provides a secure and transactional environment for managing users, and processing financial movements.

## Project Overview

This project serves as a backend engine for a fintech application. It manages the full lifecycle of a bank account, from user registration and account creation to transaction processing. The core focus is on **data integrity**, **strict validation**, and **clean architectural patterns**.

For now, the frontend portion of this project has been put on hold.

## Key Features

* **User Management:** Secure registration and login flows.
* **Automated IBAN Generation:** Custom logic to generate valid Italian IBANs (`IT` + Check Digits + CIN + ABI + CAB + Account Number) with collision detection.
* **Transactional Engine:** Processing of **Deposits**, **Withdrawals**, and **Transfers** using Spring's `@Transactional`.
* **DTO Pattern:** Total separation between database entities and API data to ensure security and flexibility.
* **Robust Testing:** High-coverage suite including **Integration Tests** (Service layer) and **Web Layer Tests** (MockMvc).

## Tech Stack

* **Java 17**
* **Spring Boot 3**
* **Spring Data JPA** (Hibernate)
* **H2 Database** (In-memory for development/testing)
* **PostgreSQL**n (Main project database)
* **JUnit 5 & Mockito**
* **Maven**

---

## API Endpoints

### User & Authentication

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/api/users/register` | Create a new user account. |
| `POST` | `/api/users/login` | Authenticate user credentials. |

### Account Management

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/api/accounts/create` | Open a new account with a generated IBAN. |
| `GET` | `/api/accounts/check/{userId}` | Retrieve all accounts owned by a user. |

### Financial Transactions

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/api/transactions/process` | Execute a Deposit, Withdrawal, or Transfer. |
| `GET` | `/api/transactions/history/{iban}` | View the movement history for an account. |

---

## Testing & Reliability

The application is built and tested to ensure financial operations are bug-free:

* **Isolation:** Database state is rolled back after every test to maintain purity.
* **Edge Case Handling:** Specific tests for insufficient funds, duplicate emails, and invalid IBANs.

---

## Getting Started

### Prerequisites

* JDK 17 or higher
* Maven 3.6+

### Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/banking-backend.git

```


2. Navigate to the project folder:
```bash
cd banking-backend

```


3. Build the project:
```bash
mvn clean install

```


4. Run the application:
```bash
mvn spring-boot:run

```



The server will be available at `http://localhost:8081`.

---

### Example Request (Create Account)

**POST** `/api/accounts/create`

```json
{
  "userId": 1
}

```

**Response**

```json
{
  "id": 1,
  "iban": "IT99L0012300045000000000123",
  "balance": 50.00,
  "userId": 1
}

```
