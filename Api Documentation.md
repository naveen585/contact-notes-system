
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
- Soft delete is preferred over hard delete for traceability.

---

##  What I'd Improve with More Time

| Area | Improvement                                                                                                                          |
|------|--------------------------------------------------------------------------------------------------------------------------------------|
| **Security** | Implementation of role-based access (Admin/User), token expiry refresh handling, brute-force prevention.                             |
| **Testing** | Add end-to-end tests using Testcontainers (for MySQL).As of now the code coverage is 38%, if possible should increase code coverage. |
| **Monitoring** | Integration of Splunk used for logging and monitor the health of endpoints.                                                          |
| **Frontend** | Build a Angular interface to manage contacts and notes visually.                                                                     |
| **Search** | Able to implement the filter like full-text search for notes using ElasticSearch or native MySQL FT index.                           |
| **Pagination** | Introducing paging and sorting for contacts and notes list endpoints.                                                                |
| **Retry Handling** | Event handling by implmeneting the Retry handling so that we can able to retry the event once it is failed.                          |
| **Analytics** | Implementing the analytics api so that we can visually view the events in a table. And if possible would have implmeneted in RabbitMQ.|          
| **Docker** | Instead of using MySQL locally should have used docker image so that within the application itself we can integrate the db.                        |

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

![Screenshot (567)](https://github.com/user-attachments/assets/002dc848-990b-4cea-8679-b4800830ca50)
![Screenshot (568)](https://github.com/user-attachments/assets/c0a4f7eb-982d-4133-bb33-162a6471da31)

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
![Screenshot (569)](https://github.com/user-attachments/assets/1cf6e8b8-9050-4609-901a-1d1d11c35bd1)

---

### Contacts

#### `GET /api/contacts/`
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
![Screenshot (571)](https://github.com/user-attachments/assets/a12bcf67-1919-4866-af79-b22bc2939fab)
![Screenshot (577)](https://github.com/user-attachments/assets/95f82534-4233-4c36-8549-faaa28952a90)

---

#### `POST /api/contacts/`

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
![Screenshot (570)](https://github.com/user-attachments/assets/f4e4bf42-eda0-420d-86c1-b970a50c303f)

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
![Screenshot (573)](https://github.com/user-attachments/assets/ee747e89-a380-4e42-a221-d2d74453b719)

---

#### `DELETE /api/contacts/{id}`
Pass the value in path param id: 1
- Performs soft delete.
- **Headers**: `Authorization: Bearer <JWT>`
- **Response**: `204 No Content`
![Screenshot (580)](https://github.com/user-attachments/assets/ef01c9df-f7a1-49ea-82c2-786428665543)

---

### Notes

#### `GET /api/notes/contact/{contactId}`
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
![Screenshot (574)](https://github.com/user-attachments/assets/796c65c1-ead4-443c-825e-5317e649c863)
![Screenshot (576)](https://github.com/user-attachments/assets/dcf898a3-e7ba-497b-a9d7-b669b68a78f9)

---

#### `POST /api/notes/`

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
![Screenshot (575)](https://github.com/user-attachments/assets/9f64057e-b115-4dde-ba24-3490a2ac33e3)



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
![Screenshot (578)](https://github.com/user-attachments/assets/ec56b38e-08d4-41ab-9986-0d768a535499)

---

#### `DELETE /api/contacts/{noteId}`
Pass the value in path param noteId: 15
- Performs soft delete.
- **Headers**: `Authorization: Bearer <JWT>`
- **Response**: `204 No Content`
![Screenshot (579)](https://github.com/user-attachments/assets/2dad23a8-d244-4b67-807c-56332e644bbc)

---

#### Database view:
![Screenshot (581)](https://github.com/user-attachments/assets/6f006681-ea6a-42be-8ec6-b8dae72c7f90)
