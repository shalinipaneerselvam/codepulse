# CodePulse

A web-based coding practice platform built with Spring Boot that helps users practice programming problems, track solved problems, and monitor their learning progress.

## 🚀 Features

* User Registration and Login
* Secure password encryption using BCrypt
* Personalized Dashboard
* Coding Practice Problems
* Basic Programming Problems
* Array Practice – 7 Problems
* String Practice – 7 Problems
* Problem Completion Tracking
* Database-based Progress Tracking
* Solved Problem Status
* Overall Progress
* Current Streak Tracking
* Responsive and Clean UI

## 🛠️ Technologies Used

* Java 17
* Spring Boot 3.5.6
* Spring Security
* Spring Data JPA
* Hibernate
* MySQL 8
* Thymeleaf
* HTML
* CSS
* Maven

## 📂 Project Structure

```text
CodePulse/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/codepulse/codepulse/
│   │   │       ├── controller/
│   │   │       ├── entity/
│   │   │       ├── repository/
│   │   │       └── service/
│   │   │
│   │   └── resources/
│   │       ├── templates/
│   │       └── application.properties
│   │
│   └── test/
│
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## 🔐 Authentication

CodePulse uses Spring Security for authentication.

User passwords are stored using BCrypt password hashing instead of storing plain-text passwords.

## 📊 Progress Tracking

The application stores user progress in MySQL.

For each completed problem, CodePulse records:

* User
* Problem
* Completion date

This allows the application to display:

* Problems Solved
* Category Progress
* Overall Progress
* Current Streak
* Completed Problem Status

## 🧩 Practice Categories

### Basic Programming

Includes fundamental programming problems such as:

* Largest Number
* Factorial
* Fibonacci
* Prime Number
* Palindrome
* Reverse Number
* Second Largest

### Array Practice

Includes:

* Find Minimum and Maximum
* Reverse Array
* Second Largest Element
* Linear Search
* Even and Odd Elements
* Sum of Array
* Average of Array

### String Practice

Includes:

* Reverse a String
* String Palindrome
* Vowels and Consonants
* Count Characters
* Remove Spaces
* Check Anagram
* Duplicate Characters

## ⚙️ How to Run

### 1. Clone the repository

```bash
git clone https://github.com/shalinipaneerselvam/codepulse.git
```

### 2. Open the project

Open the project in IntelliJ IDEA or another Java IDE.

### 3. Configure MySQL

Create a database:

```sql
CREATE DATABASE codepulse;
```

Update the database configuration in:

```text
src/main/resources/application.properties
```

Use your own MySQL username and password.

### 4. Run the application

Using Maven:

```bash
mvn spring-boot:run
```

Or on Windows:

```bash
mvnw.cmd spring-boot:run
```

### 5. Open in browser

```text
http://localhost:8080
```

## 🔮 Future Improvements

* Add more DSA problems
* Add difficulty levels
* Add coding editor with code execution
* Add test cases
* Add leaderboard
* Add user profile
* Add achievements and badges
* Add advanced progress analytics

## 👩‍💻 Author

**Shalini P.**

B.Tech Information Technology Student

GitHub: https://github.com/shalinipaneerselvam

---

⭐ If you find this project useful, consider giving it a star.
