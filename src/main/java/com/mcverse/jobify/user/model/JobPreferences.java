package com.mcverse.jobify.user.model;

public class JobPreferences {

    private String jobPreferences;
    private String locationPreferences;
    private String salaryExpectations;
    private String desiredJobType;
    private String workingHours;
    private String skills;
    private String experienceLevel;

    public String getJobPreferences()      { return jobPreferences; }
    public String getLocationPreferences() { return locationPreferences; }
    public String getSalaryExpectations()  { return salaryExpectations; }
    public String getDesiredJobType()      { return desiredJobType; }
    public String getWorkingHours()        { return workingHours; }
    public String getSkills()              { return skills; }
    public String getExperienceLevel()     { return experienceLevel; }

    public void setJobPreferences(String jobPreferences)           { this.jobPreferences = jobPreferences; }
    public void setLocationPreferences(String locationPreferences) { this.locationPreferences = locationPreferences; }
    public void setSalaryExpectations(String salaryExpectations)   { this.salaryExpectations = salaryExpectations; }
    public void setDesiredJobType(String desiredJobType)           { this.desiredJobType = desiredJobType; }
    public void setWorkingHours(String workingHours)               { this.workingHours = workingHours; }
    public void setSkills(String skills)                           { this.skills = skills; }
    public void setExperienceLevel(String experienceLevel)         { this.experienceLevel = experienceLevel; }
}
