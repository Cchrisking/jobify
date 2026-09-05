package com.mcverse.jobify.model;

import com.mcverse.jobify.user.model.Employer;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "job_posts")
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_id")
    private Employer employer;

    @Column(nullable = false)
    private String jobTitle;

    @Lob
    private String jobDescription;

    private double jobRating;
    private double hourlyRate;

    private String location;

    @Enumerated(EnumType.STRING)
    private WorkMode workMode;

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @Column(nullable = false)
    private boolean available = true;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "job_required_skills",
            joinColumns = @JoinColumn(name = "job_post_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> requiredSkills = new ArrayList<>();

    protected JobPost() {}

    public JobPost(String jobTitle, String jobDescription, double jobRating, double hourlyRate) {
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.jobRating = jobRating;
        this.hourlyRate = hourlyRate;
        this.available = true;
        this.createdAt = LocalDateTime.now();
    }

    public Integer getPostId()         { return postId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getJobTitle()        { return jobTitle; }
    public String getJobDescription()  { return jobDescription; }
    public double getJobRating()       { return jobRating; }
    public double getHourlyRate()      { return hourlyRate; }
    public boolean isAvailable()       { return available; }

    public void setPostId(Integer postId)                { this.postId = postId; }
    public void setJobTitle(String jobTitle)             { this.jobTitle = jobTitle; }
    public void setJobDescription(String jobDescription) { this.jobDescription = jobDescription; }
    public void setJobRating(double jobRating)           { this.jobRating = jobRating; }
    public void setHourlyRate(double hourlyRate)         { this.hourlyRate = hourlyRate; }
    public void setAvailable(boolean available)          { this.available = available; }
    public Employer getEmployer()                        { return employer; }
    public void setEmployer(Employer employer)           { this.employer = employer; }
    public String getLocation()                          { return location; }
    public void setLocation(String location)              { this.location = location; }
    public WorkMode getWorkMode()                         { return workMode; }
    public void setWorkMode(WorkMode workMode)            { this.workMode = workMode; }
    public EmploymentType getEmploymentType()             { return employmentType; }
    public void setEmploymentType(EmploymentType employmentType) { this.employmentType = employmentType; }
    public List<Skill> getRequiredSkills()                { return requiredSkills; }
    public void setRequiredSkills(List<Skill> requiredSkills) { this.requiredSkills = requiredSkills; }
}
