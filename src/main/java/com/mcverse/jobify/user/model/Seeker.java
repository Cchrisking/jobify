package com.mcverse.jobify.user.model;

import java.util.ArrayList;

public class Seeker extends User {

    private boolean isIndependent;
    private ArrayList<Cv> cv;
    private JobPreferences jobPreferences;

    public Seeker(String id, String name, String lastName, String password, boolean isIndependent) {
        super(id, name, lastName, password);
        this.isIndependent = isIndependent;
        this.cv = new ArrayList<>();
    }

    public boolean isIndependent() { return isIndependent; }

    public ArrayList<Cv> getCv()                 { return cv; }
    public JobPreferences getJobPreferences()     { return jobPreferences; }

    public void addCv(Cv cv)                              { this.cv.add(cv); }
    public void setJobPreferences(JobPreferences jobPreferences) { this.jobPreferences = jobPreferences; }
}
