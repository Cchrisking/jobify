package com.mcverse.jobify.cms.service;

import com.mcverse.jobify.cms.model.ContentCategory;
import com.mcverse.jobify.cms.model.ContentEntry;
import com.mcverse.jobify.cms.repository.ContentEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Seeds the default CMS content entries on every boot. Only inserts keys that
 * don't already exist, so an admin's saved edits are never overwritten.
 */
@Component
public class ContentSeeder implements ApplicationRunner {

    @Autowired
    private ContentEntryRepository contentEntryRepository;

    private static final List<ContentEntry> DEFAULTS = List.of(
            new ContentEntry("seo.title", ContentCategory.SEO,
                    "Jobify — Find Your Next Dream Opportunity"),
            new ContentEntry("seo.description", ContentCategory.SEO,
                    "Jobify is an AI-native hiring platform that matches you with your next dream opportunity."),
            new ContentEntry("seo.ogImage", ContentCategory.SEO, ""),

            new ContentEntry("landing.hero.badge", ContentCategory.LANDING,
                    "AI-matched opportunities, refreshed daily"),
            new ContentEntry("landing.hero.subtitle", ContentCategory.LANDING,
                    "Find your next dream opportunity — curated by your skills, your salary goals, and how teams actually work."),
            new ContentEntry("landing.hero.searchPlaceholder", ContentCategory.LANDING,
                    "Search job titles, skills, or companies…"),
            new ContentEntry("landing.hero.ctaLabel", ContentCategory.LANDING,
                    "Explore Job Search"),
            new ContentEntry("landing.newestJobs.title", ContentCategory.LANDING,
                    "Newest Jobs"),
            new ContentEntry("landing.newestJobs.emptyMessage", ContentCategory.LANDING,
                    "No jobs available right now — check back soon."),
            new ContentEntry("landing.footer.tagline", ContentCategory.LANDING,
                    "Jobify — AI-native hiring, 2026."),

            new ContentEntry("error.network", ContentCategory.ERROR_MESSAGE,
                    "Could not reach the server. It may be offline or your network connection may be down — please try again shortly."),
            new ContentEntry("error.timeout", ContentCategory.ERROR_MESSAGE,
                    "The request timed out. The server may be overloaded or unreachable — please try again."),
            new ContentEntry("error.generic", ContentCategory.ERROR_MESSAGE,
                    "Something went wrong."),
            new ContentEntry("error.searchEmptyMessage", ContentCategory.ERROR_MESSAGE,
                    "No jobs match your search and filters yet — try widening them."),
            new ContentEntry("error.jobsLoadFailedPrefix", ContentCategory.ERROR_MESSAGE,
                    "Couldn't load jobs:")
    );

    @Override
    public void run(ApplicationArguments args) {
        for (ContentEntry entry : DEFAULTS) {
            if (!contentEntryRepository.existsById(entry.getKey())) {
                contentEntryRepository.save(entry);
            }
        }
    }
}
