package com.mcverse.jobify.job.controller;

import com.mcverse.jobify.job.dto.JobPostResponse;
import com.mcverse.jobify.model.JobPost;
import com.mcverse.jobify.job.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping
    public List<JobPostResponse> getAllJobs() {
        return jobService.getJobs();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobPostResponse addJob(@RequestBody JobPost jobPost) {
        return jobService.addJob(jobPost);
    }
}
