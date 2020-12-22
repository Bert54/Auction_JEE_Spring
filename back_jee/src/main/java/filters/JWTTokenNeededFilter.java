package filters;

import bindings.JWTTokenNeeded;
import configuration.JWTKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import javax.annotation.Priority;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenNeededFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        Logger logger = Logger.getLogger("");
        String keyString = JWTKey.key;
        Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "AES");
        String token = null;
        try {
            String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
            token = authorizationHeader.substring("Bearer".length()).trim();
            Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            logger.info("#### valid token : " + token);
            SecurityContext originalContext = requestContext.getSecurityContext();
            Set<String> roles = new HashSet<>();
            roles.add("ADMIN");
            Authorizer authorizer = new Authorizer(roles, claims.getBody().get("username").toString(),
                    originalContext.isSecure());
            requestContext.setSecurityContext(authorizer);
        } catch (Exception e) {
            logger.severe("#### invalid token : " + token);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    public static class Authorizer implements SecurityContext {

        Set<String> roles;
        String username;
        boolean isSecure;

        public Authorizer(Set<String> roles, final String username,
                          boolean isSecure) {
            this.roles = roles;
            this.username = username;
            this.isSecure = isSecure;
        }

        @Override
        public Principal getUserPrincipal() {
            return new UserInfo(username);
        }

        @Override
        public boolean isUserInRole(String role) {
            return roles.contains(role);
        }

        @Override
        public boolean isSecure() {
            return isSecure;
        }

        @Override
        public String getAuthenticationScheme() {
            return "Your Scheme";
        }
    }

    public static class UserInfo implements Principal {
        String name;

        public UserInfo(String name) {
            this.name = name;
        }

        @Override
        public String getName() { return name; }
    }

}