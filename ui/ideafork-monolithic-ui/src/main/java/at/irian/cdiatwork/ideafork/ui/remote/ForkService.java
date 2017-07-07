package at.irian.cdiatwork.ideafork.ui.remote;

import at.irian.cdiatwork.ideafork.remote.api.ResourceClient;
import at.irian.cdiatwork.ideafork.ui.remote.dto.Idea;

import javax.ws.rs.PUT;

@ResourceClient(name = "forks", version = "v1")
public interface ForkService {
    @PUT
    Idea forkIdea(String idOfIdea);
}
