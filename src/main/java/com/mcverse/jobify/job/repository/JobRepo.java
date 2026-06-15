package com.mcverse.jobify.job.repository;

import com.mcverse.jobify.model.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepo extends JpaRepository<JobPost, Integer> {}
