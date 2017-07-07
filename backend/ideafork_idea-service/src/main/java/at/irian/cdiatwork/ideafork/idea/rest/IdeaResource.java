package at.irian.cdiatwork.ideafork.idea.rest;

import at.irian.cdiatwork.ideafork.idea.domain.Idea;
import at.irian.cdiatwork.ideafork.idea.domain.IdeaValidator;
import at.irian.cdiatwork.ideafork.idea.event.IdeaChangedBroadcaster;
import at.irian.cdiatwork.ideafork.idea.repository.IdeaRepository;
import at.irian.cdiatwork.ideafork.idea.rest.request.IdeaCreationRequest;
import at.irian.cdiatwork.ideafork.idea.rest.request.IdeaUpdateRequest;
import at.irian.cdiatwork.ideafork.idea.rest.response.IdeaResponse;
import at.irian.cdiatwork.ideafork.jwt.api.AuthenticationRequired;
import at.irian.cdiatwork.ideafork.jwt.api.IdentityHolder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@AuthenticationRequired
@Path("ideas")

@ApplicationScoped
public class IdeaResource {
    @Inject
    private IdentityHolder identityHolder;

    @Inject
    private IdeaRepository ideaRepository;

    @Inject
    private IdeaValidator ideaValidator;

    @Inject
    private IdeaChangedBroadcaster ideaChangedBroadcaster;

    @GET
    public List<IdeaResponse> loadIdeasForCurrentUser(@QueryParam("filter") String filter) {
        List<Idea> loadedIdeas;
        if (filter == null) {
            String email = identityHolder.getAuthenticatedEMail();

            loadedIdeas = ideaRepository.loadAllOfAuthor(email);
        } else {
            loadedIdeas = ideaRepository.search(filter);
        }

        return loadedIdeas.stream().map(IdeaResponse::new).collect(Collectors.toList());
    }

    @GET
    @Path("{id}")
    public IdeaResponse loadIdea(@PathParam("id") String idOfIdea) {
        Idea loadedIdea = ideaRepository.findBy(idOfIdea);
        return new IdeaResponse(loadedIdea);
    }

    @POST
    public Response save(IdeaCreationRequest ideaCreationRequest) {
        String email = identityHolder.getAuthenticatedEMail();

        Idea idea = new Idea(ideaCreationRequest.baseIdeaId, ideaCreationRequest.topic, ideaCreationRequest.category, email);
        idea.setDescription(ideaCreationRequest.description);

        if (!this.ideaValidator.checkIdea(idea)) {
            return Response.status(Response.Status.BAD_REQUEST).build(); //here we could also provide a message with a reason
        }

        idea = ideaRepository.save(idea);
        ideaChangedBroadcaster.onIdeaChange(idea);
        return Response.ok().entity(new IdeaResponse(idea)).build();
    }

    @PUT
    public Response update(IdeaUpdateRequest ideaUpdateRequest) {
        Idea foundIdea = ideaRepository.findBy(ideaUpdateRequest.id);

        if (foundIdea == null) {
            return Response.status(Response.Status.GONE).build();
        }

        String email = identityHolder.getAuthenticatedEMail();

        if (!foundIdea.getAuthorEmail().equals(email)) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        foundIdea.setTopic(ideaUpdateRequest.topic);
        foundIdea.setCategory(ideaUpdateRequest.category);
        foundIdea.setDescription(ideaUpdateRequest.description);

        if (!this.ideaValidator.checkIdea(foundIdea)) {
            return Response.status(Response.Status.BAD_REQUEST).build(); //here we could also provide a message with a reason
        }

        Idea idea = ideaRepository.save(foundIdea);
        ideaChangedBroadcaster.onIdeaChange(idea);

        return Response.ok().entity(new IdeaResponse(idea)).build();
    }

    @POST
    @Path("import")
    public void importIdeaUpload(String ideaString) {
        if (ideaString == null) {
            throw new IllegalArgumentException("No idea to import");
        }

        String[] ideaToImport = ideaString.split(";");

        if (ideaToImport.length >= 2) {
            Idea newIdea = new Idea(null, ideaToImport[0], ideaToImport[1], identityHolder.getAuthenticatedEMail());

            if (ideaToImport.length == 3) {
                newIdea.setDescription(ideaToImport[2]);
            }
            newIdea = ideaRepository.save(newIdea);
            ideaChangedBroadcaster.onIdeaChange(newIdea);
        } else {
            throw new IllegalArgumentException("invalid idea to import: " + ideaString);
        }
    }

    @DELETE
    @Path("/{id}")
    public void removeIdea(@PathParam("id") String idOfIdea) {
        Idea loadedIdea = ideaRepository.findBy(idOfIdea);

        if (loadedIdea != null) {
            ideaRepository.attachAndRemove(loadedIdea);
        }
    }
}
