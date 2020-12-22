package controllers;

import bindings.JWTTokenNeeded;
import filters.JWTTokenNeededFilter;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@RequestScoped
@Path("/test")
public class TestController {

    @GET
    @JWTTokenNeeded
    @Path("/hello")
    public Response hello(@Context SecurityContext securityContext) {
        JWTTokenNeededFilter.UserInfo user = (JWTTokenNeededFilter.UserInfo)securityContext.getUserPrincipal();
        return Response.ok("{\n\"message\":\"Hello " + user.getName() +
                "!\"\n}", MediaType.APPLICATION_JSON).build();
    }

}
