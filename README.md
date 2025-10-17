# eLibrary Backend

This is the backend for the eLibrary system, built with Java  and running on a Spring Boot.

## Main Features
* **User Accounts & Security:** Handles all user registrations, logins, and secure sessions using tokens (JWT). Users can log out anytime and reset their passwords through email.
* **Book:** Admins can add, edit, or remove books from the catalog. Users can browse, view details, and search by title or category. Each book shows available copies and total stock.
* **Checkout:** Manages the checkout process. Users can checkout, return, and renew books. The system keeps a full history of all past and current checkouts. Admins can also manage active checkouts on behalf of users.
* **Reviews and Ratings:** Users can post star ratings and written reviews for books. The system automatically calculates and displays the average rating for every book.
* **User Support Messages:** Allows users to send messages directly to admins. Admins can view, filter (Pending/Replied), reply, and delete messages.
* **Admin Management Tools:** Special features for admins to manage all user accounts, update their roles (e.g., promote a User to Admin), and delete profiles. Includes statistics for users, books, and checkouts.
* **Automatic Email Service:** Sends helpful automated emails for important actions, such as password reset links and checkout confirmations.

## Tech Stack

* **Core Structure:** Spring Boot
* **Data Storage:** JPA/Hibernate
* **Database:** MySQL
* **Security:** Spring Security & JWT
* **Email Service:** JavaMail
* **Templating Engine:** Thymeleaf
* **Build Tool:** Maven
* **Validation:** Jakarta Bean Validation
* **Pagination & Sorting:** Spring Data Pageable

## Configuration Details

The following properties should be set in your `application.properties` file.

```properties
spring.application.name=elibrary-backend

# Databae Configurations
spring.datasource.url=jdbc:mysql://localhost:3306/elibrarydb
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

# JPA Properties
spring.jpa.hibernate.ddl-auto=update
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Server Configurations
server.servlet.context-path=/api/v1

# Output Formatting
spring.output.ansi.enabled=ALWAYS

# JWT Secret Key
jwt.secret=${JWT_SECRET}
jwt.token.expiration=${JWT_TOKEN-EXPIRATION}

# Gmail Config
spring.mail.host=${MAIL_HOST}
spring.mail.port=587
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true

# Frontend Route
frontend.url=${FRONTEND_URL}

# Token Expiry Duration
password.reset.token.expiry=${RESET_TOKEN_EXPIRY}
```


### What You Need to Get Started

* Java Development Kit (JDK 17+)
* Maven
* MySQL

### Setup Steps

1. Get the Code

    ```bash
    git clone https://github.com/khalid-nur/eLibrary-backend.git
    cd eLibrary-backend
    ```

2.  Update your `application.properties`

3.  Run the Server

    The application will start up on port **8080**

## API Guide

Here’s an overview of the main feature APIs

| What You Want to Do | Action (Method / Path) | Who Can Access |
| :--- | :--- | :--- |
| **Sign Up / Login** | `POST /auth/register or  POST /auth/login` | Anyone (**Public**) |
| **Forgot Password (Email Reset Link)** | `POST /auth/forgot-password?email={email}` | Anyone (**Public**) |
| **Reset Password** | `POST /auth/reset-password?token={token}&newPassword={newPassword}` | Anyone (**Public**) |
| **Checkout a Book** | `PUT /checkouts` | **Users** & **Admins** |
| **View All Books** | `GET /books` | Anyone (**Public**) |
| **Post a Review** | `POST /reviews` | **Users** & **Admins** |
| **Admin: Manage Users** | `GET /admin/users` | **Admins Only** |
| **Admin: Mark Book Returned** | `PUT /checkouts/admin/return` | **Admins Only** |






