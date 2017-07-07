package at.irian.cdiatwork.ideafork.config.rest;

import at.irian.cdiatwork.ideafork.jwt.api.AuthenticationRequired;
import org.apache.deltaspike.core.api.config.ConfigResolver;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

@AuthenticationRequired
@Path("configs")

@ApplicationScoped
public class ConfigResource {
    @GET
    @Path("/{key}")
    public String loadConfig(@PathParam("key") String key,
                             @QueryParam("defaultValue") String defaultValue) {
        String result = ConfigResolver.getProjectStageAwarePropertyValue(key, defaultValue);
        return result;
    }

    /*
    @POST
    public void updateConfig(@FormParam("key") String key,
                             @FormParam("value") String value) {

        configRepository.save(new ConfigEntry(key, value));
    }
    */
}
