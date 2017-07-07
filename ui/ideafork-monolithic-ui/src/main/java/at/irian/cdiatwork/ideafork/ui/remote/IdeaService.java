package at.irian.cdiatwork.ideafork.ui.remote;

import at.irian.cdiatwork.ideafork.remote.api.ResourceClient;
import at.irian.cdiatwork.ideafork.ui.remote.dto.Idea;

import javax.ws.rs.*;
import java.util.List;

@ResourceClient(name = "ideas", version = "v1")
public interface IdeaService {
    @POST
    void save(Idea idea);

    @PUT
    void update(Idea idea); //Technically it could be merged with #save. It's just that way to follow REST.

    @GET
    @Path("/{id}")
    Idea loadById(@PathParam("id") String id);

    @GET
    List<Idea> loadAll();

    @DELETE
    @Path("/{id}")
    void remove(@PathParam("id") String id);

    @GET
    List<Idea> searchIdea(@QueryParam("filter") String searchText);
}
