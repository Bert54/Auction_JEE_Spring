package controllers;

import bindings.JWTTokenNeeded;
import dto.AddArticleDto;
import dto.BidArticleDto;
import entities.Article;
import services.ArticleService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
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
        if (article == null) {
            return Response.status(500, "Couldn't create article").build();
        }
        return Response.ok(article, MediaType.APPLICATION_JSON).status(201).build();
    }

    @GET
    @JWTTokenNeeded
    public Response getAllArticles(@Context SecurityContext securityContext) {
        List<Article> articles = this.articleService.getArticles(
                securityContext.getUserPrincipal().getName());
        return Response.ok(this.buildJsonArrayArticle(articles).build(), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @JWTTokenNeeded
    @Path("/buyable")
    public Response getAllBuyableArticles(@Context SecurityContext securityContext) {
        List<Article> articles = this.articleService.getBuyableArticles(securityContext.getUserPrincipal().getName());
        return Response.ok(this.buildJsonArrayArticle(articles).build(), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{id}")
    public Response getOneArticle(@PathParam("id") long id) {
        Article article = this.articleService.getArticle(id);
        if (article == null) {
            return Response.status(404,
                    "Article with id " + id + " was not found").build();
        }
        return Response.ok(article, MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @JWTTokenNeeded
    @Path("/delete/{id}")
    public Response deleteOneArticle(@Context SecurityContext securityContext,
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
        long timestamp = System.currentTimeMillis() / 1000;
        if (article.getEndingDate() <= timestamp) {
            return Response.status(400,
                    "The article's end date with id " + id + " has been reached." +
                            " Articles with reached end dates cannot be deleted"
            ).build();
        }
        int result = this.articleService.deleteArticle(id);
        if (result == 0) {
            return Response.status(500,
                    "Could not delete article with id " + id).build();
        }
        return Response.ok(article, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/search")
    public Response getArticlesByFilter(@QueryParam("name") String name,
                                        @QueryParam("categories") String categories) {
        List<Article> articles = this.articleService.filterArticles(name, categories);
        return Response.ok(this.buildJsonArrayArticle(articles).build(), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @JWTTokenNeeded
    @Path("/bid")
    public Response bid(@Context SecurityContext securityContext,
                        BidArticleDto bid) {
        if (bid == null || bid.getId() == 0 || bid.getAmount() == 0) {
            return Response.status(400, "Malformed body").build();
        }
        Article article = this.articleService.getArticle(bid.getId());
        if (article == null) {
            return Response.status(404,
                    "Article with id " + bid.getId() + " was not found").build();
        }
        if (article.getEndingDate() <= System.currentTimeMillis() / 1000) {
            return Response.status(400,
                    "The article's end date with id " + bid.getId() + " has been reached." +
                            " Articles with reached end dates cannot be updated").build();
        }
        if (article.getCurrentPrice() >= bid.getAmount()) {
            return Response.status(400,
                    "The article's current bid with id " + bid.getId() + " is equal or higher" +
                            " than proposed amount").build();
        }
        bid.setBidder(securityContext.getUserPrincipal().getName());
        if (article.getSeller().equals(bid.getBidder())) {
            return Response.status(400,
                    "The bidder " + bid.getBidder() + " of article with id " + bid.getId() + " is also its seller." +
                            " Sellers cannot bid on their own article").build();
        }
        int status = this.articleService.updateArticle(bid);
        if (status == 0) {
            return Response.status(500,
                    "Could not update article with id " + bid.getId()).build();
        }
        return Response.ok(this.articleService.getArticle(bid.getId()), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @JWTTokenNeeded
    @Path("/userbids")
    public Response getUserBids(@Context SecurityContext securityContext) {
        List<Article> articles = this.articleService.getArticlesByUserBids(
                securityContext.getUserPrincipal().getName());
        return Response.ok(this.buildJsonArrayArticle(articles).build(), MediaType.APPLICATION_JSON).build();
    }

    private JsonArrayBuilder buildJsonArrayArticle(List<Article> articles) {
        final Map<String, ?> config = Collections.emptyMap();
        JsonBuilderFactory factory = Json.createBuilderFactory(config);
        JsonArrayBuilder articlesJson = factory.createArrayBuilder();
        for (Article article: articles) {
            articlesJson.add(factory.createObjectBuilder()
                    .add("id", article.getId())
                    .add("name", article.getName())
                    .add("description", article.getDescription() + "")
                    .add("startingPrice", article.getStartingPrice() + "")
                    .add("currentPrice", article.getCurrentPrice() + "")
                    .add("categories", article.getCategories() + "")
                    .add("endingDate", article.getEndingDate() + "")
                    .add("seller", article.getSeller() + "")
                    .add("lastBidder", article.getLastBidder() + ""));
        }
        return articlesJson;
    }


}
