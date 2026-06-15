# Jobify Back-End — Code Review

**Reviewed:** 2026-06-15  
**Spring Boot:** 4.0.0-M3 · **Java:** 21  
**Root:** `jobify/src/main/java/com/mcverse/jobify/`

---

## Summary

The scaffold is well-structured (feature-sliced: `auth/`, `job/`, `user/`, `common/`, `config/`) but the implemented files had several Java convention violations, type issues, dead code, and API design problems. All issues below have been fixed in the refactor unless marked **[TODO]**.

---

## Issues Found & Fixed

### 1. `model/Skill.java` — PascalCase field names
**Severity:** Medium

Fields `SkillID`, `SkillName`, `SkillDescription` used PascalCase, which violates the Java naming convention (fields must be camelCase). This also caused the generated JSON keys to be non-standard.

**Fix:** Renamed to `skillId`, `skillName`, `skillDescription` throughout (fields, constructor params, getters, setters).

---

### 2. `model/JobPost.java` — Multiple issues

**2a. Package-private field**  
`int postId` had no access modifier, making it package-private by accident.  
**Fix:** Added `private`.

**2b. Constructor parameter type mismatch**  
Constructor accepted `float jobRating` and `float hourlyRate` while the fields were `double`. The float literals in `JobRepo` (e.g. `4.5f`) widened silently, which is confusing and imprecise.  
**Fix:** Changed constructor and setter parameters to `double`.

**2c. Duplicate `setJobRating` overloads**  
Both `setJobRating(double)` and `setJobRating(float)` were defined on the same class. The `float` overload was redundant and inconsistent with the field type.  
**Fix:** Removed the `float` overload.

**2d. `setHourlyRate` accepted `float` instead of `double`**  
**Fix:** Changed parameter to `double`.

**2e. Trailing semicolon after closing brace**  
`};` at end of class — invalid Java style (compiles but is wrong).  
**Fix:** Removed.

**2f. Formatting**  
Inconsistent blank lines inside setters and multi-line method signatures split mid-parameter.  
**Fix:** Normalised to single-line accessors with consistent spacing.

---

### 3. `job/model/Job.java` — Raw `Hashtable` used as ID

**Severity:** High

`private Hashtable id` is a raw (un-genericised) use of the legacy `java.util.Hashtable` class as an entity ID. An ID should be a scalar (`String` or `UUID`). The `Hashtable` type was not parameterised, causing an unchecked-type warning, and its purpose was completely unclear.

**Fix:** Changed to `private String id` and added it as the first constructor parameter.

Additionally, `jobRating` and `hourlyRate` had no getters despite being declared, and `lastUpdate` had no setter (preventing any mutations). These were also absent from the constructor, leaving them silently at `0.0`.

**Fix:** Added getters for `jobRating` and `hourlyRate`; added setters for `jobRating`, `hourlyRate`, and `lastUpdate`.

---

### 4. `job/controller/JobController.java` — Multiple issues

**4a. Bypassing the service layer**  
`allJobs()` called `jobRepo.getJobs()` directly, bypassing `JobService`. Meanwhile `@Autowired JobService` was injected but completely unused. This defeats the purpose of the service layer.  
**Fix:** Removed the direct `JobRepo` injection; all data access now goes through `JobService`.

**4b. Non-RESTful route names**  
`GET /alljobs` and `POST /addjob` use verbs in URLs, which violates REST conventions. HTTP methods already provide the verb.  
**Fix:** Moved to `@RequestMapping("/jobs")`, with `GET /jobs` and `POST /jobs`.

**4c. `POST /addjob` was a stub**  
The endpoint returned a hardcoded `"adding job"` string and never persisted anything.  
**Fix:** Now accepts `@RequestBody JobPost` and delegates to `jobService.addJob()`. Returns `201 Created`.

**4d. `GET /` and `GET /home` noise endpoints**  
These were not meaningful REST resources.  
**Fix:** Removed.

**4e. Return type `ArrayList` instead of `List`**  
Exposing a concrete collection type leaks implementation detail.  
**Fix:** Changed return type to `List<JobPost>`.

**4f. CORS hardcoded** (**[TODO]**)  
`@CrossOrigin(origins = "http://localhost:3000")` is hardcoded. It should be externalised to `application.properties` and wired through `SecurityConfig` or a `WebMvcConfigurer`.  
**No fix yet** — requires `SecurityConfig` to be implemented first.

---

### 5. `job/repository/JobRepo.java` — Package-private field

`ArrayList<JobPost> jobs` had no access modifier.  
**Fix:** Added `private final`. Changed return type of `getJobs()` from `ArrayList<JobPost>` to `List<JobPost>`.  
Also aligned seed-data literal types from `float` literals (`4.5f`) to `double` literals (`4.5`) to match the `JobPost` constructor.

---

### 6. `job/service/JobService.java` — Misleading comment

`//DTO` comment above `@Service` was incorrect and confusing (a service is not a DTO).  
**Fix:** Removed. Changed return type from `ArrayList<JobPost>` to `List<JobPost>`.

---

### 7. `user/model/User.java` — Legacy `java.util.Date`

**Severity:** Medium

`creationDate` and `lastUpdate` used `java.util.Date`, which has been effectively deprecated since Java 8 in favour of `java.time`. `Date` is mutable, not thread-safe, and has a confusing API.

**Fix:** Replaced with `LocalDateTime`. Renamed `lastUpdate` (singular) to `lastUpdates` (plural list) for clarity. Removed the two dead private setters `setCreationDate` / `setLastUpdate` that were unreachable from outside and served no purpose.

---

### 8. `user/model/File.java` — Legacy `Date` + misleading getter

Same `java.util.Date` issue as `User.java`.  
`getFile()` returned `fileUrl` — the name was ambiguous and hid what was actually being returned.  
`getFileType()` getter was missing entirely.

**Fix:**
- `Date` → `LocalDateTime`
- `getFile()` → `getFileUrl()`
- Added `getFileType()`
- Setter names aligned: `setUrl()` → `setFileUrl()`, `setType()` → `setFileType()`, `setDate()` → `setFileDate()`

---

### 9. `user/model/Cv.java` — Cascading `Date` fix

Updated constructor signature from `Date` to `LocalDateTime` to match the parent `File` class after fix #8.

---

### 10. `JobifyApplication.java` — Debug `System.out.println`

`System.out.println("Spring version: " + SpringVersion.getVersion())` in `main()` is debug output with inconsistent indentation.  
**Fix:** Removed. Spring Boot's own banner already logs the version on startup. Removed the now-unused `SpringVersion` import.

---

## Remaining Issues (Not Fixed — Require Broader Work)

| # | Location | Issue | Reason deferred |
|---|---|---|---|
| 1 | `common/exception/`, `common/response/`, `common/validation/`, `config/` | ~~18 stub files~~ | **Fixed** — exceptions (ResourceNotFoundException 404, BusinessRuleException 422, LicenseValidationException 403), GlobalExceptionHandler (`@RestControllerAdvice`), ApiResponse\<T\> + PagedResponse\<T\> records, ValidSalaryRange constraint + SalaryRangeValidator, OpenApiConfig (springdoc + JWT Bearer scheme). ValidGpuRamCombo deleted (wrong domain). |
| 2 | `auth/controller/AuthController.java` | ~~Empty class body~~ | **Fixed** — implemented in security feature commit |
| 3 | `JobController` | ~~CORS hardcoded~~ | **Fixed** — CORS moved to SecurityConfig, driven by `cors.allowed-origins` property |
| 4 | Whole project | ~~No persistence — in-memory ArrayList~~ | **Fixed** — Spring Data JPA + H2 (file-based dev, in-memory for tests). `JobPost` → `@Entity`. `JobRepo` → `JpaRepository<JobPost, Integer>`. `AppUser` JPA entity + `AuthUserRepository` for auth (separated from domain User). `DataInitializer` seeds on first start. `JobService` uses `findAll()` / `save()`. H2 console at `/h2-console`. Swagger UI at `/swagger-ui/index.html`. |
| 5 | `user/model/JobPreferences.java` | ~~All fields `String`~~ | **Fixed** — `salaryExpectations: String` → `minSalaryExpectation + maxSalaryExpectation: double`; `desiredJobType: String` → `employmentType: EmploymentType`; `skills: String` → `List<String>`; `jobPreferences: String` → `preferredJobTitle: String`; annotated `@ValidSalaryRange`. |
| 6 | `user/model/Seeker.java` | ~~`getStatus()` returns `isIndependent` — name does not describe what it returns~~ | **Fixed** — renamed to `isIndependent()` |
| 7 | `job/model/Job.java` | ~~No `EmploymentType` field despite the enum existing~~ | **Fixed** — added `employmentType` field, getter, setter, and constructor param |
| 8 | Root `pom.xml` vs `jobify/pom.xml` | ~~Nested stale `pom.xml` (Spring Boot 3.4.5, Java 24) should be deleted~~ | **Fixed** — `jobify/` directory removed from git |
| 9 | `jobify/jobify/` nested directory | ~~Contains a second copy of `JobifyApplication.java`~~ | **Fixed** — deleted with `git rm -r jobify/` |

---

## Files Changed

| File | Changes |
|---|---|
| `model/Skill.java` | PascalCase fields → camelCase |
| `model/JobPost.java` | Private field, fix parameter types, remove duplicate setter, remove trailing `;`, normalise formatting |
| `job/model/Job.java` | Replace `Hashtable` id with `String`, add `id` to constructor, add missing getters and setters |
| `job/repository/JobRepo.java` | Private field, `List` return type, `double` literals |
| `job/service/JobService.java` | Remove `//DTO` comment, `List` return type |
| `job/controller/JobController.java` | REST routes, wire service, remove direct repo use, `@RequestBody`, `201 Created`, remove stub endpoints |
| `user/model/User.java` | `LocalDateTime`, rename `lastUpdates`, remove dead private setters |
| `user/model/File.java` | `LocalDateTime`, fix getter names, add `getFileType()` |
| `user/model/Cv.java` | Updated `LocalDateTime` constructor |
| `JobifyApplication.java` | Remove debug `println`, fix indentation |
