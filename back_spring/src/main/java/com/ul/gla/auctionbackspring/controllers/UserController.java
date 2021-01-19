package com.ul.gla.auctionbackspring.controllers;

import com.ul.gla.auctionbackspring.configuration.JWTKey;
import com.ul.gla.auctionbackspring.dto.LoginUserDto;
import com.ul.gla.auctionbackspring.dto.RegisterUserDto;
import com.ul.gla.auctionbackspring.entities.User;
import com.ul.gla.auctionbackspring.exceptions.UserAlreadyExistsException;
import com.ul.gla.auctionbackspring.exceptions.UserNotFoundException;
import com.ul.gla.auctionbackspring.exceptions.UserUnauthorizedException;
import com.ul.gla.auctionbackspring.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RestController
@RequestMapping(value = "auctions/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public User signupUser(@RequestBody RegisterUserDto newUser) {
        User user = this.userService.signup(newUser);
        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "User with username " + newUser.getUsername() + " already exists",
                    new UserAlreadyExistsException("Username already exists"));
        }
        return user;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String loginUser(@RequestBody LoginUserDto user, HttpServletRequest request) {
        Boolean validated = this.userService.login(user);
        if (validated == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User " + user.getUsername() + " was not found on the server",
                    new UserNotFoundException("User not found"));
        }
        if (!validated) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Passwords do not match",
                    new UserUnauthorizedException("Wrong password"));
        }
        return "{\n\"token\":\"" + this.issueToken(this.userService.getUser(user.getUsername()),
                request.getRequestURI()) + "\"\n}";
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getLoggedInUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return "{\n\"username\":\"" + name +"\"\n}";
    }

    private String issueToken(User user, String uri) {
        String keyString = JWTKey.key;
        Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "AES");
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuer(uri)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(15000000L).atZone(ZoneId.systemDefault()).toInstant()))
                .claim("username", user.getUsername())
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

}
