package controllers;

import bindings.JWTTokenNeeded;
import dto.AddArticleDto;
import entities.Article;
import services.ArticleService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
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
@Path("/articles")
public class ArticleController {

    @Inject
    private ArticleService articleService;

    @POST
    @JWTTokenNeeded
    @Path("/add")
    public Response addArticle(@Context SecurityContext securityContext,
                               AddArticleDto newArticle) {
        if (newArticle == null) {
            return Response.status(400, "No data provided").build();
        }
        if (newArticle.getCategories() == null || newArticle.getEndingDate() == 0) {
            return Response.status(400, "Malformed body").build();
        }
        newArticle.setName(securityContext.getUserPrincipal().getName());
        newArticle.setActualPrice(newArticle.getStartingPrice());
        Article article = this.articleService.addArticle(newArticle);
        return Response.ok(article, MediaType.APPLICATION_JSON).status(201).build();
    }


}
