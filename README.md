# Dynamic API Generator 🚧

> **Status: in progress.** Schema discovery and the secured admin API work today; endpoint generation and the React admin panel are being built.

Point it at an existing PostgreSQL database and it discovers the tables and columns at runtime – the first step toward serving REST endpoints for any table without writing a controller for each one.

![Java](https://img.shields.io/badge/Java-21-orange) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4-green) ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-blue) ![Status](https://img.shields.io/badge/status-in%20progress-yellow)

## How it works

```
PostgreSQL ──JDBC DatabaseMetaData──▶ DatabaseMetadataService ──▶ TableController ──▶ /api/admin/** (ADMIN only)
                                                                         ▲
                                                         Spring Security (form login + HTTP Basic)
```

The service reads the database's own metadata (`DatabaseMetaData.getTables` / `getColumns`), so nothing about the schema is hard-coded.

## Roadmap

- [x] Connect to PostgreSQL and list all tables in the `public` schema
- [x] Read each table's columns and types
- [x] Protect the admin API with Spring Security (role-based access, BCrypt passwords)
- [ ] Generate CRUD endpoints for any discovered table
- [ ] React admin panel to browse tables and manage generated APIs
- [ ] Move the admin account from in-memory to the database

## API (admin only)

| Method | Path | Description |
|---|---|---|
| GET | `/api/admin/tables` | Names of all tables |
| GET | `/api/admin/tables/{tableName}/columns` | Columns and types of one table |

## Run it locally

**Requirements:** Java 21, PostgreSQL

```bash
createdb dynamic_api_db

export DB_URL=jdbc:postgresql://localhost:5432/dynamic_api_db
export DB_USERNAME=postgres
export DB_PASSWORD=your_password

./mvnw spring-boot:run
```

Open http://localhost:8080/api/admin/tables and sign in with the development admin account defined in `SecurityConfig`.

## Tech stack

Java 21 · Spring Boot · Spring Security · Spring JDBC · PostgreSQL · React (in progress) · Maven

## Author

**Ahmed Al-Gabri** – Computer Engineering student, Bingöl University  
[LinkedIn](https://www.linkedin.com/in/ahmed-al-gabri) · [GitHub](https://github.com/ah24aki)
