package at.irian.cdiatwork.ideafork.idea.rest;

import at.irian.cdiatwork.ideafork.idea.domain.Idea;
import at.irian.cdiatwork.ideafork.idea.domain.PromotionRequest;
import at.irian.cdiatwork.ideafork.idea.repository.PromotionRepository;
import at.irian.cdiatwork.ideafork.idea.rest.request.IdeaPromotionRequest;
import at.irian.cdiatwork.ideafork.idea.rest.response.IdeaResponse;
import at.irian.cdiatwork.ideafork.jwt.api.AuthenticationRequired;
import at.irian.cdiatwork.ideafork.jwt.api.IdentityHolder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;

import static java.util.stream.Collectors.toList;

@AuthenticationRequired
@Path("promotions")

@ApplicationScoped
public class IdeaPromotionResource {
    @Inject
    private IdentityHolder identityHolder;

    @Inject
    private PromotionRepository promotionRepository;

    @GET
    public List<IdeaResponse> loadRecentlyPromotedIdeas() {
        String email = identityHolder.getAuthenticatedEMail();
        List<Idea> originalIdeaList = promotionRepository.loadRecentlyPromotedIdeas(email);
        return originalIdeaList.stream().map(IdeaResponse::new).map(IdeaResponse::fork).collect(toList());
    }

    @POST
    public Response promoteIdea(IdeaPromotionRequest selectedPromotionRequest) {
        PromotionRequest promotionRequest = promotionRepository.findBy(selectedPromotionRequest.id);

        if (promotionRequest == null) {
            return Response.status(Response.Status.GONE).build();
        }

        promotionRepository.promoteIdea(promotionRequest);
        return Response.ok().build();
    }
}
