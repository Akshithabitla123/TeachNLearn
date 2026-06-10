# TeachNLearn

A peer-to-peer skill exchange platform that enables users to learn and teach skills through session-based mentoring.
Users can create skill offerings, discover mentors, book sessions, manage bookings, reschedule meetings, and provide reviews after completing sessions.

---

## Features

### Authentication & Security

* JWT-based Authentication
* Spring Security Integration
* BCrypt Password Encryption
* Role-Based Access Control (RBAC)
* Secure Protected APIs

### User Management

* User Registration & Login
* Profile Management
* Mentor Profiles
* Skill-based User Discovery

### Skill Management

* Create Skills
* View Skills
* Search Skills
* Trending Skills
* Mentor Skill Listings

### Booking System

* Session Booking Requests
* Booking Status Tracking
* Accept/Reject Booking Requests
* Session Completion Tracking
* Booking History

### Rescheduling System

* Mentor can propose alternative slots
* Learner can accept any one of the proposed slots
* Automatic session update after acceptance

### Review & Rating System

* Session Feedback
* Star Ratings
* Mentor Rating Calculation
* Public Reviews

### Admin Features

* User Management
* Role-Based Access Restrictions
* Protected Admin APIs

---

## Tech Stack

### Backend

* Java 17
* Spring Boot
* Spring Security
* JWT Authentication
* Spring Data JPA
* Hibernate
* Maven

### Database

* MySQL

### Frontend

* HTML
* CSS
* JavaScript

### Tools

* Git
* GitHub
* Postman

---

## Architecture

```text
Frontend (HTML/CSS/JavaScript)
           |
           v
Spring Boot REST APIs
           |
           v
Spring Security + JWT
           |
           v
Service Layer
           |
           v
JPA / Hibernate
           |
           v
MySQL Database
```

---

## Database Entities

### User

* id
* name
* email
* password
* bio
* profileImage
* github
* linkedIn
* role
* rating

### Skill

* id
* title
* description
* category
* experienceLevel
* price
* user

### Booking

* id
* mentor
* student
* skill
* sessionDate
* status
* message

### Rating

* id
* learner
* mentor
* stars
* comment

### RescheduleRequest

* id
* booking
* slot1
* slot2
* slot3
* acceptedSlot

---

## API Modules

### Authentication APIs

* Register User
* Login User
* JWT Generation

### User APIs

* View Profile
* Update Profile
* Get Mentor Details

### Skill APIs

* Create Skill
* Get Skill By Id
* Search Skills
* Get User Skills
* Trending Skills

### Booking APIs

* Create Booking
* Update Status of Booking
* View Booking
* Booking History

### Reschedule APIs

* Create Reschedule Request
* Propose New Slots
* Accept Slot
* Reject Request

### Rating APIs

* Add Review
* View Mentor Reviews
* Calculate Ratings

---

## Security

TeachNLearn uses JWT Authentication for securing APIs.

### Authentication Flow

1. User logs in.
2. Backend validates credentials.
3. JWT token is generated.
4. Token is stored in browser localStorage.
5. Protected API requests include:

```http
Authorization: Bearer <JWT_TOKEN>
```

6. JwtFilter validates the token before granting access.

---

## Role-Based Access Control

### USER

* Create Skills
* Book Sessions
* Manage Own Profile
* Submit Reviews

### ADMIN

* Manage Users
* Access Admin APIs

---

## Author

**Akshitha Bitla**

GitHub: https://github.com/Akshithabitla123
