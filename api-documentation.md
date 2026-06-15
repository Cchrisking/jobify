# Jobify API Documentation

**Base URL:** `http://localhost:9080`  
**Swagger UI:** `http://localhost:9080/swagger-ui/index.html`  
**OpenAPI spec (JSON):** `http://localhost:9080/v3/api-docs`

---

## Authentication

Jobify uses **JWT Bearer tokens**.

1. Call `POST /auth/register` or `POST /auth/login` — both return a `TokenResponse`.
2. Store `token` (e.g. in `localStorage`).
3. Include the token on every protected request:

```
Authorization: Bearer <token>
```

Tokens expire after `expiresIn` milliseconds (default 24 h = `86400000`).

---

## Error format

All error responses share the same envelope:

```json
{
  "success": false,
  "message": "Human-readable error description",
  "data": null,
  "status": 404
}
```

| HTTP status | Trigger |
|---|---|
| `400` | Validation failure or illegal argument |
| `401` | Missing / expired / invalid JWT |
| `403` | Forbidden (license / access violation) |
| `404` | Resource not found |
| `422` | Business rule violation (e.g. duplicate company) |
| `500` | Unexpected server error |

---

## Auth endpoints `/auth/**`

> **No Authorization header required** for any endpoint in this group.

---

### `POST /auth/register`

Creates an account and immediately returns a token. A domain profile (Seeker **or** Employer) is created in the same transaction.

**Request body**

```json
{
  "username": "jane_doe",
  "password": "s3cur3P@ss",
  "role": "SEEKER",
  "firstName": "Jane",
  "lastName": "Doe"
}
```

| Field | Type | Required | Notes |
|---|---|---|---|
| `username` | string | yes | Must be unique across the platform |
| `password` | string | yes | Plain text — hashed server-side (BCrypt) |
| `role` | `"SEEKER"` \| `"EMPLOYER"` | yes | Determines which profile type is created |
| `firstName` | string | yes | |
| `lastName` | string | yes | |

**Response `201 Created`**

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "expiresIn": 86400000,
  "username": "jane_doe",
  "role": "SEEKER"
}
```

**Error responses**

| Status | When |
|---|---|
| `400` | Any field blank, `role` null, or username already taken |

---

### `POST /auth/login`

Authenticates an existing account.

**Request body**

```json
{
  "username": "jane_doe",
  "password": "s3cur3P@ss"
}
```

**Response `200 OK`** — same `TokenResponse` shape as register.

**Error responses**

| Status | When |
|---|---|
| `401` | Username not found or wrong password |

---

## Jobs endpoints `/jobs/**`

> **All endpoints require** `Authorization: Bearer <token>`.

---

### `GET /jobs`

Returns the full list of job posts.

**Response `200 OK`**

```json
[
  {
    "postId": 1,
    "jobTitle": "Senior Java Developer",
    "jobDescription": "We are looking for a Senior Java Developer with 5+ years of experience...",
    "jobRating": 4.2,
    "hourlyRate": 75.00,
    "employerUsername": "acme_corp"
  },
  {
    "postId": 2,
    "jobTitle": "Junior React Developer",
    "jobDescription": "Entry-level position for a React developer...",
    "jobRating": 3.8,
    "hourlyRate": 35.00,
    "employerUsername": null
  }
]
```

| Field | Type | Notes |
|---|---|---|
| `postId` | integer | Auto-generated primary key |
| `jobTitle` | string | |
| `jobDescription` | string | Up to 1000 characters |
| `jobRating` | double | 0.0–5.0 |
| `hourlyRate` | double | USD per hour |
| `employerUsername` | string \| null | Username of the posting employer; `null` for seed data with no employer assigned |

---

### `POST /jobs`

Creates a new job post. Currently accepts a raw `JobPost` entity body.

**Request body**

```json
{
  "jobTitle": "Full-Stack Engineer",
  "jobDescription": "Looking for a full-stack engineer with React and Spring Boot experience...",
  "jobRating": 4.5,
  "hourlyRate": 65.00
}
```

**Response `201 Created`** — returns the saved `JobPostResponse` with the generated `postId`.

---

## Users endpoints `/users/**`

> **All endpoints require** `Authorization: Bearer <token>`.  
> `/me` endpoints use the **username embedded in the JWT** — no ID needed in the path.

---

### Seeker endpoints

#### `GET /users/seekers/me`

Returns the seeker profile of the authenticated user.

**Response `200 OK`**

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "username": "jane_doe",
  "name": "Jane",
  "lastName": "Doe",
  "creationDate": "2024-01-15T10:30:00",
  "isIndependent": false
}
```

| Field | Type | Notes |
|---|---|---|
| `id` | UUID string | Profile UUID (different from `AppUser` row ID) |
| `username` | string | Matches the login username |
| `name` | string | First name |
| `lastName` | string | Last name |
| `creationDate` | ISO-8601 datetime | When the profile was created |
| `isIndependent` | boolean | Whether the seeker is an independent contractor |

**Error responses**

| Status | When |
|---|---|
| `401` | Invalid or missing token |
| `404` | Seeker profile not found (token belongs to an EMPLOYER) |

---

#### `GET /users/seekers/{id}`

Returns any seeker profile by UUID. Accessible to all authenticated users.

**Path parameter:** `id` — seeker UUID.

**Response `200 OK`** — same shape as `/seekers/me`.

---

#### `PUT /users/seekers/me`

Updates the authenticated seeker's first and last name.

**Request body**

```json
{
  "name": "Jane",
  "lastName": "Smith"
}
```

**Response `200 OK`** — updated `SeekerResponse`.

---

### Employer endpoints

#### `GET /users/employers/me`

Returns the employer profile of the authenticated user, including the linked company if one exists.

**Response `200 OK`**

```json
{
  "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "username": "acme_corp",
  "name": "Alice",
  "lastName": "Smith",
  "creationDate": "2024-01-15T10:30:00",
  "company": {
    "id": "7f3b2c4a-1234-5678-abcd-ef0123456789",
    "name": "Acme Corporation"
  }
}
```

`company` is `null` until `POST /users/companies` is called.

---

#### `GET /users/employers/{id}`

Returns any employer profile by UUID.

**Path parameter:** `id` — employer UUID.

**Response `200 OK`** — same shape as `/employers/me`.

---

#### `PUT /users/employers/me`

Updates the authenticated employer's first and last name.

**Request body**

```json
{
  "name": "Alice",
  "lastName": "Johnson"
}
```

**Response `200 OK`** — updated `EmployerResponse`.

---

### Company endpoints

An employer can only have **one** company. The company is a child of the employer profile.

#### `POST /users/companies`

Creates a company and links it to the authenticated employer. Returns `422` if a company already exists — use the update endpoint instead.

**Request body**

```json
{
  "name": "Acme Corporation"
}
```

**Response `201 Created`**

```json
{
  "id": "7f3b2c4a-1234-5678-abcd-ef0123456789",
  "name": "Acme Corporation"
}
```

**Error responses**

| Status | When |
|---|---|
| `400` | `name` is blank |
| `401` | Invalid token |
| `404` | Employer profile not found |
| `422` | Employer already has a company |

---

#### `PUT /users/companies/{id}`

Updates the name of the company identified by `{id}`. The caller must own the company.

**Path parameter:** `id` — company UUID (from `CompanyResponse.id`).

**Request body**

```json
{
  "name": "Acme Corp (rebranded)"
}
```

**Response `200 OK`** — updated `CompanyResponse`.

**Error responses**

| Status | When |
|---|---|
| `401` | Invalid token |
| `404` | Employer not found |
| `422` | The `{id}` does not belong to the authenticated employer |

---

## Data models reference

### `TokenResponse`

```ts
interface TokenResponse {
  token: string;        // JWT — pass as Bearer header
  type: string;         // always "Bearer"
  expiresIn: number;    // milliseconds (default 86400000 = 24 h)
  username: string;
  role: "SEEKER" | "EMPLOYER";
}
```

### `JobPostResponse`

```ts
interface JobPostResponse {
  postId: number;
  jobTitle: string;
  jobDescription: string;
  jobRating: number;        // 0.0–5.0
  hourlyRate: number;       // USD
  employerUsername: string | null;
}
```

### `SeekerResponse`

```ts
interface SeekerResponse {
  id: string;               // UUID
  username: string;
  name: string;
  lastName: string;
  creationDate: string;     // ISO-8601
  isIndependent: boolean;
}
```

### `EmployerResponse`

```ts
interface EmployerResponse {
  id: string;               // UUID
  username: string;
  name: string;
  lastName: string;
  creationDate: string;     // ISO-8601
  company: CompanyResponse | null;
}
```

### `CompanyResponse`

```ts
interface CompanyResponse {
  id: string;               // UUID
  name: string;
}
```

---

## Typical client flows

### Flow 1 — Seeker signs up and browses jobs

```
POST /auth/register   { username, password, role: "SEEKER", firstName, lastName }
  → 201 { token, username, role: "SEEKER" }

Store token in localStorage.

GET /jobs             Authorization: Bearer <token>
  → 200 [ { postId, jobTitle, ... }, ... ]

GET /users/seekers/me Authorization: Bearer <token>
  → 200 { id, username, name, lastName, ... }
```

### Flow 2 — Employer signs up and creates a company + job post

```
POST /auth/register   { username, password, role: "EMPLOYER", firstName, lastName }
  → 201 { token, username, role: "EMPLOYER" }

POST /users/companies Authorization: Bearer <token>
  { "name": "Acme Corporation" }
  → 201 { id, name }

POST /jobs            Authorization: Bearer <token>
  { jobTitle, jobDescription, jobRating, hourlyRate }
  → 201 { postId, jobTitle, ... }
```

### Flow 3 — Returning user logs in

```
POST /auth/login      { username, password }
  → 200 { token, username, role }

// Use token from here on for all other requests.
```

---

## Notes for frontend developers

- **Token storage:** `localStorage` is the simplest option; consider `httpOnly` cookies for production.
- **Token expiry:** `expiresIn` is in **milliseconds**. Compute expiry as `Date.now() + expiresIn`.
- **Role-based rendering:** Use the `role` field from `TokenResponse` to decide which profile endpoint to call (`/seekers/me` vs `/employers/me`) and which UI to display.
- **`/me` endpoints vs `/{id}` endpoints:** Use `/me` for the authenticated user's own data (token carries the identity). Use `/{id}` to look up other users' public profiles.
- **CORS:** The backend allows requests from `http://localhost:3000` by default. Change `cors.allowed-origins` in `application.properties` for other origins.
- **Timestamps:** All `LocalDateTime` fields are serialized as ISO-8601 strings in UTC (e.g. `"2024-01-15T10:30:00"`).
- **Interactive testing:** Open `http://localhost:9080/swagger-ui/index.html`, click **Authorize**, paste your JWT, and try every endpoint from the browser.
