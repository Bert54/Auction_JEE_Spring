package configuration;

import filters.CORSFilter;
import filters.JWTTokenNeededFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class JAXRSConfiguration extends ResourceConfig {

    public JAXRSConfiguration() {

        packages("");
        register(CORSFilter.class);
        register(JWTTokenNeededFilter.class);
        property(ServerProperties.TRACING, "ALL");

    }

}
