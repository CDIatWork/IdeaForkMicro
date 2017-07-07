package at.irian.cdiatwork.ideafork.idea.rest;

import at.irian.cdiatwork.ideafork.idea.domain.Idea;
import at.irian.cdiatwork.ideafork.idea.domain.PromotionRequest;
import at.irian.cdiatwork.ideafork.idea.repository.IdeaRepository;
import at.irian.cdiatwork.ideafork.idea.repository.PromotionRepository;
import at.irian.cdiatwork.ideafork.idea.rest.request.IdeaPromotionRequest;
import at.irian.cdiatwork.ideafork.jwt.api.AuthenticationRequired;
import at.irian.cdiatwork.ideafork.jwt.api.IdentityHolder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@AuthenticationRequired
@Path("promotions/requests")

@ApplicationScoped
public class IdeaPromotionRequestResource {
    @Inject
    private IdentityHolder identityHolder;

    @Inject
    private PromotionRepository promotionRepository;

    @Inject
    private IdeaRepository ideaRepository;

    @GET
    public List<IdeaPromotionRequest> loadRecentIdeaPromotions(@QueryParam("searchHint") String searchHint) {
        String email = identityHolder.getAuthenticatedEMail();

        if (searchHint == null) {
            searchHint = "*";
        }
        return Optional.ofNullable(promotionRepository.loadRecentIdeaPromotions(email, searchHint)).orElse(emptyList())
                .stream().map(IdeaPromotionRequest::convert).collect(toList());
    }

    @POST
    public Response requestIdeaPromotion(IdeaPromotionRequest ideaPromotionRequest) {
        Idea storedIdea = ideaRepository.findBy(ideaPromotionRequest.ideaId);

        if (storedIdea == null) {
            return Response.status(Response.Status.GONE).build();
        }
        PromotionRequest promotionRequest = new PromotionRequest(storedIdea);
        promotionRequest.setDescription(ideaPromotionRequest.description);
        promotionRepository.save(promotionRequest);

        return Response.ok().build();
    }
}
