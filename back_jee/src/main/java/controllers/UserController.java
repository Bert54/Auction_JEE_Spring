package controllers;

import bindings.JWTTokenNeeded;
import configuration.JWTKey;
import dto.LoginUserDto;
import dto.RegisterUserDto;
import entities.User;
import filters.JWTTokenNeededFilter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import services.UserService;

import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@RequestScoped
@Transactional
@Path("/users")
public class UserController {

    @Inject
    private UserService userService;

    @Context
    UriInfo uri;

    @POST
    @Path("/signup")
    public Response signupUser(RegisterUserDto newUser) {
        if (newUser == null) {
            return Response.status(400, "No data provided").build();
        }

        Boolean street = newUser.getStreet() != null;
        Boolean city = newUser.getCity() != null;
        Boolean postcode = newUser.getPostcode() != 0;
        Integer pstCode = new Integer(newUser.getPostcode());
        if( street || city || postcode){
            //System.out.println(street + " "+ city);
            if(!street || !city || !postcode){
                return Response.status(400,"incomplete address").build();
            }
            if(!pstCode.toString().matches("[0-9]{5}$")){
                return Response.status(400,"invalid postcode "+newUser.getPostcode()+" format : aaaaa").build();
            }

        }
        User user = this.userService.register(newUser);
        if (user == null) {
            return Response.status(409, "Username " +
                    newUser.getUsername() + " already exists.").build();
        }


        return Response.ok(user, MediaType.APPLICATION_JSON).status(201).build();
    }

    @POST
    @Path("/login")
    public Response loginUser(LoginUserDto user) {
        if (user == null) {
            return Response.status(400, "No data provided").build();
        }
        Boolean validated = this.userService.login(user);
        if (validated == null) {
            return Response.status(404, "Username " +
                    user.getUsername() + " was not found.").build();
        }
        if (!validated) {
            return Response.status(401, "Passwords for user " +
                    user.getUsername() + " do not match.").build();
        }
        return Response.ok("{\n\"token\":\"" + this.issueToken(this.userService.getUser(user.getUsername())) +
                "\"\n}", MediaType.APPLICATION_JSON).build();
    }

    @GET
    @JWTTokenNeeded
    public Response getLoggedInUser(@Context SecurityContext securityContext) {
        JWTTokenNeededFilter.UserInfo user = (JWTTokenNeededFilter.UserInfo)securityContext.getUserPrincipal();
        return Response.ok("{\n\"username\":\"" + user.getName() +
                "\"\n}", MediaType.APPLICATION_JSON).build();
    }

    @GET
    @JWTTokenNeeded
    @Path("/info")
    public Response getLoggedInUserInformations(@Context SecurityContext securityContext) {
        User user = this.userService.getUser(securityContext.getUserPrincipal().getName());
        return Response.ok(new User(user.getId(), user.getUsername(), null, user.getFirstName(), user.getLastName(),
                user.getStreet(), user.getCity(), user.getPostcode(), user.getHouseNumber()), MediaType.APPLICATION_JSON).build();
    }

    private String issueToken(User user) {
        String keyString = JWTKey.key;
        Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "AES");
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuer(uri.getAbsolutePath().toString() + "/login")
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(15000000L).atZone(ZoneId.systemDefault()).toInstant()))
                .claim("username", user.getUsername())
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

}
