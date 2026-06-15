package com.mcverse.jobify.job.controller;

import com.mcverse.jobify.model.JobPost;
import com.mcverse.jobify.job.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping
    public List<JobPost> getAllJobs() {
        return jobService.getJobs();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addJob(@RequestBody JobPost jobPost) {
        jobService.addJob(jobPost);
    }
}
