package at.irian.cdiatwork.ideafork.ui.remote;

import at.irian.cdiatwork.ideafork.remote.api.ResourceClient;
import at.irian.cdiatwork.ideafork.ui.remote.dto.Category;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@ResourceClient(name = "categories", version = "v1")
public interface CategoryService {
    @GET
    @Path("/top")
    List<Category> loadHighestRatedCategories();
}
