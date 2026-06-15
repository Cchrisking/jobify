package com.mcverse.jobify.job.service;

import com.mcverse.jobify.common.exception.ResourceNotFoundException;
import com.mcverse.jobify.job.dto.JobPostResponse;
import com.mcverse.jobify.model.JobPost;
import com.mcverse.jobify.job.repository.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepo repo;

    public List<JobPostResponse> getJobs(Boolean available) {
        List<JobPost> posts = (available != null)
                ? repo.findAllByAvailable(available)
                : repo.findAll();
        return posts.stream().map(this::toResponse).toList();
    }

    public JobPostResponse addJob(JobPost jobPost) {
        return toResponse(repo.save(jobPost));
    }

    @Transactional
    public JobPostResponse updateAvailability(Integer id, boolean available) {
        JobPost job = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobPost", id.toString()));
        job.setAvailable(available);
        return toResponse(repo.save(job));
    }

    private JobPostResponse toResponse(JobPost job) {
        String employerUsername = job.getEmployer() != null ? job.getEmployer().getUsername() : null;
        return new JobPostResponse(job.getPostId(), job.getJobTitle(), job.getJobDescription(),
                job.getJobRating(), job.getHourlyRate(), employerUsername, job.isAvailable());
    }
}
