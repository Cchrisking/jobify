package com.mcverse.jobify.job.repository;

import com.mcverse.jobify.model.JobPost;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JobRepo {

    private final List<JobPost> jobs = new ArrayList<>(List.of(
            new JobPost(1, "Frontend Developer",
                    "Seeking a React developer with experience in TypeScript and Next.js.",
                    4.5, 65.00),
            new JobPost(2, "Backend Engineer",
                    "Looking for a Java Spring Boot expert to build RESTful APIs for a fintech app.",
                    4.7, 72.50),
            new JobPost(3, "UX/UI Designer",
                    "Hiring a creative designer to improve the user experience of our mobile app.",
                    4.2, 50.00),
            new JobPost(4, "DevOps Specialist",
                    "Seeking an AWS-certified DevOps engineer to automate deployment pipelines.",
                    4.8, 80.00),
            new JobPost(5, "Data Scientist",
                    "Looking for a data scientist with machine learning and Python experience.",
                    4.6, 78.25)
    ));

    public List<JobPost> getJobs() {
        return jobs;
    }

    public void addJob(JobPost job) {
        jobs.add(job);
    }
}
