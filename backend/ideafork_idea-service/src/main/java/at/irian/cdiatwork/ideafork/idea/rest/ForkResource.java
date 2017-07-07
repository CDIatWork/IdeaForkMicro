package at.irian.cdiatwork.ideafork.idea.rest;

import at.irian.cdiatwork.ideafork.idea.domain.Idea;
import at.irian.cdiatwork.ideafork.idea.repository.IdeaRepository;
import at.irian.cdiatwork.ideafork.idea.rest.response.IdeaResponse;
import at.irian.cdiatwork.ideafork.jwt.api.AuthenticationRequired;
import at.irian.cdiatwork.ideafork.jwt.api.IdentityHolder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@AuthenticationRequired
@Path("forks")

@ApplicationScoped
public class ForkResource {
    @Inject
    private IdentityHolder identityHolder;

    @Inject
    private IdeaRepository ideaRepository;

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public IdeaResponse forkIdea(String idOfIdea) {
        Idea originalIdea = ideaRepository.findBy(idOfIdea);

        if (originalIdea == null) {
            return null;
        }
        Idea result = new Idea(originalIdea, identityHolder.getAuthenticatedEMail());
        return new IdeaResponse(result).fork();
    }
}
