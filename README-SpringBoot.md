# EduVerse User Services - Spring Boot

## Overview

This is the fully converted Java Spring Boot version of the EduVerse User Services microservice. The application has been migrated from Node.js/Express to Spring Boot while maintaining all original functionality.

## Tech Stack

- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Database**: MySQL with JPA/Hibernate
- **Authentication**: JWT (JSON Web Tokens)
- **Security**: Spring Security
- **Email Service**: Spring Mail
- **Build Tool**: Maven

## Features

- **User Authentication**: Secure registration, login, OTP verification
- **Profile Management**: Student and Teacher profile management
- **Role-Based Access Control**: STUDENT and TEACHER roles
- **Password Recovery**: Secure password reset with email tokens
- **Quiz Management**: Create, update, delete quizzes (Teachers)
- **Assignment Management**: Create and manage assignments (Teachers)
- **Study Notes**: Educational content management (Teachers)
- **Quiz Results**: Track student quiz performance and results
- **Email Notifications**: OTP and password reset emails

## API Endpoints

### Authentication (`/api/auth`)
- `POST /register` - Register new user
- `POST /login` - User login
- `POST /verify-otp` - Verify email OTP
- `POST /forgot-password` - Request password reset
- `POST /reset-password` - Reset password with token

### User Management (`/api/user`)
- `GET /me` - Get current user profile
- `PUT /me` - Update current user
- `DELETE /me` - Delete current user account
- `GET /` - Get all users (Admin)
- `GET /{id}` - Get user by ID
- `PUT /{id}` - Update user by ID
- `DELETE /{id}` - Delete user by ID
- `GET /search` - Search users

### Profile Management (`/api/profile`)
- `POST /student` - Create/Update student profile
- `GET /student/me` - Get my student profile
- `GET /students` - Get all student profiles
- `POST /teacher` - Create/Update teacher profile
- `GET /teacher/me` - Get my teacher profile
- `GET /teachers` - Get all teacher profiles
- `GET /teachers/specialization/{spec}` - Get teachers by specialization
- `GET /teachers/top-rated` - Get top-rated teachers

### Quiz Management (`/api/quiz`)
- `POST /` - Create quiz (Teachers)
- `GET /` - Get all quizzes
- `GET /{id}` - Get quiz by ID
- `GET /teacher/me` - Get my quizzes (Teachers)
- `PUT /{id}` - Update quiz
- `DELETE /{id}` - Delete quiz
- `GET /search` - Search quizzes

### Assignment Management (`/api/assignment`)
- `POST /` - Create assignment (Teachers)
- `GET /` - Get all assignments
- `GET /{id}` - Get assignment by ID
- `GET /teacher/me` - Get my assignments (Teachers)
- `PUT /{id}` - Update assignment
- `DELETE /{id}` - Delete assignment
- `GET /search` - Search assignments

### Study Notes (`/api/studynote`)
- `POST /` - Create study note (Teachers)
- `GET /` - Get all study notes
- `GET /{id}` - Get study note by ID
- `GET /teacher/me` - Get my study notes (Teachers)
- `PUT /{id}` - Update study note
- `DELETE /{id}` - Delete study note
- `GET /search` - Search study notes

### Quiz Results (`/api/result`)
- `POST /` - Create quiz result (Students)
- `GET /` - Get all quiz results
- `GET /{id}` - Get quiz result by ID
- `GET /student/me` - Get my quiz results (Students)
- `GET /lesson/{lessonId}` - Get results by lesson
- `GET /course/{courseId}` - Get results by course
- `GET /student/me/average` - Get my average marks
- `PUT /{id}` - Update quiz result
- `DELETE /{id}` - Delete quiz result

### Health Check (`/api/health`)
- `GET /health` - Service health status

## Environment Configuration

Configure the following environment variables in `application.yml`:

```yaml
server:
  port: 5000

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/eduverse
    username: your_db_username
    password: your_db_password
    
  mail:
    host: smtp.gmail.com
    port: 587
    username: your_email@gmail.com
    password: your_email_password

jwt:
  secret: your_jwt_secret_key
  expiration: 2592000000 # 30 days
```

## Running the Application

1. **Prerequisites**:
   - Java 17 or higher
   - Maven 3.6+
   - MySQL 8.0+

2. **Database Setup**:
   ```sql
   CREATE DATABASE eduverse;
   ```

3. **Build and Run**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Access the API**:
   - Base URL: `http://localhost:5000`
   - Health Check: `http://localhost:5000/api/health`

## Security

- JWT-based authentication
- Role-based access control (STUDENT, TEACHER)
- Password encryption using BCrypt
- CORS enabled for cross-origin requests
- Secure email OTP verification

## Database Schema

The application uses JPA/Hibernate for ORM with the following main entities:
- User (with roles)
- StudentProfile
- TeacherProfile
- Quiz
- Question
- Assignment
- StudyNote
- QuizResult
- AnsweredQuestion

## Migration Notes

This Spring Boot version maintains 100% feature parity with the original Node.js version:
- All API endpoints preserved
- Same authentication flow
- Identical database schema
- Same business logic
- Compatible request/response formats

The conversion includes modern Spring Boot best practices:
- Proper layered architecture (Controller → Service → Repository)
- Comprehensive validation
- Exception handling
- Security configuration
- Email service integration
