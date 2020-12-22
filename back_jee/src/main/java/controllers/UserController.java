package controllers;

import configuration.JWTKey;
import dto.LoginUserDto;
import dto.RegisterUserDto;
import entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import services.UserService;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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
        User user = this.userService.register(newUser);
        if (user == null) {
            return Response.status(409, "Username " +
                    newUser.getUsername() + " already exists.").build();
        }
        return Response.ok(user, MediaType.APPLICATION_JSON).status(201).build();
    }

    @POST
    @Path("/login")
    public Response loginUser(LoginUserDto user) throws NoSuchAlgorithmException {
        Boolean validated = this.userService.login(user);
        if (validated == null) {
            return Response.status(404, "Username " +
                    user.getUsername() + " was not found.").build();
        }
        if (!validated) {
            return Response.status(401, "Passwords for user " +
                    user.getUsername() + " do not match.").build();
        }
        return Response.ok("{\n\"token\":\"" + this.issueToken(user.getUsername()) +
                "\"\n}", MediaType.APPLICATION_JSON).build();
    }

    private String issueToken(String login) throws NoSuchAlgorithmException {
        String keyString = JWTKey.key;
        Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "AES");
        return Jwts.builder()
                .setSubject(login)
                .setIssuer(uri.getAbsolutePath().toString() + "/login")
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(15L).atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, key)
                .claim("username", login)
                .compact();
    }

}
