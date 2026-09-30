package com.mcverse.jobify.job.service;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.stereotype.Component;

/** Server-side allow-list sanitizer for rich-text job descriptions. */
@Component
public class HtmlSanitizer {

    private static final PolicyFactory POLICY = new HtmlPolicyBuilder()
            .allowElements("p", "br", "ul", "ol", "li", "strong", "em", "h2", "h3", "a")
            .allowUrlProtocols("http", "https", "mailto")
            .allowAttributes("href").onElements("a")
            .requireRelNofollowOnLinks()
            .toFactory();

    public String sanitize(String html) {
        return html == null ? null : POLICY.sanitize(html);
    }
}
