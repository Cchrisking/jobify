package com.mcverse.jobify.job.service;

import com.mcverse.jobify.job.dto.JobPostResponse;
import com.mcverse.jobify.model.JobPost;
import com.mcverse.jobify.job.repository.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepo repo;

    public List<JobPostResponse> getJobs() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    public JobPostResponse addJob(JobPost jobPost) {
        return toResponse(repo.save(jobPost));
    }

    private JobPostResponse toResponse(JobPost job) {
        String employerUsername = job.getEmployer() != null ? job.getEmployer().getUsername() : null;
        return new JobPostResponse(job.getPostId(), job.getJobTitle(), job.getJobDescription(),
                job.getJobRating(), job.getHourlyRate(), employerUsername);
    }
}
