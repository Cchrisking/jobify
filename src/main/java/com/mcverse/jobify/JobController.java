package com.mcverse.jobify;

import com.mcverse.jobify.model.JobPost;
import com.mcverse.jobify.repo.JobRepo;
import com.mcverse.jobify.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class JobController {
  JobRepo jobRepo = new JobRepo();
  @Autowired
  private JobService jobService;
  @GetMapping({"/", "home"})
  public String home() {
    return "This is home";
  }
  @PostMapping("addjob")
  public String addJob() {
    return "adding job";
    }
    @GetMapping("alljobs")
  public ArrayList<JobPost> allJobs() {
    return jobRepo.getJobs();
    }
};
