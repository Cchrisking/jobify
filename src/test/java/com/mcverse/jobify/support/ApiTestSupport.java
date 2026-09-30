package com.mcverse.jobify.support;

import com.jayway.jsonpath.JsonPath;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/** Shared helpers for MockMvc integration tests against the seeded in-memory database. */
public final class ApiTestSupport {

    private ApiTestSupport() {}

    /** Logs in a seeded account (password "password") and returns a ready-to-use Authorization header value. */
    public static String bearer(MockMvc mvc, String username) throws Exception {
        String body = mvc.perform(post("/auth/login").contentType(APPLICATION_JSON)
                        .content("{\"username\":\"" + username + "\",\"password\":\"password\"}"))
                .andReturn().getResponse().getContentAsString();
        return "Bearer " + JsonPath.read(body, "$.token");
    }
}
