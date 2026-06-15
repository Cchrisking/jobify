package com.mcverse.jobify.model;

public class JobPost {

    private int postId;
    private String jobTitle;
    private String jobDescription;
    private double jobRating;
    private double hourlyRate;

    public JobPost(int postId, String jobTitle, String jobDescription, double jobRating, double hourlyRate) {
        this.postId = postId;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.jobRating = jobRating;
        this.hourlyRate = hourlyRate;
    }

    public int getPostId()             { return postId; }
    public String getJobTitle()        { return jobTitle; }
    public String getJobDescription()  { return jobDescription; }
    public double getJobRating()       { return jobRating; }
    public double getHourlyRate()      { return hourlyRate; }

    public void setPostId(int postId)                    { this.postId = postId; }
    public void setJobTitle(String jobTitle)             { this.jobTitle = jobTitle; }
    public void setJobDescription(String jobDescription) { this.jobDescription = jobDescription; }
    public void setJobRating(double jobRating)           { this.jobRating = jobRating; }
    public void setHourlyRate(double hourlyRate)         { this.hourlyRate = hourlyRate; }
}
