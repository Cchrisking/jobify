package com.mcverse.jobify.config;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtConfigTest {

    private static final String STRONG = Base64.getEncoder().encodeToString(new byte[32]);

    private static JwtConfig config(String secret, String... profiles) {
        MockEnvironment env = new MockEnvironment();
        env.setActiveProfiles(profiles);
        JwtConfig config = new JwtConfig(env);
        config.setSecret(secret);
        return config;
    }

    @Test
    void acceptsStrongPrivateSecret() {
        assertDoesNotThrow(config(STRONG, "prod")::validateSecret);
    }

    @Test
    void rejectsMissingSecret() {
        assertThrows(IllegalStateException.class, config(null, "prod")::validateSecret);
        assertThrows(IllegalStateException.class, config("  ", "prod")::validateSecret);
    }

    @Test
    void rejectsNonBase64Secret() {
        assertThrows(IllegalStateException.class, config("not base64 !!", "prod")::validateSecret);
    }

    @Test
    void rejectsShortSecret() {
        String tooShort = Base64.getEncoder().encodeToString(new byte[16]);
        assertThrows(IllegalStateException.class, config(tooShort, "prod")::validateSecret);
    }

    @Test
    void placeholderOnlyAllowedInDevProfile() {
        assertDoesNotThrow(config(JwtConfig.DEV_PLACEHOLDER_SECRET, "dev")::validateSecret);
        assertThrows(IllegalStateException.class,
                config(JwtConfig.DEV_PLACEHOLDER_SECRET, "prod")::validateSecret);
        assertThrows(IllegalStateException.class, config(JwtConfig.DEV_PLACEHOLDER_SECRET)::validateSecret);
    }
}
