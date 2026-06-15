package com.mcverse.jobify.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    private String secret;
    private long expiration = 86400000L;

    public String getSecret()             { return secret; }
    public long getExpiration()           { return expiration; }

    public void setSecret(String secret)           { this.secret = secret; }
    public void setExpiration(long expiration)     { this.expiration = expiration; }
}
