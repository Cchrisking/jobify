package com.mcverse.jobify.user.model;

import com.mcverse.jobify.common.validation.ValidSalaryRange;
import com.mcverse.jobify.model.EmploymentType;

import java.util.ArrayList;
import java.util.List;

@ValidSalaryRange
public class JobPreferences {

    private String preferredJobTitle;
    private String locationPreferences;
    private double minSalaryExpectation;
    private double maxSalaryExpectation;
    private EmploymentType employmentType;
    private String workingHours;
    private List<String> skills;
    private String experienceLevel;

    public JobPreferences() {
        this.skills = new ArrayList<>();
    }

    public String getPreferredJobTitle()       { return preferredJobTitle; }
    public String getLocationPreferences()     { return locationPreferences; }
    public double getMinSalaryExpectation()    { return minSalaryExpectation; }
    public double getMaxSalaryExpectation()    { return maxSalaryExpectation; }
    public EmploymentType getEmploymentType()  { return employmentType; }
    public String getWorkingHours()            { return workingHours; }
    public List<String> getSkills()            { return skills; }
    public String getExperienceLevel()         { return experienceLevel; }

    public void setPreferredJobTitle(String preferredJobTitle)       { this.preferredJobTitle = preferredJobTitle; }
    public void setLocationPreferences(String locationPreferences)   { this.locationPreferences = locationPreferences; }
    public void setMinSalaryExpectation(double minSalaryExpectation) { this.minSalaryExpectation = minSalaryExpectation; }
    public void setMaxSalaryExpectation(double maxSalaryExpectation) { this.maxSalaryExpectation = maxSalaryExpectation; }
    public void setEmploymentType(EmploymentType employmentType)     { this.employmentType = employmentType; }
    public void setWorkingHours(String workingHours)                 { this.workingHours = workingHours; }
    public void setSkills(List<String> skills)                       { this.skills = skills; }
    public void setExperienceLevel(String experienceLevel)           { this.experienceLevel = experienceLevel; }
}
