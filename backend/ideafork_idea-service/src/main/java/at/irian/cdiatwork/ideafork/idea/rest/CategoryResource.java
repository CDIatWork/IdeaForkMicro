package at.irian.cdiatwork.ideafork.idea.rest;

import at.irian.cdiatwork.ideafork.idea.config.IdeaConfig;
import at.irian.cdiatwork.ideafork.idea.domain.CategoryView;
import at.irian.cdiatwork.ideafork.idea.repository.IdeaRepository;
import at.irian.cdiatwork.ideafork.jwt.api.AuthenticationRequired;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@AuthenticationRequired
@Path("categories")

@ApplicationScoped
public class CategoryResource {
    @Inject
    private IdeaRepository ideaRepository;

    @Inject
    private IdeaConfig ideaConfig;

    @GET
    @Path("top")
    public List<CategoryView> getHighestRatedCategories() {
        List<CategoryView> result = ideaRepository.getHighestRatedCategories(ideaConfig.maxNumberOfHighestRatedCategories());
        return result;
    }
}
