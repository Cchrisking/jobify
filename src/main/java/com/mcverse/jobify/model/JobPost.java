package com.mcverse.jobify.model;
public class JobPost {
  int postId;
  private String jobTitle;
  private String jobDescription;
  private double jobRating;
  private double hourlyRate;
  public JobPost(int postId, String jobTitle, String jobDescription,
                 float jobRating,
                 float hourlyRate) {
    this.postId = postId;
    this.jobTitle = jobTitle;
    this.jobDescription = jobDescription;
    this.jobRating = jobRating;
    this.hourlyRate = hourlyRate;
  }
  public int getPostId() {
    return postId;
  }
  public void setPostId(int postId) {
    this.postId = postId;
  }
  public String getJobTitle() {
    return this.jobTitle;
  }
  public String getJobDescription() {
    return this.jobDescription;
  }
  public double getJobRating() {
    return this.jobRating;
  }
  public double getHourlyRate() {
    return this.hourlyRate;
  }
  public void setJobRating(double jobRating) {
    this.jobRating = jobRating;
  }
  public void setJobTitle(String jobTitle) {

    this.jobTitle = jobTitle;
  }
  public void setJobDescription(
      String jobDescription) {
    this.jobDescription = jobDescription;
  }
  public void setJobRating(
      float jobRating) {
    this.jobRating = jobRating;
  }
  public void setHourlyRate(float hourlyRate) {

    this.hourlyRate = hourlyRate;
  }
};
