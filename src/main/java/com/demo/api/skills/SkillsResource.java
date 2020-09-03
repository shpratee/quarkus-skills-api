package com.demo.api.skills;

import com.demo.api.skills.model.DeveloperSkills;
import com.demo.api.skills.model.Skill;
import com.demo.api.skills.service.SkillsService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/skills")
public class SkillsResource {

    @Inject
    SkillsService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Skill[] getSkills(@QueryParam("developerId") String developerId) {
        return service.getSkills(developerId);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addSkill(DeveloperSkills developerSkills) {
        service.addSkills(developerSkills);
        return Response.status(Response.Status.CREATED).build();
    }
}