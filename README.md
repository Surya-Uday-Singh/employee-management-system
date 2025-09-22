# Employee Management System

This repository contains a modular Employee Management System composed of several cooperating microservices. The description below focuses solely on what the project is and what each component's responsibilities are operational and setup details are intentionally excluded.
<img width="1331" height="600" alt="Frontend (Client)-2" src="https://github.com/user-attachments/assets/5b08133c-7f99-4585-b17d-7e08b5a88550" />


## Summary

The system models a production-like service architecture for employee management, authentication, salary handling, and analytics. It demonstrates:

- Bounded contexts implemented as independent services
- Typed event-driven integration using Protocol Buffers
- A hybrid API surface (REST for external APIs, gRPC for internal RPCs)
- Centralized request routing and lightweight policy enforcement via an API Gateway

## Core Services & Responsibilities

api-gateway

  - Central routing and lightweight policy enforcement (authentication, request filtering). Does not own business data.

employee-service

  - Manages employee lifecycle and domain rules. Owns employee data and publishes domain events describing changes.

auth-service

  - Issues and validates tokens. Central authority for authentication-related concerns and validation used by other components.

salary-service

  - Manages salary accounts and related operations. Exposes gRPC interfaces for strongly-typed, low-latency internal calls.

analytics-service
  - Consumes domain events (e.g., employee events) to create analytics, projections, and audit logs. Functions as a read-model/analytics pipeline.

## API Surface & Integration Patterns

- Employee API (HTTP REST): CRUD and query operations for employee domain.
- Auth API (HTTP REST): Token issuance and validation endpoints.
- Salary API (gRPC): Internal RPCs for salary operations and queries.
- Domain Events (Protocol Buffers): Typed events (for example, `EmployeeEvent`) used for asynchronous, eventual-consistency integrations.

## Data Ownership & Consistency

Each service owns its data persistence and model. Services communicate changes via events, favoring eventual consistency and clear contracts through protobuf schemas.

## Technology Highlights

- Java / Spring Boot
- gRPC / Protocol Buffers
- Kafka (event streaming integration)
- Spring Cloud Gateway

## Repository Layout (overview)

- `api-gateway/`
- `employee-service/`
- `auth-service/`
- `salary-service/`
- `analytics-service/`

Protobuf service contracts and event schemas live in each module's `src/main/proto/` directory.

## Design Notes

- Token validation is centralized in `auth-service` to avoid duplication of auth logic.
- Employee changes are emitted as protobuf-typed events to provide a stable contract for consumers.
- Salary operations use gRPC where performance and strong typing are primary concerns.
