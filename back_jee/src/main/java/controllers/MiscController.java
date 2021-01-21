package controllers;

import services.MiscService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@RequestScoped
@Path("/miscs")
public class MiscController {

    @Inject
    private MiscService miscellaneousService;

    @GET
    @Path("/offers")
    public Response getCurrentOffers() {
        return Response.ok(this.miscellaneousService.getCurrentOffer(), MediaType.APPLICATION_JSON).build();
    }

}
