package com.mcverse.jobify.job.service;

import com.mcverse.jobify.model.JobPost;
import com.mcverse.jobify.job.repository.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepo repo;

    public List<JobPost> getJobs() {
        return repo.getJobs();
    }

    public void addJob(JobPost jobPost) {
        repo.addJob(jobPost);
    }
}
