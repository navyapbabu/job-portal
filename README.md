# Job Portal REST API

A full-featured Job Portal backend REST API built with Java Spring Boot, JWT Authentication, and MySQL.

## 🚀 Live Demo
API Documentation: [Swagger UI](https://job-portal-production-aec2.up.railway.app/swagger-ui/index.html)
## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java 17+ | Programming language |
| Spring Boot 4.x | Backend framework |
| Spring Security | Authentication & Authorization |
| JWT | Token-based authentication |
| Spring Data JPA | Database ORM |
| Hibernate | ORM implementation |
| MySQL | Database |
| Swagger/OpenAPI | API Documentation |
| Maven | Build tool |
| Lombok | Reduce boilerplate code |

## ✨ Features

- ✅ JWT Authentication (Register & Login)
- ✅ Role-based access control (EMPLOYER & JOBSEEKER)
- ✅ BCrypt password encryption
- ✅ Job CRUD operations
- ✅ Job search by title and location
- ✅ Job application system
- ✅ Application status tracking (APPLIED, REVIEWED, ACCEPTED, REJECTED)
- ✅ Swagger API documentation

## 📋 API Endpoints

### Authentication
| Method | URL | Access | Description |
|---|---|---|---|
| POST | `/api/auth/register` | Public | Register new user |
| POST | `/api/auth/login` | Public | Login and get JWT token |

### Jobs
| Method | URL | Access | Description |
|---|---|---|---|
| POST | `/api/jobs` | Employer | Post a new job |
| GET | `/api/jobs` | Public | Get all jobs |
| GET | `/api/jobs/{id}` | Public | Get job by ID |
| GET | `/api/jobs/my-jobs` | Employer | Get my posted jobs |
| PUT | `/api/jobs/{id}` | Employer | Update a job |
| DELETE | `/api/jobs/{id}` | Employer | Delete a job |
| GET | `/api/jobs/search/title` | Public | Search by title |
| GET | `/api/jobs/search/location` | Public | Search by location |

### Applications
| Method | URL | Access | Description |
|---|---|---|---|
| POST | `/api/applications/apply/{jobId}` | Jobseeker | Apply for a job |
| GET | `/api/applications/my-applications` | Jobseeker | View my applications |
| GET | `/api/applications/job/{jobId}` | Employer | View job applicants |
| PUT | `/api/applications/{id}/status` | Employer | Update application status |
| DELETE | `/api/applications/{id}` | Jobseeker | Withdraw application |

## 🗄️ Database Schema

## ⚙️ Setup & Installation

### Prerequisites
- Java 17+
- MySQL 5.5+
- Maven

### 1. Clone the repository
```bash
git clone https://github.com/navyapbabu/job-portal.git
cd job-portal
```

### 2. Create MySQL database
```sql
CREATE DATABASE job_portal_db;
CREATE USER 'jobportal_user'@'localhost' IDENTIFIED BY 'yourpassword';
GRANT ALL PRIVILEGES ON job_portal_db.* TO 'jobportal_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Create tables
```sql
USE job_portal_db;

CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('JOBSEEKER', 'EMPLOYER') NOT NULL,
    created_at DATETIME,
    PRIMARY KEY (id)
);

CREATE TABLE jobs (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(2000) NOT NULL,
    company VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    salary VARCHAR(255) NOT NULL,
    job_type VARCHAR(255) NOT NULL,
    status ENUM('OPEN', 'CLOSED') NOT NULL,
    employer_id BIGINT NOT NULL,
    created_at DATETIME,
    PRIMARY KEY (id),
    FOREIGN KEY (employer_id) REFERENCES users(id)
);

CREATE TABLE job_applications (
    id BIGINT NOT NULL AUTO_INCREMENT,
    job_id BIGINT NOT NULL,
    applicant_id BIGINT NOT NULL,
    status ENUM('APPLIED','REVIEWED','ACCEPTED','REJECTED') NOT NULL,
    applied_at DATETIME,
    PRIMARY KEY (id),
    FOREIGN KEY (job_id) REFERENCES jobs(id),
    FOREIGN KEY (applicant_id) REFERENCES users(id),
    UNIQUE KEY unique_application (job_id, applicant_id)
);
```

### 4. Configure application properties
Copy `application.properties.example` to `application.properties` and fill in your values:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/job_portal_db
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
jwt.secret=your_jwt_secret_key_minimum_32_characters
jwt.expiration=86400000
```

### 5. Run the project
```bash
mvn spring-boot:run
```

### 6. Access Swagger UI

http://localhost:8080/swagger-ui/index.html

## 🔐 How to use JWT in Swagger

1. Open Swagger UI
2. Click **Authorize** button (top right)
3. Enter: `Bearer your_jwt_token_here`
4. Click **Authorize**
5. Now all protected endpoints will use your token!

## 📱 Sample API Requests

### Register as Employer
```json

POST /api/auth/register
{
  "fullName": "John Employer",
  "email": "john@company.com",
  "password": "john123",
  "role": "EMPLOYER"
}
```

### Post a Job
```json
POST /api/jobs
Authorization: Bearer <token>
{
  "title": "Java Backend Developer",
  "description": "Looking for experienced Java developer",
  "company": "Tech Solutions",
  "location": "Bangalore",
  "salary": "6-8 LPA",
  "jobType": "Full-time"
}
```

### Apply for a Job

## 👩‍💻 Author
**Navya** — [GitHub](https://github.com/navyapbabu)