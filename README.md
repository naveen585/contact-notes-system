# contact-notes-system
User can create various contacts and also add notes for that particular contacts. He can perform all the operations such as create, add, update and delete.

## Running the Contact Notes System Locally

This guide explains how to set up and run the Contact Notes backend service on your local machine.

---

###  Tech Stack
- **Java 17**
- **Spring Boot 3.4.4**
- **MySQL**
- **Liquibase** (for DB migrations)
- **JWT** (for authentication)

---

### Prerequisites

Make sure you have the following installed:

- Java 17+
- Maven 3.6+
- MySQL Server
- Git

---

###  Clone the Repository

```bash
git clone https://github.com/naveen585/contact-notes-system.git
cd contact-notes-system
```

---

### Setup the Database

1. **Create the database**:
```sql
CREATE DATABASE contact_notes;
```

2. **Update the** `application.properties` file:

```
spring.datasource.url=jdbc:mysql://localhost:3306/created_db_name
spring.datasource.username=your_username
spring.datasource.password=your_password
```

---


### Generate JWT Secret

Set a JWT secret in `application.properties`:

```
jwt.secret=your-32-byte-minimum-secret-key-value-123! 
jwt.expiration=3600000000
```

---

###  Run the App

```bash
./mvnw spring-boot:run
```

or if you have Maven installed:

```bash
mvn clean install
mvn spring-boot:run
```

App runs by default at:  
📍 `http://localhost:8080`

If needed change port number as well in `application.properties` file

server.port=port_number
---

###  Initial API Endpoints

- **Register**: `POST /auth/register`
- **Login**: `POST /auth/login`

---

###  Running Tests

```bash
mvn test
```

---

###  Project Structure

```
src/
├── controller
├── entity
├── dto
├── service
├── repository
├── config
├── security
├── events
└── util
```
