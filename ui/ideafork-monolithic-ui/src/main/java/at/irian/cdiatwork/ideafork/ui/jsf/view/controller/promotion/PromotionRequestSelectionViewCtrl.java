package at.irian.cdiatwork.ideafork.ui.jsf.view.controller.promotion;

import at.irian.cdiatwork.ideafork.ui.jsf.view.config.Pages;
import at.irian.cdiatwork.ideafork.ui.remote.IdeaPromotionService;
import at.irian.cdiatwork.ideafork.ui.remote.dto.PromotionRequest;
import org.apache.deltaspike.core.api.scope.ConversationGroup;
import org.apache.deltaspike.core.api.scope.GroupedConversationScoped;
import org.apache.deltaspike.core.spi.scope.conversation.GroupedConversationManager;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@GroupedConversationScoped
@ConversationGroup(Pages.PromotionSelectionArea.class)
public class PromotionRequestSelectionViewCtrl implements Serializable {

    @Inject
    private GroupedConversationManager conversationManager;

    @Inject
    private IdeaPromotionService ideaPromotionService;

    private PromotionRequest selectedPromotionRequest;

    public Class<? extends Pages.PromotionSelectionArea> showPromotionRequest(PromotionRequest promotionRequest) {
        this.selectedPromotionRequest = promotionRequest;
        return Pages.PromotionSelectionArea.SelectPromotion.class;
    }

    public Class<? extends Pages> promote() {
        conversationManager.closeConversationGroup(Pages.PromotionSelectionArea.class);
        ideaPromotionService.promoteIdea(this.selectedPromotionRequest);
        return Pages.Index.class;
    }

    public PromotionRequest getPromotionRequest() {
        return selectedPromotionRequest;
    }
}
