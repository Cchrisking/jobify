package com.mcverse.jobify.model;

public class Skill {

    private int skillId;
    private String skillName;
    private String skillDescription;

    public Skill(int skillId, String skillName, String skillDescription) {
        this.skillId = skillId;
        this.skillName = skillName;
        this.skillDescription = skillDescription;
    }

    public int getSkillId()              { return skillId; }
    public String getSkillName()         { return skillName; }
    public String getSkillDescription()  { return skillDescription; }

    public void setSkillId(int skillId)                    { this.skillId = skillId; }
    public void setSkillName(String skillName)             { this.skillName = skillName; }
    public void setSkillDescription(String skillDescription) { this.skillDescription = skillDescription; }
}

