package com.demo.api.skills.model;

public class DeveloperSkills {
    private String developerId;
    private Skill[] skills;

    public String getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(String developerId) {
        this.developerId = developerId;
    }

    public Skill[] getSkills() {
        return skills;
    }

    public void setSkills(Skill[] skills) {
        this.skills = skills;
    }
}
