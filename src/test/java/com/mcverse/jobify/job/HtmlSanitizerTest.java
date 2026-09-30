package com.mcverse.jobify.job;

import com.mcverse.jobify.job.service.HtmlSanitizer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HtmlSanitizerTest {

    private final HtmlSanitizer sanitizer = new HtmlSanitizer();

    @Test
    void keepsAllowListedFormatting() {
        String html = "<h2>Role</h2><h3>Duties</h3><p>Hello <strong>bold</strong> <em>it</em><br />x</p>"
                + "<ul><li>a</li></ul><ol><li>b</li></ol>";
        String out = sanitizer.sanitize(html);
        assertTrue(out.contains("<h2>Role</h2>"));
        assertTrue(out.contains("<strong>bold</strong>"));
        assertTrue(out.contains("<ul><li>a</li></ul>"));
        assertTrue(out.contains("<ol><li>b</li></ol>"));
    }

    @Test
    void stripsScriptStyleIframeAndHandlers() {
        String out = sanitizer.sanitize("<script>alert(1)</script><style>p{}</style>"
                + "<iframe src=\"http://evil\"></iframe><img src=x onerror=alert(1)><p onclick=\"x()\">t</p>");
        assertFalse(out.contains("script"));
        assertFalse(out.contains("style"));
        assertFalse(out.contains("iframe"));
        assertFalse(out.contains("img"));
        assertFalse(out.contains("onerror"));
        assertFalse(out.contains("onclick"));
        assertTrue(out.contains("<p>t</p>"));
    }

    @Test
    void linksKeepHttpAndMailtoOnly() {
        assertTrue(sanitizer.sanitize("<a href=\"https://example.com\">x</a>").contains("href=\"https://example.com\""));
        assertTrue(sanitizer.sanitize("<a href=\"mailto:a@b.com\">x</a>").contains("href=\"mailto:"));
        assertFalse(sanitizer.sanitize("<a href=\"javascript:alert(1)\">x</a>").contains("javascript"));
        assertFalse(sanitizer.sanitize("<a href=\"data:text/html,x\">x</a>").contains("data:"));
    }

    @Test
    void externalLinksGetNofollow() {
        assertTrue(sanitizer.sanitize("<a href=\"https://example.com\">x</a>").contains("nofollow"));
    }

    @Test
    void plainTextSurvivesAndEmojiIsKeptAsAnHtmlEntity() {
        assertEquals("Join us", sanitizer.sanitize("Join us"));
        // Supplementary characters are written as numeric entities, which browsers render as the emoji.
        assertEquals("Join us &#x1f680;", sanitizer.sanitize("Join us 🚀"));
    }

    @Test
    void nullStaysNull() {
        assertNull(sanitizer.sanitize(null));
    }
}
