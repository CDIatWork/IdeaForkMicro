package at.irian.cdiatwork.ideafork.ui.jsf.view.controller.idea;

import at.irian.cdiatwork.ideafork.ui.jsf.view.config.Pages;
import at.irian.cdiatwork.ideafork.ui.jsf.view.controller.ViewController;
import at.irian.cdiatwork.ideafork.ui.remote.dto.Idea;
import at.irian.cdiatwork.ideafork.ui.remote.ForkService;

import javax.inject.Inject;
import java.io.Serializable;

@ViewController
public class IdeaForkViewCtrl implements Serializable {
    @Inject
    private IdeaEditViewCtrl ideaEditViewCtrl;

    @Inject
    private ForkService forkService;

    public Class<? extends Pages.Idea> forkIdea(Idea currentIdea) {
        Idea forkedIdea = forkService.forkIdea(currentIdea.getId());
        ideaEditViewCtrl.editIdea(forkedIdea);
        return Pages.Idea.Edit.class;
    }
}
