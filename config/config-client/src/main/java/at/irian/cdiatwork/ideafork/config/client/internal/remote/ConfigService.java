package at.irian.cdiatwork.ideafork.config.client.internal.remote;

import at.irian.cdiatwork.ideafork.remote.api.ResourceClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@ResourceClient(name = "configs", version = "v1")
public interface ConfigService {
    @GET
    @Path("/{key}")
    String loadForKey(@PathParam("key") String key);
}
