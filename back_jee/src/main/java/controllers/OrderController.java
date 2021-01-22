package controllers;

import bindings.JWTTokenNeeded;
import dto.CreateOrderDto;
import entities.Article;
import entities.Order;
import services.ArticleService;
import services.OrderService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.persistence.Column;
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
@Path("/orders")
public class OrderController {

    @Inject
    private OrderService orderService;

    @Inject
    private ArticleService articleService;

    @GET
    @JWTTokenNeeded
    public Response getOrders(@Context SecurityContext securityContext) {
        List<Order> orders = this.orderService.getOrdersByUsername(securityContext.getUserPrincipal().getName());
        return Response.ok(this.buildJsonArrayOrder(orders).build(), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @JWTTokenNeeded
    @Path("/{id}")
    public Response getOrder(@Context SecurityContext securityContext,
                             @PathParam("id") long id) {
        Order order = this.orderService.getOrderById(id);
        if (order == null) {
            return Response.status(404,
                    "Order with id " + id + " was not found").build();
        }
        String username = securityContext.getUserPrincipal().getName();
        if (!order.getBuyer().equals(username) &&
                !this.articleService.getArticle(order.getArticleId()).getSeller().equals(username)) {
            return Response.status(403,
                    "Order with id " + id + " was not created by user " +
                            username + " and is not the seller of its article").build();
        }
        return Response.ok(order, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @JWTTokenNeeded
    public Response createOrder(@Context SecurityContext securityContext,
                                CreateOrderDto newOrder) {
        if (newOrder == null) {
            return Response.status(400, "No data provided").build();
        }
        if (newOrder.getArticleId() == 0 || newOrder.getFirstname() == null || newOrder.getLastname() == null ||
                newOrder.getStreetName() == null || newOrder.getZipcode() < 10000 || newOrder.getZipcode() > 99999 ||
                newOrder.getCity() == null) {
            return Response.status(400, "Malformed body").build();
        }
        Article article = this.articleService.getArticle(newOrder.getArticleId());
        if (article == null) {
            return Response.status(404,
                    "Article with id " + newOrder.getArticleId() + " was not found").build();
        }
        if (article.getEndingDate() > System.currentTimeMillis() / 1000) {
            return Response.status(400,
                    "Article with id " + newOrder.getArticleId() + " has not reached its deadline").build();
        }
        if (!article.getLastBidder().equals(securityContext.getUserPrincipal().getName())) {
            return Response.status(403,
                    "Article with id " + newOrder.getArticleId() + " has not been won by " +
                            securityContext.getUserPrincipal().getName()).build();
        }
        Order order = this.orderService.getOrderByArticleId(newOrder.getArticleId());
        if (order != null) {
            return Response.status(409, "An order for article with id " +
                    newOrder.getArticleId() + " has already been created").build();
        }
        newOrder.setBuyer(securityContext.getUserPrincipal().getName());
        order =  this.orderService.saveOrder(newOrder);
        return Response.ok(order, MediaType.APPLICATION_JSON).status(201).build();
    }

    private JsonArrayBuilder buildJsonArrayOrder(List<Order> orders) {
        final Map<String, ?> config = Collections.emptyMap();
        JsonBuilderFactory factory = Json.createBuilderFactory(config);
        JsonArrayBuilder ordersJson = factory.createArrayBuilder();
        for (Order order : orders) {
            ordersJson.add(factory.createObjectBuilder()
                    .add("id", order.getId())
                    .add("buyer", order.getBuyer() + "")
                    .add("articleId", order.getArticleId())
                    .add("status", order.getStatus() + "")
                    .add("firstname", order.getFirstname() + "")
                    .add("lastname", order.getLastname() + "")
                    .add("street", order.getStreet() + "")
                    .add("zipcode", order.getZipcode())
                    .add("city", order.getCity() + ""));
        }
        return ordersJson;
    }

}
