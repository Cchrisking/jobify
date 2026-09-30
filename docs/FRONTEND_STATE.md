# Front-end state, as seen by the back end

**Snapshot:** 2026-09-30. Front end: `../frontjobfy/` (Next.js 15, React 19, TypeScript, Tailwind 4, axios). Much of it is **uncommitted** in that repo (about 26 modified and 20 new files), so treat the working tree as the source of truth, not git history.
Base URL is hardcoded to `http://localhost:9080` in `app/lib/api.ts`. Token, username, role and expiry are kept in `localStorage`, and a 401 clears them and logs the user out.

## 1. Endpoints the front end really calls (keep these stable)

| Call | Where used | Notes |
|---|---|---|
| `POST /auth/login`, `POST /auth/register` | `AuthContext` | Register sends `role` SEEKER or EMPLOYER plus first and last name. |
| `GET /jobs?available=true` | `useJobs`, `app/sitemap.ts` | Public. The whole list is loaded, and **search and filters run client-side**. |
| `GET /jobs` (no filter) | `useEmployerJobs` | Employer panel. Returns every employer's jobs. **Switch to `GET /jobs/mine`** (B2, done on the back end). |
| `GET /jobs/{id}` | `app/jobs/[id]/page.tsx` (SSR, SEO) | Public. Also feeds the JobPosting JSON-LD. |
| `POST /jobs` | `PostJobModal` | Sends the 7 fields listed below. |
| `PATCH /jobs/{id}/available` | `useEmployerJobs` | Fixed in sprint 1 (CORS now allows PATCH). Owner only. |
| `GET/PUT /users/seekers/me`, `GET/PUT /users/employers/me` | `UserProfile` | PUT body is `{name, lastName}`. |
| `POST /users/companies`, `PUT /users/companies/{id}` | `UserProfile` | `{name}`. |
| `POST/GET/DELETE /users/seekers/me/resume` | `useResume` | Multipart upload, blob download. Reads `SeekerResponse.cv`. |
| `PUT /account/password` | `useAccount` | `{currentPassword, newPassword}`. |
| `GET/POST/DELETE /account/deletion-request` | `useAccount` | The GET must return `null` (200) when there is no request. |
| `GET /admin/deletion-requests`, `POST …/{id}/approve`, `POST …/{id}/reject {note}` | `useAdminDeletionRequests` | |
| `GET /content` | `ContentContext`, `app/lib/seo.ts` | Public. Returns a flat `{key: value}` map. |
| `GET/PUT /admin/content` | `useAdminContent` | GET returns `ContentEntry[]`. PUT takes `{values: {key: value}}` and returns `ContentEntry[]`. |

`POST /jobs` payload today: `{jobTitle, jobDescription (sanitized HTML from a TipTap editor), jobRating, hourlyRate, location, workMode, employmentType}`. It does **not** send `requiredSkills`.
The front end's `Job` type (`app/types/job.ts`) has no `requiredSkills` field, although the API returns one. `SeekerResponse` in the front end types only `id/username/name/lastName/creationDate/isIndependent/cv`. The educations, certifications, experiences and skills lists the API now embeds are ignored.

## 2. Built on the back end but not used by the front end yet
- `/users/seekers/me/{educations,certifications,experiences,skills}` full CRUD. There is **no UI** for it, and the profile page's completeness widgets are cosmetic.
- `requiredSkills` on job posts (never sent, never displayed).
- `GET /admin/users` (`AdminUserController`). The admin dashboard shows `MOCK_USERS`.
- `JobPreferences` entity. It is not reachable from any endpoint.

## 3. Faked on the front end because the API has nothing (the real gaps)
| Feature | Front-end reality today | Back end needs |
|---|---|---|
| **Applications** | `applyForJob` only appends to in-memory React state. It is lost on refresh and the employer never sees it. | `Application` entity plus `POST /jobs/{id}/apply`, `GET /applications/me`, `GET /jobs/{id}/applications` (employer), a withdraw call, and a status pipeline. |
| **Application stage** (Recruiter Review / Interview / Offer / Rejected) | `app/lib/applicationStage.ts` derives a fake stage from `postId`. | A real stage field and an employer-side transition endpoint. |
| **Saved jobs** | In-memory React state. | `PUT/DELETE /jobs/{id}/save` plus `GET /jobs/saved`. |
| **Match %** | `jobEnrichment.ts` computes a fake 70–99 number from `postId` and `jobRating`. Shown on cards, details, hero, carousel and both dashboards. | The matching engine (see `../job-matching-engine.md`): `GET /jobs/recommended`, a per-job score with a breakdown (matched and missing skills), and employer-side `GET /jobs/{id}/candidates`. |
| **Job skills, benefits, gallery, salary band, posted-ago, company name and logo** | Seeded from `postId`. Skills come from a hardcoded pool. | Real `requiredSkills` on the client type (front-end work), `createdAt` (already returned), and a company name in the job response (today only `employerUsername`, which the UI capitalizes). Benefits and gallery are optional. |
| **Notifications** | `MOCK_NOTIFICATIONS`. The bell badge and the panel are fake. | A `Notification` entity, `GET /notifications`, mark-read, and events on application status change and new match. |
| **Notification preferences and accent colour** | `localStorage` only. | Optional `GET/PUT /users/me/preferences`. Low priority. |
| **Company dashboard** | `MOCK_PIPELINE_CANDIDATES`, `HIRING_CHART_DATA` and activity are fake. The job list and counts are real. | The applications and pipeline endpoints above, plus employer stats (views, applications per job, funnel counts). |
| **Admin dashboard metrics and user table** | Mock. Only the deletion-requests card and the CMS panel are real. | Use `GET /admin/users` (add paging and search), plus `GET /admin/stats` (users by role, jobs, applications). |
| **Employee role dashboard** | Pure mock ("Employee Preview"). The role does not exist in the API. | Product decision needed. See backlog P3. |
| **Seeker dashboard charts and tips** | Static (`EMPLOYEE_CHART_DATA`, interview tips). | Application-activity time series once applications exist. |
| **Job search** | Client-side over the full list. | Server-side `GET /jobs` with `q`, `location`, `workMode`, `employmentType`, `minRate`, `skills`, `page`, `size`, `sort`. The response shape must change for paging, so **coordinate** (see below). |
| **Location facet** | Filter over the free-text `location` string. | Optional normalization. |

## 4. Back-end behaviours the front end assumes
- `available=true` filters server-side. Newly created jobs are `available=true`.
- Description is rich text: the front end sanitizes with DOMPurify, but **the server must too** (B4). The docs claim 1000 chars, but the field is `@Lob`.
- Auth errors: login failure must be 401 with a readable `message`.
- The employer panel assumes it only sees its own jobs. It currently filters nothing.
- Deletion-request GET returning `null` is intentional.
- Sitemap and SEO fetch server-side with `cache: 'no-store'`. Both survive the API being down, so keep `GET /jobs` cheap.

## 5. Contract-change protocol
The two sessions do not share memory. When you change something the front end touches:
1. Keep the old shape working or coordinate the break.
2. Update `api-documentation.md`.
3. Append an entry below. The user relays it to the front-end session.

### Requests for the front-end session (append here)
- [ ] Switch `useEmployerJobs` to `GET /jobs/mine` and handle 403 on job writes.
- [ ] Add `requiredSkills: string[]` to `Job` and `CreateJobRequest`, and add a skills picker to `PostJobModal`.
- [ ] Extend `SeekerResponse` with `educations/certifications/experiences/skills`, then build the profile-editing UI.
- [ ] Replace `applyForJob`/`saveJob` in-memory state with API calls once the P1 and P2 endpoints exist.
- [ ] Replace the fake `matchScore` in `jobEnrichment.ts` once the matching endpoint exists.
- [ ] Replace `MOCK_USERS` with `GET /admin/users`.

### Change log (back end → front end)
**Sprint 1 (2026-09-30)**
- `PATCH` now passes CORS, so the employer open/close toggle works from the browser (B1).
- New `GET /jobs/mine` (EMPLOYER only, includes closed jobs). **Please switch `useEmployerJobs` to it**; `GET /jobs` still returns every employer's jobs (B2).
- Job writes are checked on the server. A non-employer `POST /jobs` is now **403** (was 404 "Employer not found"), and editing or toggling someone else's job is **403** with a readable `message` (B3).
- `jobDescription` is sanitized on write to `p, br, ul, ol, li, strong, em, h2, h3, a[href]`; the max is 20,000 characters of submitted HTML. What you get back may differ slightly from what you sent (for example emoji as `&#x1f680;`, `rel="nofollow"` on links) (B4).
- `POST /jobs` now binds `CreateJobRequest`. The 7 fields you send today are unchanged and still work. `requiredSkills` is now an array of **names** (`["Java"]`), not objects. Validation errors are **400** with a `message` such as `jobTitle is required; jobRating must be between 0 and 5` (B5).
- New `PUT /jobs/{id}` to edit a post (same body as create, owner only) (B5).
- Malformed JSON bodies now return 400 instead of 500.
