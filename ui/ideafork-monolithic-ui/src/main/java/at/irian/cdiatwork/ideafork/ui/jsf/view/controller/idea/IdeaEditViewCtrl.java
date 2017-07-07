package at.irian.cdiatwork.ideafork.ui.jsf.view.controller.idea;

import at.irian.cdiatwork.ideafork.ui.jsf.view.config.Pages;
import at.irian.cdiatwork.ideafork.ui.jsf.view.controller.ViewController;
import at.irian.cdiatwork.ideafork.ui.remote.dto.Idea;
import at.irian.cdiatwork.ideafork.ui.remote.IdeaService;

import javax.inject.Inject;
import java.io.Serializable;

@ViewController
public class IdeaEditViewCtrl implements Serializable {
    @Inject
    private IdeaService ideaService;

    private Idea currentIdea;

    public Class<? extends Pages.Idea> editIdea(Idea currentIdea) {
        this.currentIdea = currentIdea;
        return Pages.Idea.Edit.class;
    }

    public Class<? extends Pages.Idea> save() {
        if (currentIdea.getId() == null) {
            ideaService.save(currentIdea);
        } else {
            ideaService.update(currentIdea);
        }
        return Pages.Idea.List.class;
    }

    public Idea getCurrentIdea() {
        return currentIdea;
    }
}
