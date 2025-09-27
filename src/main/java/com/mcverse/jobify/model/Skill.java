package com.mcverse.jobify.model;

public class Skill {
  private int SkillID;
  private String SkillName;
  private String SkillDescription;
  public int getSkillID() {
    return SkillID;
  }
  public Skill(int SkillID, String SkillName, String SkillDescription) {
    this.SkillID = SkillID;
    this.SkillName = SkillName;
    this.SkillDescription = SkillDescription;
  }
  public void setSkillID(int skillID) {
    SkillID = skillID;
  }
  public String getSkillName() {
    return SkillName;
  }
  public void setSkillName(String skillName) {
    SkillName = skillName;
  }
  public String getSkillDescription() {
    return SkillDescription;
  }
  public void setSkillDescription(String skillDescription) {
    SkillDescription = skillDescription;
  }
}

