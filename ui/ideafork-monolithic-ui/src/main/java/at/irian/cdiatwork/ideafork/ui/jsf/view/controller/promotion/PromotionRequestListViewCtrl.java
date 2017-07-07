package at.irian.cdiatwork.ideafork.ui.jsf.view.controller.promotion;

import at.irian.cdiatwork.ideafork.ui.jsf.view.config.Pages;
import at.irian.cdiatwork.ideafork.ui.remote.dto.PromotionRequest;
import at.irian.cdiatwork.ideafork.ui.remote.IdeaPromotionService;
import org.apache.deltaspike.core.api.config.view.controller.PreRenderView;
import org.apache.deltaspike.core.api.config.view.navigation.NavigationParameterContext;
import org.apache.deltaspike.core.api.scope.ConversationGroup;
import org.apache.deltaspike.core.api.scope.GroupedConversationScoped;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Named
@GroupedConversationScoped
@ConversationGroup(Pages.PromotionSelectionArea.class)
public class PromotionRequestListViewCtrl implements Serializable {

    @Inject
    private IdeaPromotionService ideaPromotionService;

    @Inject
    private NavigationParameterContext navigationParameterContext;

    private String searchHint;

    private List<PromotionRequest> foundPromotionRequests;

    @PreRenderView
    public void onPreRenderView() {
        foundPromotionRequests = Optional.ofNullable(ideaPromotionService.loadRecentIdeaPromotions(searchHint)).orElse(emptyList());
    }

    public Class<? extends Pages.PromotionSelectionArea> applyFilter() {
        navigationParameterContext.addPageParameter("searchHint", searchHint);
        return Pages.PromotionSelectionArea.ListPromotions.class;
    }

    /*
     * generated
     */

    public List<PromotionRequest> getFoundPromotionRequests() {
        return foundPromotionRequests;
    }

    public String getSearchHint() {
        return searchHint;
    }

    public void setSearchHint(String searchHint) {
        this.searchHint = searchHint;
    }
}
