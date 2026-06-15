package com.mcverse.jobify.user.model;

import jakarta.persistence.*;

@Entity
@Table(name = "seekers")
public class Seeker extends User {

    private boolean isIndependent;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "job_preferences_id")
    private JobPreferences jobPreferences;

    protected Seeker() {}

    public Seeker(String name, String lastName, String username, boolean isIndependent) {
        super(name, lastName, username);
        this.isIndependent = isIndependent;
    }

    public boolean isIndependent()             { return isIndependent; }
    public JobPreferences getJobPreferences()  { return jobPreferences; }

    public void setIndependent(boolean isIndependent)              { this.isIndependent = isIndependent; }
    public void setJobPreferences(JobPreferences jobPreferences)   { this.jobPreferences = jobPreferences; }
}
