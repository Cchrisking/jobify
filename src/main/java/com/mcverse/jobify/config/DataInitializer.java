package com.mcverse.jobify.config;

import com.mcverse.jobify.job.repository.JobRepo;
import com.mcverse.jobify.model.JobPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private JobRepo jobRepo;

    @Override
    public void run(ApplicationArguments args) {
        if (jobRepo.count() > 0) return;

        jobRepo.saveAll(List.of(
                new JobPost("Frontend Developer",
                        "Seeking a React developer with experience in TypeScript and Next.js.",
                        4.5, 65.00),
                new JobPost("Backend Engineer",
                        "Looking for a Java Spring Boot expert to build RESTful APIs for a fintech app.",
                        4.7, 72.50),
                new JobPost("UX/UI Designer",
                        "Hiring a creative designer to improve the user experience of our mobile app.",
                        4.2, 50.00),
                new JobPost("DevOps Specialist",
                        "Seeking an AWS-certified DevOps engineer to automate deployment pipelines.",
                        4.8, 80.00),
                new JobPost("Data Scientist",
                        "Looking for a data scientist with machine learning and Python experience.",
                        4.6, 78.25)
        ));
    }
}
