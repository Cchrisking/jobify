# Jobify — Demo Users & Security Reference

> All demo accounts are seeded on first boot when the database is empty.  
> **Never use these credentials in production.**

---

## Demo Accounts

### Admin

| Username | Password | Role  |
|----------|----------|-------|
| `admin`  | `password` | `ADMIN` |

- Cannot be created via `POST /auth/register` (server rejects `role: ADMIN`).
- Seeded only through `DataInitializer` on first boot.
- Can access all `/admin/**` endpoints.

---

### Seekers

| Username   | Password   | First Name | Last Name |
|------------|------------|------------|-----------|
| `alice_s`  | `password` | Alice      | Johnson   |
| `bob_s`    | `password` | Bob        | Williams  |
| `carol_s`  | `password` | Carol      | Martinez  |

---

### Employers

| Username       | Password   | First Name | Last Name | Company            |
|----------------|------------|------------|-----------|-------------------|
| `techcorp`     | `password` | David      | Chen      | TechCorp Ltd       |
| `startupxyz`   | `password` | Emma       | Davis     | StartupXYZ Inc     |
| `financegroup` | `password` | Frank      | Wilson    | Finance Group SA   |

---

## Seeded Job Posts

| ID | Title                    | Employer       | Rate ($/hr) | Rating | Available |
|----|--------------------------|----------------|-------------|--------|-----------|
| 1  | Senior Frontend Developer | techcorp      | 75.00       | 4.5    | true      |
| 2  | Backend Engineer          | techcorp      | 72.50       | 4.7    | true      |
| 3  | Junior QA Engineer        | techcorp      | 35.00       | 4.0    | **false** |
| 4  | Product Designer          | startupxyz    | 55.00       | 4.2    | true      |
| 5  | DevOps Engineer           | startupxyz    | 85.00       | 4.8    | true      |
| 6  | Full-Stack Developer      | startupxyz    | 60.00       | 4.3    | true      |
| 7  | Senior Data Scientist     | financegroup  | 80.00       | 4.6    | true      |
| 8  | Junior Business Analyst   | financegroup  | 40.00       | 3.9    | **false** |
| 9  | ML Engineer               | financegroup  | 90.00       | 4.9    | true      |

Posts 3 and 8 are seeded as `available: false` (closed/filled) for testing the availability filter.

---

## JWT Security

### Algorithm & Configuration

| Property            | Value                                      |
|---------------------|--------------------------------------------|
| Algorithm           | HS256 (HMAC-SHA256)                        |
| Token type          | Bearer                                     |
| Expiry              | 86 400 000 ms (24 hours)                   |
| Secret key property | `jwt.secret` in `application.properties`   |
| Secret encoding     | Base64-encoded 256-bit key                 |

> **Warning:** The default secret in `application.properties` is a placeholder. Generate a real key before deploying:
> ```bash
> openssl rand -base64 32
> ```

### Token Structure

```
Header:  { "alg": "HS256" }
Payload: { "sub": "<username>", "iat": <issued-at>, "exp": <expires-at> }
```

### TokenResponse shape

```json
{
  "token":     "eyJhbGciOiJIUzI1NiJ9...",
  "type":      "Bearer",
  "expiresIn": 86400000,
  "username":  "alice_s",
  "role":      "SEEKER"
}
```

`role` is one of: `SEEKER` | `EMPLOYER` | `ADMIN`

### Usage

Include the token on every protected request:

```
Authorization: Bearer <token>
```

### Role permissions

| Role       | Public `GET /jobs` | Protected endpoints | `/admin/**` |
|------------|--------------------|---------------------|-------------|
| *(none)*   | ✅                 | ❌                  | ❌          |
| `SEEKER`   | ✅                 | ✅                  | ❌          |
| `EMPLOYER` | ✅                 | ✅                  | ❌          |
| `ADMIN`    | ✅                 | ✅                  | ✅          |

---

## Quick Login (curl)

```bash
# Login as admin
curl -s -X POST http://localhost:9080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"password"}'

# Login as seeker
curl -s -X POST http://localhost:9080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"alice_s","password":"password"}'

# Login as employer
curl -s -X POST http://localhost:9080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"techcorp","password":"password"}'

# List all users (admin only)
TOKEN=<paste token here>
curl -s http://localhost:9080/admin/users \
  -H "Authorization: Bearer $TOKEN"
```
