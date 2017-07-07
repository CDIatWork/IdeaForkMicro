package at.irian.cdiatwork.ideafork.ui.jsf.view.controller.promotion;

import at.irian.cdiatwork.ideafork.ui.jsf.view.config.Pages;
import at.irian.cdiatwork.ideafork.ui.remote.dto.Idea;
import at.irian.cdiatwork.ideafork.ui.remote.dto.PromotionRequest;
import at.irian.cdiatwork.ideafork.ui.jsf.view.model.SelectableEntry;
import at.irian.cdiatwork.ideafork.ui.remote.IdeaPromotionService;
import at.irian.cdiatwork.ideafork.ui.remote.IdeaService;
import org.apache.deltaspike.core.api.config.view.controller.PreRenderView;
import org.apache.deltaspike.core.api.scope.GroupedConversation;
import org.apache.deltaspike.core.api.scope.GroupedConversationScoped;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Named
@GroupedConversationScoped
public class PromotionWizardCtrl implements Serializable {
    @Inject
    private IdeaService ideaService;

    @Inject
    private IdeaPromotionService ideaPromotionService;

    @Inject
    private GroupedConversation conversation;

    private List<SelectableEntry<Idea>> selectableIdeaList;
    private String rowClasses;

    private PromotionRequest promotionRequest;
    private Idea selectedIdeaToPromote;

    @PreRenderView
    public void onPreRenderView() {
        if (selectableIdeaList != null) {
            return;
        }

        selectableIdeaList = Optional.ofNullable(ideaService.loadAll()).orElse(emptyList())
                .stream().map(SelectableEntry::new).collect(toList());
    }

    public void select(SelectableEntry<Idea> selectedIdea) {
        StringBuilder rowClassBuilder = new StringBuilder();
        for (SelectableEntry selectableIdea : selectableIdeaList) {
            if (selectableIdea == selectedIdea) { //check for the same instance
                selectedIdea.setSelected(true);
                promotionRequest = new PromotionRequest(selectedIdea.getEntry().getId());
                selectedIdeaToPromote = selectedIdea.getEntry();
                rowClassBuilder.append("selectedPromotionTableRow, ");
            } else {
                selectableIdea.setSelected(false);
                rowClassBuilder.append("'', ");
            }
        }
        rowClasses = rowClassBuilder.toString();
        rowClasses = rowClasses.substring(0, rowClasses.lastIndexOf(","));
    }

    public Class<? extends Pages.PromotionWizard> toStep2() {
        return Pages.PromotionWizard.Step2.class;
    }

    public Class<? extends Pages.PromotionWizard> showConfirmation() {
        return Pages.PromotionWizard.FinalStep.class;
    }

    public Class<? extends Pages> savePromotionRequest() {
        this.ideaPromotionService.requestIdeaPromotion(this.promotionRequest);
        this.conversation.close();
        return Pages.Index.class;
    }

    /*
     * generated
     */

    public List<SelectableEntry<Idea>> getSelectableIdeaList() {
        return selectableIdeaList;
    }

    public String getRowClasses() {
        return rowClasses;
    }

    public PromotionRequest getPromotionRequest() {
        return promotionRequest;
    }

    public Idea getSelectedIdeaToPromote() {
        return selectedIdeaToPromote;
    }
}
