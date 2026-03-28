package cit.ctu;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api/rest")
public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() {
        packages("cit.ctu");
    }
}