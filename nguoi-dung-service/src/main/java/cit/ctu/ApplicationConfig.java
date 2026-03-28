package cit.ctu;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api/rest")
public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() {
        packages("cit.ctu");
    }
}