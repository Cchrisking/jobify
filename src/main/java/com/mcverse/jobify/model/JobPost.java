package com.mcverse.jobify.model;

import com.mcverse.jobify.user.model.Employer;
import jakarta.persistence.*;

@Entity
@Table(name = "job_posts")
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_id")
    private Employer employer;

    @Column(nullable = false)
    private String jobTitle;

    @Column(length = 1000)
    private String jobDescription;

    private double jobRating;
    private double hourlyRate;

    protected JobPost() {}

    public JobPost(String jobTitle, String jobDescription, double jobRating, double hourlyRate) {
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.jobRating = jobRating;
        this.hourlyRate = hourlyRate;
    }

    public Integer getPostId()         { return postId; }
    public String getJobTitle()        { return jobTitle; }
    public String getJobDescription()  { return jobDescription; }
    public double getJobRating()       { return jobRating; }
    public double getHourlyRate()      { return hourlyRate; }

    public void setPostId(Integer postId)                { this.postId = postId; }
    public void setJobTitle(String jobTitle)             { this.jobTitle = jobTitle; }
    public void setJobDescription(String jobDescription) { this.jobDescription = jobDescription; }
    public void setJobRating(double jobRating)           { this.jobRating = jobRating; }
    public void setHourlyRate(double hourlyRate)         { this.hourlyRate = hourlyRate; }
    public Employer getEmployer()                        { return employer; }
    public void setEmployer(Employer employer)           { this.employer = employer; }
}
