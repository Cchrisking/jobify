package com.mcverse.jobify.service;

import com.mcverse.jobify.model.JobPost;
import com.mcverse.jobify.repo.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
//DTO
@Service
public class JobService {
  @Autowired
  private JobRepo repo;
  public void addJob(JobPost jobPost) {
    repo.addJob(jobPost);
  }
  public ArrayList<JobPost> getJobs() {
    return repo.getJobs();
  }
}
