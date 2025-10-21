
# 💼 Expense Management Application

A full-stack **Expense Management System** built with **Java Spring Boot**, **JWT Authentication**, and **Angular**.  
This application allows users to manage daily expenses, categorize them, and track reports securely with role-based access.

---

## 🚀 Features

### 🔐 Authentication & Authorization
- Secure login and registration using **JWT (JSON Web Token)**.
- Role-based access control (**Admin**, **User**).
- Password encryption using **BCrypt**.

### 💰 Expense Management
- Add, update, and delete expenses.
- Filter expenses by category, date range, or user.
- Generate monthly and yearly expense summaries.

### 👤 Admin Panel
- View all registered users.
- Manage users (activate/deactivate, change roles).
- View all user expenses in one place.

### 📊 Reports & Dashboard
- Real-time data visualization using charts (Angular).
- Insights on total spend, category-wise analysis, and trends.

---

## 🧩 Tech Stack

| Layer | Technology |
|-------|-------------|
| **Frontend** | Angular 16, TypeScript, HTML5, SCSS, Tailwind CSS, Chart.js |
| **Backend** | Java 17, Spring Boot 3, Spring Security, JWT, Spring Data JPA |
| **Database** | MySQL |
| **Build Tools** | Maven |
| **Other Tools** | Postman, VS Code, IntelliJ IDEA |

---

## ⚙️ Backend Structure (`/backend`)

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/example/expensemanagement/
│   │   │   ├── config/          # Security & JWT configuration
│   │   │   ├── controller/      # REST API controllers
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── exception/       # Custom exceptions & handlers
│   │   │   ├── model/           # Entity classes (User, Expense, Category)
│   │   │   ├── repository/      # JPA repositories
│   │   │   ├── security/        # JWT filters & utilities
│   │   │   ├── service/         # Business logic services
│   │   │   └── ExpenseManagementApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql / schema.sql (optional)
│   └── test/
│       └── java/...             # Unit & Integration Tests
├── pom.xml
└── README.md
```

---

## 💻 Frontend Structure (`/frontend`)

```
frontend/
├── src/
│   ├── app/
│   │   ├── auth/
│   │   │   ├── login/
│   │   │   ├── register/
│   │   │   └── auth.service.ts
│   │   ├── services/             # API & JWT interceptor services
│   │   ├── models/               # TypeScript interfaces (User, Expense)
│   │   ├── components/
│   │   │   ├── dashboard/
│   │   │   ├── expense-list/
│   │   │   ├── expense-form/
│   │   │   └── admin-panel/
│   │   ├── guards/               # Auth guards
│   │   ├── interceptors/         # JWT interceptor
│   │   ├── pages/                # Main routes
│   │   ├── app-routing.module.ts
│   │   ├── app.component.ts/html/scss
│   │   └── app.module.ts
│   ├── assets/
│   ├── environments/
│   │   ├── environment.ts
│   │   └── environment.prod.ts
├── angular.json
├── package.json
└── README.md
```

---

## 🔧 How to Run the Project

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/<your-username>/expense-management-app.git
```

### 2️⃣ Setup Backend
```bash
cd backend
mvn clean install
mvn spring-boot:run
```
Backend runs on: **http://localhost:8080**

### 3️⃣ Setup Frontend
```bash
cd frontend
npm install
ng serve
```
Frontend runs on: **http://localhost:4200**

---

## 🔑 Default Roles
| Role | Description |
|------|--------------|
| `ROLE_USER` | Can manage only their own expenses |
| `ROLE_ADMIN` | Can view and manage all users and expenses |

---

## 🧠 JWT Authentication Flow
1. User logs in using valid credentials.  
2. Server returns a **JWT Token**.  
3. Angular stores the token in **LocalStorage**.  
4. All subsequent API calls include `Authorization: Bearer <token>`.  
5. Spring Security validates and authorizes each request.

---
