package com.mcverse.jobify.model;

import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Job {
    private Hashtable id;
    private String title;
    private String description;
    private List<Skill> requiredSkills;
    private Map<String, Double> salaryRange;
    private Region location;
    private LocalDateTime datePosted;
    private LocalDateTime lastUpdate;
    private double jobRating;
    private double hourlyRate;
    public Job(String title, String description, List<Skill> requiredSkills, Map<String, Double> salaryRange, Region location) {
        this.title = title;
        this.description = description;
        this.requiredSkills = requiredSkills;
        this.salaryRange = salaryRange;
        this.location = location;
        this.datePosted = LocalDateTime.now();
        this.lastUpdate = LocalDateTime.now();
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public List<Skill> getRequiredSkills() {
        return requiredSkills;
    }
    public Map<String, Double> getSalaryRange() {
        return salaryRange;
    }
    public Region getLocation() {
        return location;
    }
    public LocalDateTime getDatePosted() {
        return datePosted;
    }
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
}
