<div align="center">

# 🚀 AgencyFlow CRM

**Enterprise-grade Modular Monolith CRM & Delivery Platform for Software Development Agencies**

[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/technologies/downloads/#java21)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.6-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Modulith](https://img.shields.io/badge/Spring_Modulith-Modular_Monolith-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-modulith)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Flyway](https://img.shields.io/badge/Flyway-Migrations-CC0200?style=for-the-badge&logo=flyway&logoColor=white)](https://flywaydb.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![JWT](https://img.shields.io/badge/Security-Stateless_JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)](https://jwt.io/)
[![OpenAPI](https://img.shields.io/badge/OpenAPI-3.0_Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)](http://localhost:8080/swagger-ui/index.html)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)](LICENSE)

<p align="center">
  <em>An internal CRM backend tailored for software agencies to streamline the entire client journey: from incoming lead qualification and atomic customer conversion to commercial deal closing and production project task execution.</em>
</p>

</div>

---

## 📑 Table of Contents

- [Overview](#-overview)
- [Key Business Capabilities](#-key-business-capabilities)
- [Canonical Business Lifecycle & State Machines](#-canonical-business-lifecycle--state-machines)
- [System Architecture](#-system-architecture)
- [Database Schema & Migrations](#-database-schema--migrations)
- [Security & RBAC](#-security--rbac)
- [REST API Reference](#-rest-api-reference)
- [Technology Stack](#-technology-stack)
- [Getting Started](#-getting-started)
  - [Prerequisites](#prerequisites)
  - [Environment Setup](#environment-setup)
  - [Docker Services](#docker-services)
  - [Running the Application](#running-the-application)
- [Default Seed Accounts](#-default-seed-accounts)
- [Testing & Quality Assurance](#-testing--quality-assurance)
- [Project Directory Layout](#-project-directory-layout)
- [Contributing](#-contributing)
- [License](#-license)
- [Author & Acknowledgments](#-author--acknowledgments)

---

## 💡 Overview

Software engineering and digital agencies face a distinct operational challenge: **sales pipelines are inherently disconnected from technical delivery**. Traditional CRMs treat winning a deal as the final step, forcing development teams to manually recreate client context, project scopes, and requirements across disjointed issue trackers.

**AgencyFlow CRM** solves this problem by providing a cohesive, end-to-end backend platform designed specifically for agency workflows:
- Tracks prospects and leads through disciplined qualification stages.
- Enforces an **atomic Lead-to-Customer conversion** transaction.
- Manages commercial deals with explicit revenue tracking and status flows.
- Automatically transitions **Won Deals into active Delivery Projects**.
- Breaks down client commitments into assignable engineering tasks with deadlines and priorities.
- Maintains unified discussions across all domain entities via a polymorphic commenting engine.
- Captures an immutable, actor-attributed **Audit Log** with JSONB change snapshots for full organizational accountability.

Architecturally, the project is structured as an intentional **Modular Monolith** using **Spring Modulith**. It guarantees strict module boundaries, high cohesion, and compile-time architectural verification without introducing the latency, network unreliability, distributed transaction nightmares, or operational overhead of microservices.

---

## ✨ Key Business Capabilities

| Feature | Description |
|---|---|
| **Lead Qualification** | Track incoming leads, assign dedicated Sales Managers, and advance leads through `NEW` ➔ `CONTACTED` ➔ `QUALIFIED`. |
| **Strict Customer Conversion** | Enforces a strict domain invariant: customers cannot be manually created via an arbitrary public endpoint. Every customer record originates from a qualified lead conversion executed in an atomic transaction. |
| **Deal Pipeline** | Track commercial proposals, deal valuations (USD), and negotiation stages (`PROPOSAL` ➔ `NEGOTIATION` ➔ `WON` / `LOST`). |
| **Delivery Projects** | Seamless 1:1 transition from a `WON` deal to an active project, binding account responsibility (Sales Manager) with engineering delivery (Team Lead). |
| **Task Management** | Granular task tracking under projects with status (`TODO`, `IN_PROGRESS`, `DONE`), priorities (`LOW`, `MEDIUM`, `HIGH`), assignees, and target due dates. |
| **Polymorphic Comments** | Contextual conversations attached to exactly one target entity (Lead, Deal, Project, or Task) guaranteed by database constraints. |
| **Complete Auditability** | Automatic actor tracking via Spring Security Auditing (`created_by`, `updated_by`, timestamps) and system-wide JSONB audit logs for critical mutations. |
| **Stateless Security** | High-performance JWT authentication with BCrypt hashing and role-based authorities (`ADMIN`, `SALES_MANAGER`, `TEAM_LEAD`). |
| **Schema-First Migrations** | Zero-downtime, fully versioned Flyway database migrations with Hibernate schema validation (`ddl-auto: validate`). |

---

## 🔄 Canonical Business Lifecycle & State Machines

AgencyFlow CRM enforces strict, domain-driven state transitions throughout the lifecycle of an engagement:

```text
 ┌─────────────────────────────────────────────────────────────┐
 │                         LEAD MODULE                         │
 │                                                             │
 │   [NEW] ────────► [CONTACTED] ────────► [QUALIFIED]         │
 │     │                  │                     │              │
 │     ▼                  ▼                     ▼              │
 │ [REJECTED]       [REJECTED]          [CONVERT TO CUSTOMER]  │
 └──────────────────────────────────────────────┬──────────────┘
                                                │ (Atomic Transaction)
                                                ▼
 ┌─────────────────────────────────────────────────────────────┐
 │                       CUSTOMER MODULE                       │
 │                                                             │
 │                     Active Customer Record                  │
 │                               │                             │
 └───────────────────────────────┼─────────────────────────────┘
                                 │ Create Deal
                                 ▼
 ┌─────────────────────────────────────────────────────────────┐
 │                         DEAL MODULE                         │
 │                                                             │
 │   [PROPOSAL] ────────► [NEGOTIATION] ────────► [WON]        │
 │                                │                 │          │
 │                                ▼                 │          │
 │                              [LOST]              │ (1:1)    │
 └──────────────────────────────────────────────────┼──────────┘
                                                    │ Transition
                                                    ▼
 ┌─────────────────────────────────────────────────────────────┐
 │                       PROJECT MODULE                        │
 │                                                             │
 │  [PLANNING] ◄──► [IN_PROGRESS] ◄──► [ON_HOLD] ──► [COMPLETED]
 │        │                │                                   │
 │        └────────────────┴───────────► [CANCELLED]           │
 └──────────────────────────────────────────────┬──────────────┘
                                                │ Contains
                                                ▼
 ┌─────────────────────────────────────────────────────────────┐
 │                        TASKS MODULE                         │
 │                                                             │
 │         [TODO] ────────► [IN_PROGRESS] ────────► [DONE]     │
 │         Priority: LOW | MEDIUM | HIGH                       │
 └─────────────────────────────────────────────────────────────┘
```

### 1. Lead Lifecycle & Atomic Conversion
- **States**: `NEW`, `CONTACTED`, `QUALIFIED`, `CONVERTED` (terminal), `REJECTED` (terminal).
- **Invariants**:
  - Leads can only transition forward or be marked `REJECTED`.
  - Only `QUALIFIED` leads may be converted.
  - Conversion is **strictly atomic**: inserts the new `Customer`, updates the `Lead` status to `CONVERTED`, populates `converted_customer_id` and `converted_at`, and commits both changes in a single database transaction.
  - Converted leads are retained forever for historical auditing; they are never deleted.

### 2. Deal Lifecycle
- **States**: `PROPOSAL`, `NEGOTIATION`, `WON` (terminal), `LOST` (terminal).
- **Rules**:
  - Deals must belong to an existing active Customer and be managed by a `SALES_MANAGER`.
  - `estimated_value` must be greater than zero (`NUMERIC(12, 2)` in USD).
  - Terminal deals cannot be reopened. If an existing client requests new work, a new Deal is initiated.

### 3. Project & Task Lifecycle
- **Project States**: `PLANNING`, `IN_PROGRESS`, `ON_HOLD`, `COMPLETED`, `CANCELLED`.
- **Ownership**: Every project links its commercial owner (`sales_manager_id`) with its technical delivery lead (`team_lead_id`).
- **Task Pipeline**:
  - Statuses: `TODO` ➔ `IN_PROGRESS` ➔ `DONE`.
  - Priorities: `LOW`, `MEDIUM`, `HIGH`.
  - Assigned to agency team members with optional due dates.

### 4. Polymorphic Comment Model
Comments use explicit foreign keys with a database `CHECK` constraint ensuring that **exactly one** target entity is referenced:
```sql
CHECK (
  (lead_id IS NOT NULL AND deal_id IS NULL AND project_id IS NULL AND task_id IS NULL) OR
  (lead_id IS NULL AND deal_id IS NOT NULL AND project_id IS NULL AND task_id IS NULL) OR
  (lead_id IS NULL AND deal_id IS NULL AND project_id IS NOT NULL AND task_id IS NULL) OR
  (lead_id IS NULL AND deal_id IS NULL AND project_id IS NULL AND task_id IS NOT NULL)
)
```

---

## 🏛 System Architecture

AgencyFlow CRM is built as a **Spring Modulith** modular monolith. Modules communicate across clean internal boundaries while residing in a single, easily deployable artifact.

```text
                             ┌──────────────────────────────┐
                             │       REST / OpenAPI 3       │
                             └──────────────┬───────────────┘
                                            │
               ┌────────────────────────────┼────────────────────────────┐
               │                            │                            │
             [Auth]                       [User]                     [Security]
               │                            │                            │
               └────────────────────────────┼────────────────────────────┘
                                            │
                                  Spring Modulith Engine
                                            │
    ┌──────────┬──────────────┬─────────────┼─────────────┬────────────┬───────────┐
    │          │              │             │             │            │           │
  [Lead]  [Customer]        [Deal]      [Project]       [Task]     [Comment]    [Audit]
    │          │              │             │             │            │           │
    └──────────┴──────────────┴─────────────┼─────────────┴────────────┴───────────┘
                                            │
                               Spring Data JPA / Hibernate
                                    (ddl-auto: validate)
                                            │
                               Flyway Database Migrations
                                            │
                                      PostgreSQL 17
```

### Module Layering Standard
Every domain module strictly adheres to a clean 4-tier layering pattern:
1. **Controller Layer**: Exposes `/api/v1/*` contracts, validates incoming DTOs using Jakarta Bean Validation, and returns response DTOs. Never exposes JPA entities.
2. **Service Layer**: Owns domain rules, state machines, and `@Transactional` boundaries.
3. **Repository Layer**: Extends Spring Data `JpaRepository`, handling persistence and soft-delete filtering (`deleted = false`).
4. **Mapping Layer**: Utilizes compiled **MapStruct** mappers for type-safe, zero-overhead conversion between JPA Entities and Java Record DTOs.

---

## 🗄 Database Schema & Migrations

The database schema is managed exclusively by **Flyway**. Hibernate schema generation is disabled (`ddl-auto: validate`) to ensure deterministic and auditable migrations across all environments.

```
src/main/resources/db/migration/
├── V1__create_enum_types.sql           # PostgreSQL native ENUMs
├── V2__create_users_table.sql          # Employee accounts & roles
├── V3__create_customers_table.sql      # Converted clients
├── V4__create_leads_table.sql          # Prospects & conversion links
├── V5__create_deals_table.sql          # Sales pipeline & valuation
├── V6__create_projects_table.sql       # Won delivery engagements
├── V7__create_tasks_table.sql          # Granular delivery tasks
├── V8__create_comments_table.sql       # Polymorphic contextual discussion
├── V9__create_audit_logs_table.sql     # JSONB audit trail
├── V10__create_indexes.sql             # Performance & foreign key indexes
├── V11__insert_admin_user.sql          # Seed system administrator
└── V12__insert_sales_manager_user.sql  # Seed sales manager
```

### Soft-Delete & Auditing Strategy
- **Soft Delete**: Core domain entities (`leads`, `customers`, `deals`, `projects`, `tasks`) implement soft deletion via `deleted BOOLEAN NOT NULL DEFAULT FALSE` and `deleted_at TIMESTAMP`.
- **Hard Delete**: `comments` are purged upon deletion.
- **Immutable Retention**: `audit_logs` are never deleted.
- **Audit Fields**: Every auditable entity inherits `created_by`, `updated_by`, `created_at`, and `updated_at`, populated automatically through Spring Data JPA Auditing and `SecurityContextHolder`.

---

## 🔒 Security & RBAC

AgencyFlow CRM enforces a stateless, token-based security architecture:

- **Stateless Sessions**: `SessionCreationPolicy.STATELESS` — no server-side sessions, cookies, or session hijacking vulnerabilities.
- **JWT Authentication**: Clients authenticate via `POST /api/v1/auth/login` and receive a signed JWT access token transmitted via the `Authorization: Bearer <token>` header.
- **Password Protection**: Passwords are encrypted using high-cost **BCrypt** hashing.
- **Public Endpoints**:
  - `/api/v1/auth/login`
  - `/swagger-ui/**`, `/swagger-ui.html`
  - `/v3/api-docs/**`
- **Employee Roles**:
  - `ADMIN`: User management, system configuration, audit review.
  - `SALES_MANAGER`: Lead generation, customer conversion, deal negotiation.
  - `TEAM_LEAD`: Project architecture, sprint planning, task assignment, technical delivery.

---

## 📡 REST API Reference

All business APIs are prefixed with `/api/v1`. Protected endpoints require `Authorization: Bearer <token>`.

### Authentication (`/api/v1/auth`)
| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `POST` | `/api/v1/auth/login` | Authenticate with email & password, returns JWT | ❌ Public |

### Users (`/api/v1/users`)
| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `GET` | `/api/v1/users` | List all active agency employees | ✅ Bearer |
| `GET` | `/api/v1/users/{id}` | Get employee details by ID | ✅ Bearer |

### Leads (`/api/v1/leads`)
| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `POST` | `/api/v1/leads` | Create a new lead | ✅ Bearer |
| `GET` | `/api/v1/leads` | Retrieve all active leads | ✅ Bearer |
| `GET` | `/api/v1/leads/{id}` | Get lead details by ID | ✅ Bearer |
| `PUT` | `/api/v1/leads/{id}` | Update lead information | ✅ Bearer |
| `PATCH` | `/api/v1/leads/{id}/status` | Transition lead status (`NEW`, `CONTACTED`, `QUALIFIED`, `REJECTED`) | ✅ Bearer |
| `PATCH` | `/api/v1/leads/{id}/assign/{salesManagerId}` | Assign lead to a Sales Manager | ✅ Bearer |
| `POST` | `/api/v1/leads/{id}/convert` | **Atomically convert** qualified lead to Customer | ✅ Bearer |
| `DELETE` | `/api/v1/leads/{id}` | Soft-delete lead | ✅ Bearer |

### Customers (`/api/v1/customers`)
| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `GET` | `/api/v1/customers` | List all converted customers | ✅ Bearer |
| `GET` | `/api/v1/customers/{id}` | Get customer details by ID | ✅ Bearer |
| `PUT` | `/api/v1/customers/{id}` | Update customer contact & company details | ✅ Bearer |
| `DELETE` | `/api/v1/customers/{id}` | Soft-delete customer | ✅ Bearer |

> ℹ️ **Design Note:** There is intentionally **no** `POST /api/v1/customers` endpoint. Customers are exclusively created via Lead conversion (`POST /api/v1/leads/{id}/convert`).

### Deals (`/api/v1/deals`)
| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `POST` | `/api/v1/deals` | Create a commercial deal for an existing customer | ✅ Bearer |
| `GET` | `/api/v1/deals` | List deals with filtering by status/customer | ✅ Bearer |
| `GET` | `/api/v1/deals/{id}` | Get deal details by ID | ✅ Bearer |
| `PUT` | `/api/v1/deals/{id}` | Update deal title, description, valuation | ✅ Bearer |
| `PATCH` | `/api/v1/deals/{id}/status` | Transition deal status (`PROPOSAL`, `NEGOTIATION`, `WON`, `LOST`) | ✅ Bearer |
| `DELETE` | `/api/v1/deals/{id}` | Soft-delete deal | ✅ Bearer |

### Projects (`/api/v1/projects`)
| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `GET` | `/api/v1/projects` | List active projects | ✅ Bearer |
| `GET` | `/api/v1/projects/{id}` | Get project details (linked Deal, Sales Manager, Team Lead) | ✅ Bearer |
| `PUT` | `/api/v1/projects/{id}` | Update project metadata | ✅ Bearer |
| `PATCH` | `/api/v1/projects/{id}/status` | Transition project status (`PLANNING`, `IN_PROGRESS`, etc.) | ✅ Bearer |
| `DELETE` | `/api/v1/projects/{id}` | Soft-delete project | ✅ Bearer |

### Tasks (`/api/v1/tasks`)
| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `POST` | `/api/v1/tasks` | Create task under a project | ✅ Bearer |
| `GET` | `/api/v1/tasks` | List tasks (filterable by project / assignee / status) | ✅ Bearer |
| `GET` | `/api/v1/tasks/{id}` | Get task details | ✅ Bearer |
| `PUT` | `/api/v1/tasks/{id}` | Update task title, description, priority, due date | ✅ Bearer |
| `PATCH` | `/api/v1/tasks/{id}/status` | Update task status (`TODO`, `IN_PROGRESS`, `DONE`) | ✅ Bearer |
| `PATCH` | `/api/v1/tasks/{id}/assign/{userId}` | Reassign task to another agency employee | ✅ Bearer |
| `DELETE` | `/api/v1/tasks/{id}` | Soft-delete task | ✅ Bearer |

### Comments (`/api/v1/comments`)
| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `POST` | `/api/v1/comments` | Post comment on a Lead, Deal, Project, or Task | ✅ Bearer |
| `GET` | `/api/v1/comments` | Retrieve comments by target entity ID | ✅ Bearer |
| `DELETE` | `/api/v1/comments/{id}` | Hard-delete comment | ✅ Bearer |

### Standardized Error Format
All client and server errors return a consistent RFC-compliant payload:
```json
{
  "code": "ENTITY_NOT_FOUND",
  "message": "Lead with id 42 not found",
  "timestamp": "2026-09-08T22:30:00",
  "path": "/api/v1/leads/42"
}
```

---

## 🛠 Technology Stack

| Layer | Component | Version | Purpose |
|---|---|---|---|
| **Language** | Java | 21 (LTS) | Virtual threads, record patterns, modern syntax |
| **Framework** | Spring Boot | 4.0.6 | Core enterprise runtime and auto-configuration |
| **Architecture** | Spring Modulith | 1.3+ | Modular monolith boundary verification |
| **Persistence** | Spring Data JPA / Hibernate | 6.x | Object-relational mapping, repositories, auditing |
| **Database** | PostgreSQL | 17 | Relational persistence, JSONB, native ENUM types |
| **Migrations** | Flyway | 10.x | Schema-first versioned DDL migrations |
| **Security** | Spring Security & JJWT | 0.13.0 | Stateless JWT authorization & BCrypt hashing |
| **Mapping** | MapStruct | 1.6.3 | Compile-time entity ↔ DTO mapping |
| **Boilerplate** | Project Lombok | 1.18.38 | Automated getters, setters, builders |
| **Validation** | Jakarta Bean Validation | 3.x | Declarative request payload constraints |
| **Documentation** | Springdoc OpenAPI | 3.0.3 | Interactive Swagger UI API documentation |
| **Testing** | JUnit 5, Mockito, Testcontainers | 1.21.4 | Isolated unit and containerized integration tests |
| **Containers** | Docker & Docker Compose | Latest | Localized database orchestration |

---

## 🚀 Getting Started

### Prerequisites
- **Java Development Kit (JDK)**: Version 21 or higher installed (`java -version`).
- **Docker & Docker Compose**: Installed and running daemon (`docker --version`, `docker compose version`).
- **Git**: For source version control.

### Environment Setup
Clone the repository and copy the environment configuration template:

```bash
# Clone the repository
git clone https://github.com/OzodN/agencyflow-crm-backend.git
cd agencyflow-crm-backend

# Copy the environment file
cp .env.example .env
```

Review `.env` and configure your credentials:
```env
# Application Server
PORT=8080

# Database Connection
DATABASE_URL=jdbc:postgresql://localhost:5432/agencyflow_crm
PGUSER=agencyflow
PGPASSWORD=agencyflow_secret

# Docker Compose Database Settings
POSTGRES_DB=agencyflow_crm
POSTGRES_USER=agencyflow
POSTGRES_PASSWORD=agencyflow_secret

# JWT Authentication (HS256 Base64 Secret Key)
JWT_SECRET=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
JWT_EXPIRATION=86400000
```

### Docker Services
Start the PostgreSQL 17 database container using Docker Compose:

```bash
docker compose up -d
```

Check database container health:
```bash
docker compose ps
```

### Running the Application
Run the application using the Maven wrapper:

```bash
# Linux / macOS
./mvnw clean spring-boot:run

# Windows PowerShell
.\mvnw.cmd clean spring-boot:run
```

Once started, the backend will be available at `http://localhost:8080`.

Interactive Swagger UI documentation is accessible at:
👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

---

## 👤 Default Seed Accounts

The database migrations (`V11` and `V12`) automatically provision two initial system accounts for development and testing:

| Role | Email | Password | Intended Use |
|---|---|---|---|
| **ADMIN** | `admin@agencyflow.com` | `admin123` *(or pre-configured hash)* | System configuration, user provisioning, global oversight |
| **SALES_MANAGER** | `sales-manager@agencyflow.com` | `admin123` *(or pre-configured hash)* | Lead qualification, customer conversion, deal negotiation |

### Quick Authentication Example
Authenticate via `cURL` to receive your JWT bearer token:

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@agencyflow.com",
    "password": "password"
  }'
```

Response:
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBhZ2VuY3lmbG93LmNvbSIsImlhdCI6MTcyNTg0MDAwMCwiZXhwIjoxNzI1OTI2NDAwfQ..."
}
```

Use the received token in the `Authorization` header for subsequent requests:
```bash
curl -X GET http://localhost:8080/api/v1/leads \
  -H "Authorization: Bearer <YOUR_ACCESS_TOKEN>"
```

---

## 🧪 Testing & Quality Assurance

AgencyFlow CRM maintains rigorous automated test suites across all layers:

```bash
# Run all unit and architectural verification tests
./mvnw test

# Run integration tests using Testcontainers (requires Docker)
./mvnw verify
```

### Test Suite Architecture
- **Unit Tests**: Test business logic and state machine transitions in isolation with Mockito.
- **Integration Tests**: Leverage **Testcontainers** to launch ephemeral, pristine PostgreSQL 17 containers matching production specifications exactly.
- **Architectural Tests**: Use **Spring Modulith** verification rules to enforce package boundaries and detect unintended circular dependencies between domain modules.

---

## 📂 Project Directory Layout

```text
agencyflow-crm/
├── .env.example                     # Environment template
├── docker-compose.yaml              # Local PostgreSQL 17 service
├── pom.xml                          # Maven build configuration
├── LICENSE                          # MIT License
├── README.md                        # Documentation
├── src/
│   ├── main/
│   │   ├── java/com/agencyflow/crm/
│   │   │   ├── AgencyflowCrmApplication.java
│   │   │   ├── audit/               # Audit trail & Security auditor aware
│   │   │   ├── auth/                # Login orchestration & JWT generation
│   │   │   ├── common/              # Base entities, exception advice, DTOs
│   │   │   ├── config/              # OpenAPI, JPA Auditing configurations
│   │   │   ├── customer/            # Converted client records & service
│   │   │   ├── deal/                # Commercial opportunities & valuation
│   │   │   ├── lead/                # Prospect pipeline & atomic conversion
│   │   │   ├── project/             # Delivery project coordination
│   │   │   ├── task/                # Project delivery task assignment
│   │   │   ├── comment/             # Polymorphic discussion engine
│   │   │   ├── security/            # JWT filter, custom UserDetails, BCrypt
│   │   │   └── user/                # Employee identity & role models
│   │   └── resources/
│   │       ├── application.yaml     # Application configuration
│   │       └── db/migration/        # Flyway SQL versioned migrations
│   └── test/
│       └── java/com/agencyflow/crm/ # Unit, integration & Modulith tests
```

---

## 🤝 Contributing

Contributions to AgencyFlow CRM are welcome! Please adhere to our development workflow:

1. **Fork the Repository** & create a feature branch (`git checkout -b feature/amazing-feature`).
2. **Follow Coding Standards**: Ensure code adheres to Java 21 conventions, uses records for DTOs, and keeps controllers free of business logic.
3. **Write Tests**: Accompany every business change with corresponding unit/integration tests.
4. **Commit using Conventional Commits**:
   ```bash
   git commit -m "feat: implement deal valuation threshold validation"
   ```
5. **Push to Branch** (`git push origin feature/amazing-feature`).
6. **Open a Pull Request** against `dev`.

---

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Author & Acknowledgments

Developed and maintained by **[OzodN](https://github.com/OzodN)**.

Special thanks to the Spring and PostgreSQL open-source communities for providing world-class tools.
