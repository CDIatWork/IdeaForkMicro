package at.irian.cdiatwork.ideafork.user.remote;

import at.irian.cdiatwork.ideafork.remote.api.ResourceClient;
import at.irian.cdiatwork.ideafork.user.remote.request.EntityChangeRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.POST;

@ApplicationScoped
@ResourceClient(name = "archive", version = "v1")
public interface ArchiveResource {
    @POST
    void recordChange(EntityChangeRequest entityChangeRequest);
}
