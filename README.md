
# Employee Management Application

A full-stack employee management system with authentication, JWT security, user registration, and password reset functionality.

## Features

* User registration with strong password enforcement
* Login with JWT authentication
* Role-based access control (default: `ROLE_USER`, admin support possible)
* Protected REST endpoints for employee management
* Password reset workflow:

  * Forgot password endpoint (`/auth/password/forgot`)
  * Email with secure token
  * Password reset endpoint (`/auth/password/reset`)
  * Change password for authenticated users (`/me/password`)
* React frontend with:

  * Login and registration forms
  * Employee CRUD pages
  * Shared styling for auth and employee forms
  * Password strength checklist on registration/reset forms

---

## Tech Stack

### Backend

* Java 21+
* Spring Boot 3
* Spring Security 6
* JWT (`jjwt 0.11.5`)
* Hibernate / JPA
* Spring Mail for password reset emails
* H2/MySQL/Postgres (configurable)

### Frontend

* React 18
* Axios for API requests
* React Router for routing
* Bootstrap-based styling with custom CSS

---

## Getting Started

### Backend

#### Prerequisites

* Java 21+
* Maven 3.9+
* A running database (H2 in-memory works out of the box)

#### Setup

1. Clone the repository.
2. Configure your `application.properties` (for DB and mail):

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

app.frontend.reset-url=http://localhost:5173/reset-password
app.reset.ttl-minutes=30
```

3. Build and run:

```bash
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`.

---

### Frontend

#### Prerequisites

* Node.js 18+
* npm or yarn

#### Setup

1. Navigate to the `frontend/` folder.
2. Install dependencies:

```bash
npm install
```

3. Start the dev server:

```bash
npm run dev
```

The frontend will be available at `http://localhost:5173`.

---

## API Endpoints

### Authentication

* `POST /auth/register` – Register a new user
* `POST /auth/login` – Login, returns JWT
* `POST /auth/password/forgot` – Request password reset (sends email)
* `POST /auth/password/reset` – Reset password with token

### Account

* `POST /me/password` – Change password (authenticated)

### Employees

* `GET /employees` – List employees
* `GET /employees/{id}` – Get employee by ID
* `POST /employees` – Create employee
* `PUT /employees/{id}` – Update employee
* `DELETE /employees/{id}` – Delete employee

---

## Security

* All `/auth/**` endpoints are public.
* All other endpoints require authentication via JWT in the `Authorization` header:

```
Authorization: Bearer <token>
```

* Passwords are stored with BCrypt hashing.
* Strong password enforcement (min 8 chars, upper, lower, digit, special).

---

## Password Reset Flow

1. User requests reset via `/auth/password/forgot`.
2. System emails a tokenized link (valid for 30 minutes).
3. User clicks the link, enters a new password on the frontend.
4. Frontend calls `/auth/password/reset` with the token and new password.
5. User receives confirmation email.

---

## Development Notes

* React frontend uses shared CSS (`auth.css`) for consistent design across login, register, and employee forms.
* Backend security configured with `SecurityFilterChain` and a custom `JwtFilter`.
* Add roles (`ROLE_ADMIN`) for advanced authorization rules if needed.
* Ensure environment variables or secrets are used for production mail/passwords.


