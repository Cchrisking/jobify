package com.mcverse.jobify.user.model;

import com.mcverse.jobify.job.model.Job;
import com.mcverse.jobify.model.JobPost;

import java.util.ArrayList;
import java.util.List;

public class Employer extends User {

    /** Aggregation — an Employer may or may not belong to a Company (0..1). */
    private Company company;

    /** Composition — Jobs posted directly by this Employer (0..*). */
    private List<Job> jobs;

    /** Composition — Job-post listings created by this Employer (0..*). */
    private List<JobPost> jobPosts;

    public Employer(String id, String name, String lastName, String password) {
        super(id, name, lastName, password);
        this.jobs = new ArrayList<>();
        this.jobPosts = new ArrayList<>();
    }

    public Company getCompany()       { return company; }
    public List<Job> getJobs()        { return jobs; }
    public List<JobPost> getJobPosts() { return jobPosts; }

    public void setCompany(Company company) { this.company = company; }
    public void addJob(Job job)             { this.jobs.add(job); }
    public void addJobPost(JobPost jobPost) { this.jobPosts.add(jobPost); }
}
