package controllers;

import bindings.JWTTokenNeeded;
import dto.AddArticleDto;
import entities.Article;
import services.ArticleService;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
        if (newArticle.getName() == null || newArticle.getCategories() == null
                || newArticle.getEndingDate() == 0) {
            return Response.status(400, "Malformed body").build();
        }
        newArticle.setSeller(securityContext.getUserPrincipal().getName());
        newArticle.setCurrentPrice(newArticle.getStartingPrice());
        Article article = this.articleService.addArticle(newArticle);
        return Response.ok(article, MediaType.APPLICATION_JSON).status(201).build();
    }

    @GET
    @JWTTokenNeeded
    public Response getAllArticles(@Context SecurityContext securityContext) {
        List<Article> articles = this.articleService.getArticles(
                securityContext.getUserPrincipal().getName());
        final Map<String, ?> config = Collections.emptyMap();
        JsonBuilderFactory factory = Json.createBuilderFactory(config);
        JsonArrayBuilder articlesJson = factory.createArrayBuilder();
        for (Article article: articles) {
            articlesJson.add(factory.createObjectBuilder()
                    .add("id", article.getId())
                    .add("name", article.getName())
                    .add("description", article.getDescription())
                    .add("startingPrice", article.getStartingPrice())
                    .add("currentPrice", article.getCurrentPrice())
                    .add("categories", article.getCategories())
                    .add("endingDate", article.getEndingDate())
                    .add("seller", article.getSeller()));
        }
        return Response.ok(articlesJson.build(), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @JWTTokenNeeded
    @Path("{id}")
    public Response getOneArticle(@Context SecurityContext securityContext,
                                  @PathParam("id") long id) {
        Article article = this.articleService.getArticle(id);
        if (article == null) {
            return Response.status(404,
                    "Article with id " + id + " was not found").build();
        }
        if (!securityContext.getUserPrincipal().getName().equals(article.getSeller())) {
            return Response.status(403,
                    "Article with id " + id + " does not belong to user " +
                            securityContext.getUserPrincipal().getName()).build();
        }
        return Response.ok(article, MediaType.APPLICATION_JSON).build();
    }


}
