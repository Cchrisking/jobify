package com.mcverse.jobify.job;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.mcverse.jobify.support.ApiTestSupport.bearer;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class JobEndpointsIntegrationTest {

    private static final String VALID_JOB = """
            {"jobTitle":"Platform Engineer","jobDescription":"<p>Build things</p>","jobRating":4.0,
             "hourlyRate":60,"location":"Remote","workMode":"REMOTE","employmentType":"FULL_TIME",
             "requiredSkills":["Java","Spring Boot"]}""";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    // ── B1 ────────────────────────────────────────────────────────────────────

    @Test
    void corsPreflightAllowsPatch() throws Exception {
        mvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options("/jobs/1/available")
                        .header("Origin", "http://localhost:3000")
                        .header("Access-Control-Request-Method", "PATCH")
                        .header("Access-Control-Request-Headers", "authorization,content-type"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.header()
                        .string("Access-Control-Allow-Methods", containsString("PATCH")));
    }

    // ── public routes ─────────────────────────────────────────────────────────

    @Test
    void anonymousCanListAndReadJobs() throws Exception {
        mvc.perform(get("/jobs")).andExpect(status().isOk());
        mvc.perform(get("/jobs?available=true")).andExpect(status().isOk())
                .andExpect(jsonPath("$[*].available", everyItem(equalTo(true))));
    }

    @Test
    void anonymousCannotWrite() throws Exception {
        mvc.perform(post("/jobs").contentType(APPLICATION_JSON).content(VALID_JOB))
                .andExpect(status().isForbidden());
    }

    // ── B2: /jobs/mine ────────────────────────────────────────────────────────

    @Test
    void employerSeesOnlyOwnJobsIncludingClosed() throws Exception {
        mvc.perform(get("/jobs/mine").header(AUTHORIZATION, bearer(mvc, "techcorp")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].employerUsername", everyItem(equalTo("techcorp"))))
                .andExpect(jsonPath("$.length()").isNotEmpty());
    }

    @Test
    void seekerCannotUseMine() throws Exception {
        mvc.perform(get("/jobs/mine").header(AUTHORIZATION, bearer(mvc, "alice_s")))
                .andExpect(status().isForbidden());
    }

    @Test
    void anonymousCannotUseMine() throws Exception {
        mvc.perform(get("/jobs/mine")).andExpect(status().isForbidden());
    }

    // ── B3 / B5: create ───────────────────────────────────────────────────────

    @Test
    void seekerCreatingJobGets403WithReadableMessage() throws Exception {
        mvc.perform(post("/jobs").header(AUTHORIZATION, bearer(mvc, "alice_s"))
                        .contentType(APPLICATION_JSON).content(VALID_JOB))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Only employers can post jobs."))
                .andExpect(jsonPath("$.status").value(403));
    }

    @Test
    void employerCreatesJobOpenAndOwned() throws Exception {
        mvc.perform(post("/jobs").header(AUTHORIZATION, bearer(mvc, "techcorp"))
                        .contentType(APPLICATION_JSON).content(VALID_JOB))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.employerUsername").value("techcorp"))
                .andExpect(jsonPath("$.available").value(true))
                .andExpect(jsonPath("$.requiredSkills[0]").value("Java"))
                .andExpect(jsonPath("$.workMode").value("REMOTE"));
    }

    @Test
    void clientCannotForceOwnershipOrAvailabilityOnCreate() throws Exception {
        String body = """
                {"jobTitle":"T","jobDescription":"<p>d</p>","available":false,"postId":9999,
                 "employer":{"username":"startupxyz"}}""";
        mvc.perform(post("/jobs").header(AUTHORIZATION, bearer(mvc, "techcorp"))
                        .contentType(APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.employerUsername").value("techcorp"))
                .andExpect(jsonPath("$.available").value(true))
                .andExpect(jsonPath("$.postId").value(not(9999)));
    }

    @Test
    void invalidCreateRequestGets400WithReadableMessage() throws Exception {
        String body = "{\"jobTitle\":\"\",\"jobDescription\":\"<p>d</p>\",\"jobRating\":9}";
        mvc.perform(post("/jobs").header(AUTHORIZATION, bearer(mvc, "techcorp"))
                        .contentType(APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message", containsString("jobTitle is required")))
                .andExpect(jsonPath("$.message", containsString("jobRating must be between 0 and 5")))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    void malformedJsonGets400() throws Exception {
        mvc.perform(post("/jobs").header(AUTHORIZATION, bearer(mvc, "techcorp"))
                        .contentType(APPLICATION_JSON).content("{nope"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("The request body is missing or malformed."));
    }

    @Test
    void oversizedDescriptionIsRejected() throws Exception {
        String big = "a".repeat(20_001);
        String body = "{\"jobTitle\":\"T\",\"jobDescription\":\"" + big + "\"}";
        mvc.perform(post("/jobs").header(AUTHORIZATION, bearer(mvc, "techcorp"))
                        .contentType(APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("jobDescription must be at most 20000")));
    }

    // ── B4: sanitizing on write ───────────────────────────────────────────────

    @Test
    void scriptsAndEventHandlersAreStrippedOnCreate() throws Exception {
        String body = "{\"jobTitle\":\"T\",\"jobDescription\":\"<p onclick=\\\"x()\\\">Hi</p>"
                + "<script>alert(1)</script><a href=\\\"javascript:alert(1)\\\">bad</a><strong>ok</strong>\"}";
        mvc.perform(post("/jobs").header(AUTHORIZATION, bearer(mvc, "techcorp"))
                        .contentType(APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.jobDescription", not(containsString("script"))))
                .andExpect(jsonPath("$.jobDescription", not(containsString("onclick"))))
                .andExpect(jsonPath("$.jobDescription", not(containsString("javascript:"))))
                .andExpect(jsonPath("$.jobDescription", containsString("<strong>ok</strong>")));
    }

    // ── B3 / B5: edit and availability ownership ──────────────────────────────

    @Test
    void ownerCanEditAndToggleButOthersCannot() throws Exception {
        String owner = bearer(mvc, "techcorp");
        String created = mvc.perform(post("/jobs").header(AUTHORIZATION, owner)
                        .contentType(APPLICATION_JSON).content(VALID_JOB))
                .andReturn().getResponse().getContentAsString();
        int id = JsonPath.read(created, "$.postId");

        String edited = VALID_JOB.replace("Platform Engineer", "Staff Engineer");
        mvc.perform(put("/jobs/" + id).header(AUTHORIZATION, owner)
                        .contentType(APPLICATION_JSON).content(edited))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jobTitle").value("Staff Engineer"))
                .andExpect(jsonPath("$.available").value(true));

        mvc.perform(patch("/jobs/" + id + "/available").header(AUTHORIZATION, owner)
                        .contentType(APPLICATION_JSON).content("{\"available\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available").value(false));

        String other = bearer(mvc, "startupxyz");
        mvc.perform(patch("/jobs/" + id + "/available").header(AUTHORIZATION, other)
                        .contentType(APPLICATION_JSON).content("{\"available\":true}"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("You can only change job postings that you created."));
        mvc.perform(put("/jobs/" + id).header(AUTHORIZATION, other)
                        .contentType(APPLICATION_JSON).content(edited))
                .andExpect(status().isForbidden());

        String seeker = bearer(mvc, "alice_s");
        mvc.perform(patch("/jobs/" + id + "/available").header(AUTHORIZATION, seeker)
                        .contentType(APPLICATION_JSON).content("{\"available\":true}"))
                .andExpect(status().isForbidden());

        mvc.perform(get("/jobs/" + id))
                .andExpect(jsonPath("$.jobTitle").value("Staff Engineer"))
                .andExpect(jsonPath("$.available").value(false));
    }

    @Test
    void editingMissingJobIs404() throws Exception {
        mvc.perform(put("/jobs/987654").header(AUTHORIZATION, bearer(mvc, "techcorp"))
                        .contentType(APPLICATION_JSON).content(VALID_JOB))
                .andExpect(status().isNotFound());
    }
}
