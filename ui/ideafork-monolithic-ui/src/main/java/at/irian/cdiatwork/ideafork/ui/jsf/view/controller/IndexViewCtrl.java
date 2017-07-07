package at.irian.cdiatwork.ideafork.ui.jsf.view.controller;

import at.irian.cdiatwork.ideafork.ui.jsf.view.JsfIdentityHolder;
import at.irian.cdiatwork.ideafork.ui.remote.CategoryService;
import at.irian.cdiatwork.ideafork.ui.remote.IdeaPromotionService;
import at.irian.cdiatwork.ideafork.ui.remote.dto.Category;
import at.irian.cdiatwork.ideafork.ui.remote.dto.Idea;
import org.apache.deltaspike.core.api.config.view.controller.PreRenderView;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@ViewController
public class IndexViewCtrl implements Serializable {
    @Inject
    private IdeaPromotionService ideaPromotionService;

    @Inject
    private CategoryService categoryService;

    @Inject
    private JsfIdentityHolder identityHolder;

    private List<Category> categories;
    private int categoryCount;

    private List<Idea> promotedIdeas;
    private int promotedIdeaCount;

    @PreRenderView
    public void onPreRenderView() {
        if (identityHolder.isAuthenticated()) {
            promotedIdeas = Optional.ofNullable(ideaPromotionService.loadRecentlyPromotedIdeas()).orElse(emptyList());
            categories = Optional.ofNullable(categoryService.loadHighestRatedCategories()).orElse(emptyList());

            categoryCount = categories.size();
            promotedIdeaCount = promotedIdeas.size();
        }
    }

    /*
     * generated
     */

    public int getCategoryCount() {
        return categoryCount;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public int getPromotedIdeaCount() {
        return promotedIdeaCount;
    }

    public List<Idea> getPromotedIdeas() {
        return promotedIdeas;
    }
}
