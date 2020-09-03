package com.demo.api.skills.service;

import com.demo.api.skills.model.DeveloperSkills;
import com.demo.api.skills.model.Skill;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

@ApplicationScoped
public class SkillsService {

    @Inject
    MongoClient mongoClient;

    public Skill[] getSkills(String developerId) {
        Document document = Objects.requireNonNull(getCollection()
                        .find(Filters.eq("documentId", developerId))
                        .limit(1).first());

        ArrayList skillsList = (ArrayList)document.get("skills");

        Skill[] skillArray = new Skill[skillsList.size()];

        for(int i=0 ; i < skillsList.size(); i++){
            skillArray[i] = new Skill();

            skillArray[i].setName(((Document)skillsList.get(i)).get("name").toString());
            skillArray[i].setProficiency(((Document)skillsList.get(i)).get("proficiency").toString());
        }

        return skillArray;
    }

    public void addSkills(DeveloperSkills developerSkills){
        Skill[] skills = developerSkills.getSkills();
        Document[] skillDocs = new Document[skills.length];

        for(int i = 0; i < skills.length; i++){
            skillDocs[i] = new Document();

            skillDocs[i].append("name", skills[i].getName());
            skillDocs[i].append("proficiency", skills[i].getProficiency());
        }

        Document doc = new Document().append("documentId", developerSkills.getDeveloperId())
                .append("skills", Arrays.asList(developerSkills.getSkills()));

        getCollection().insertOne(doc);
    }

    private MongoCollection<Document> getCollection(){
        return mongoClient.getDatabase("myDB").getCollection("skills");
    }
}