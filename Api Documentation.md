Here’s a structured breakdown for your documentation, covering:

---

## Key Decisions, Tradeoffs, and Assumptions

### Key Decisions

| Decision                                | Reason                                                                                                                 |
|-----------------------------------------|------------------------------------------------------------------------------------------------------------------------|
| **Spring Boot + Java 17**               | The choice for a modern, production-ready framework is excellent and has a lot of ecosystem support.                   |
| **JWT-based authentication**            | Stateless and secure token-based authentication for RESTful APIs.                                                      |
| **MySQL is the DB**                     | MySQL is Reliable, personal, suitable for contact-relational note data.                                                |
| **EventListener for Events Processing** | Allows event-driven design can decouple and also can create the analytics and report as well.                          |
| **Liquibase for DB migrations**         | It will have version-controlled and trackable database schema management and easy to deploy in different environments. |

---

### Tradeoffs

- It has event-driven architecture via @EventListener mechanism
- Integrated Swagger because focused on backend development, so using swagger we will have a UI to interact with APIs.
- Using JWT which is simpler setup and also ideal for production-scale as well.
- Instead of Hard delete implemented Soft delete using `isDeleted` flag and keep the details for further use.
- Audit fields like `createdAt`, `deletedBy`, etc easy for tracking and log of data manipulation.
- Implemented integrations such as JWT, Swagger, liquibase, mySql and written Unit tests for the functionalities.
- Added constraints for the fields and also instead of returning the objects will return the DTOs

---

### Assumptions

- A user should only see/edit their own contacts and notes.
- JWT token provides valid and accurate username in `subject`.
- RabbitMQ or downstream analytics processing is **eventually consistent** and **non-blocking**.
- Soft delete is preferred over hard delete for traceability.

---

---


---

##  What I'd Improve with More Time

| Area | Improvement                                                                                                                          |
|------|--------------------------------------------------------------------------------------------------------------------------------------|
| **Security** | Implementation of role-based access (Admin/User), token expiry refresh handling, brute-force prevention.                             |
| **Testing** | Add end-to-end tests using Testcontainers (for MySQL).As of now the code coverage is 38%, if possible should increase code coverage. |
| **Monitoring** | Integration of Splunk used for logging and monitor the health of endpoints.                                                          |                                                                                 |
| **Frontend** | Build a Angular interface to manage contacts and notes visually.                                                                     |
| **Search** | Able to implement the filter like full-text search for notes using ElasticSearch or native MySQL FT index.                           |
| **Pagination** | Introducing paging and sorting for contacts and notes list endpoints.                                                                |
| **Retry Handling** | Event handling by implmeneting the Retry handling so that we can able to retry the event once it is failed.                          |

---

## API Documentation

Instructions to use the endpoints:

### AuthenticationController

#### `POST /auth/register`

- **Request Body**
```json
{
  "username": "johndoe",
  "password": "password123"
}
```

- **Response**: `200 OK`  
  Returns success message or error if user exists.

---

#### `POST /auth/login`

- **Request Body**
```json
{
  "username": "johndoe",
  "password": "password123"
}
```

- **Response**: `200 OK` 
- We will get an JWT token which is used to pass in the header for authorizing the rest of the apis.
```
  "token": "eyJhbGciOiJIUzI1NiIsInR..."
```

---

### Contacts

#### `GET /api/contacts`
- **Headers**: `Authorization: Bearer <JWT>`
- **Response**: `200 OK`
```json
[
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "phone": 1234567890,
    "createdAt": "2024-04-01T12:00:00",
    "notes": [...]
  }
]
```

---

#### `POST /api/contacts`

- **Headers**: `Authorization: Bearer <JWT>`
- **Request Body**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "phone": 1234567890
}
```

- **Response**: `201 Created`  
  Returns the saved contact.

---

#### `PUT /api/contacts/{id}`
Pass the value in path param id: 1
- **Headers**: `Authorization: Bearer <JWT>`
- **Request Body**:
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "phone": 1234567890
}
```
- **Response**: `202 Accepted`

---

#### `DELETE /api/contacts/{id}`
Pass the value in path param id: 1
- Performs soft delete.
- **Headers**: `Authorization: Bearer <JWT>`
- **Response**: `204 No Content`

---

### Notes

#### `GET /api/contacts/{id}/notes`
Pass the value in path param id: 1

- Fetch all notes for a contact.
- **Headers**: `Authorization: Bearer <JWT>`
- **Response**: `200 OK`
```json
[
  {
    "id": 10,
    "body": "Follow-up in 3 days",
    "contactId": 1,
    "createdAt": "2024-04-05T12:00:00"
  }
]
```

---

#### `POST /api/notes`

- **Request Body**
- **Headers**: `Authorization: Bearer <JWT>`
```json
{
  "contactId": 1,
  "body": "Met with client. Follow-up in 2 weeks."
}
```

- **Response**: `201 CREATED`
```json
{
  "id": 15,
  "contactId": 1,
  "body": "Met with client. Follow-up in 2 weeks."
}
```

---

#### `PUT /api/notes/{noteId}`

Pass the value in path param noteId: 15
- **Headers**: `Authorization: Bearer <JWT>`
- **Request Body**
```json
{
  "contactId": 1,
  "body": "Rescheduled follow-up to next week"
}
```

- **Response**: `202 Accepted`

---

#### `DELETE /api/contacts/{noteId}`
Pass the value in path param noteId: 15
- Performs soft delete.
- **Headers**: `Authorization: Bearer <JWT>`
- **Response**: `204 No Content`

---
