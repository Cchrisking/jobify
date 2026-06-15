package com.mcverse.jobify.config;

import com.mcverse.jobify.job.repository.JobRepo;
import com.mcverse.jobify.model.JobPost;
import com.mcverse.jobify.user.model.Employer;
import com.mcverse.jobify.user.repository.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Separated from DataInitializer so @Transactional is effective through the Spring proxy.
 * Employers must be managed entities when job posts that reference them are persisted.
 */
@Service
public class SeedService {

    @Autowired private EmployerRepository employerRepo;
    @Autowired private JobRepo jobRepo;

    @Transactional
    public void seedJobPosts() {
        Employer techcorp      = employerRepo.findByUsername("techcorp").orElseThrow();
        Employer startupxyz    = employerRepo.findByUsername("startupxyz").orElseThrow();
        Employer financegroup  = employerRepo.findByUsername("financegroup").orElseThrow();

        jobRepo.saveAll(List.of(
                // ── TechCorp ──────────────────────────────────────────────────────────
                post("Senior Frontend Developer",
                        "Senior React and TypeScript developer needed to lead our web platform. " +
                        "5+ years of hands-on experience with React, Next.js, and REST APIs required.",
                        4.5, 75.00, techcorp),

                post("Backend Engineer",
                        "Senior Spring Boot developer for a high-throughput fintech platform. " +
                        "5+ years of Java experience, strong knowledge of JPA and microservices.",
                        4.7, 72.50, techcorp),

                post("Junior QA Engineer",
                        "Entry-level QA engineer to join our quality team. " +
                        "0-2 years of testing experience. Training provided — great way to start your career.",
                        4.0, 35.00, techcorp),

                // ── StartupXYZ ───────────────────────────────────────────────────────
                post("Product Designer",
                        "Mid-level UX/UI designer with 3+ years of experience in Figma, " +
                        "user research, and design systems. You will own the end-to-end design process.",
                        4.2, 55.00, startupxyz),

                post("DevOps Engineer",
                        "Senior DevOps engineer with 7+ years of experience managing cloud infrastructure. " +
                        "AWS-certified preferred. Expertise in Kubernetes, Terraform, and CI/CD pipelines.",
                        4.8, 85.00, startupxyz),

                post("Full-Stack Developer",
                        "Mid-level full-stack developer with 2-5 years of experience in React and Node.js. " +
                        "You will ship features across our entire product stack.",
                        4.3, 60.00, startupxyz),

                // ── Finance Group ─────────────────────────────────────────────────────
                post("Senior Data Scientist",
                        "Senior data scientist with 5+ years of experience in Python, scikit-learn, and " +
                        "financial modelling. You will build predictive models for risk and revenue.",
                        4.6, 80.00, financegroup),

                post("Junior Business Analyst",
                        "Entry-level business analyst. 1+ years of experience in data analysis or finance. " +
                        "You will support senior analysts with reporting and dashboard creation.",
                        3.9, 40.00, financegroup),

                post("ML Engineer",
                        "Senior machine learning engineer with 5+ years of experience. " +
                        "Deep learning and NLP expertise required. You will productionise ML models at scale.",
                        4.9, 90.00, financegroup)
        ));
    }

    private static JobPost post(String title, String description, double rating,
                                double rate, Employer employer) {
        JobPost p = new JobPost(title, description, rating, rate);
        p.setEmployer(employer);
        return p;
    }
}
