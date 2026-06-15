package com.mcverse.jobify.job.model;

import com.mcverse.jobify.model.EmploymentType;
import com.mcverse.jobify.model.Region;
import com.mcverse.jobify.model.Skill;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Job {

    private String id;
    private String title;
    private String description;
    private EmploymentType employmentType;
    private List<Skill> requiredSkills;
    private Map<String, Double> salaryRange;
    private Region location;
    private LocalDateTime datePosted;
    private LocalDateTime lastUpdate;
    private double jobRating;
    private double hourlyRate;

    public Job(String id, String title, String description, EmploymentType employmentType,
               List<Skill> requiredSkills, Map<String, Double> salaryRange, Region location) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.employmentType = employmentType;
        this.requiredSkills = requiredSkills;
        this.salaryRange = salaryRange;
        this.location = location;
        this.datePosted = LocalDateTime.now();
        this.lastUpdate = LocalDateTime.now();
    }

    public String getId()                        { return id; }
    public String getTitle()                     { return title; }
    public String getDescription()               { return description; }
    public EmploymentType getEmploymentType()    { return employmentType; }
    public List<Skill> getRequiredSkills()       { return requiredSkills; }
    public Map<String, Double> getSalaryRange() { return salaryRange; }
    public Region getLocation()                { return location; }
    public LocalDateTime getDatePosted()       { return datePosted; }
    public LocalDateTime getLastUpdate()       { return lastUpdate; }
    public double getJobRating()               { return jobRating; }
    public double getHourlyRate()              { return hourlyRate; }

    public void setEmploymentType(EmploymentType employmentType) { this.employmentType = employmentType; }
    public void setJobRating(double jobRating)                   { this.jobRating = jobRating; }
    public void setHourlyRate(double hourlyRate)                 { this.hourlyRate = hourlyRate; }
    public void setLastUpdate(LocalDateTime lastUpdate)          { this.lastUpdate = lastUpdate; }
}
