package at.irian.cdiatwork.ideafork.history.rest;

import at.irian.cdiatwork.ideafork.history.domain.EntityChange;
import at.irian.cdiatwork.ideafork.history.repository.EntityChangeRepository;
import at.irian.cdiatwork.ideafork.history.rest.request.EntityChangeRequest;
import at.irian.cdiatwork.ideafork.jwt.api.AuthenticationRequired;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@AuthenticationRequired
@Path("archive")

@ApplicationScoped
public class EntityArchiveResource {
    @Inject
    private EntityChangeRepository entityChangeRepository;

    @POST
    public void archiveEntity(EntityChangeRequest entityChangeRequest) {
        EntityChange entityChange = new EntityChange(
                entityChangeRequest.id,
                entityChangeRequest.version,
                entityChangeRequest.entityAsJson,
                entityChangeRequest.creationTimestamp);

        entityChangeRepository.save(entityChange);
    }

    //not used currently - just to check the result via REST-API (+ valid JWT token)
    @GET
    @Path("/{id}/{version}")
    public EntityChange loadChange(@PathParam("id") String id,
                                   @PathParam("version") Long version) {
        return entityChangeRepository.findRevision(id, version);
    }
}
