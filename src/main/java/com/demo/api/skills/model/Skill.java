package com.demo.api.skills.model;

import org.bson.Document;

public class Skill {
    private String name;
    private String proficiency;

    public Skill(){}

    public Skill(String name, String proficiency){
        this.name = name;
        this.proficiency = proficiency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }

    public static Skill from(Document doc){
        return new Skill(doc.getString("name"), doc.getString("proficiency"));
    }
}
