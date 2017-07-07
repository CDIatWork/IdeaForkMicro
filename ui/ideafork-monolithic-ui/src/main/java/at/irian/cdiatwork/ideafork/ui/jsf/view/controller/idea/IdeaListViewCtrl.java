package at.irian.cdiatwork.ideafork.ui.jsf.view.controller.idea;

import at.irian.cdiatwork.ideafork.ui.jsf.view.controller.ViewController;
import at.irian.cdiatwork.ideafork.ui.remote.IdeaService;
import at.irian.cdiatwork.ideafork.ui.remote.dto.Idea;
import org.apache.deltaspike.core.api.config.view.controller.PreRenderView;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@ViewController
public class IdeaListViewCtrl implements Serializable {
    @Inject
    private IdeaService ideaService;

    private List<Idea> ideaList;

    @PreRenderView
    public void onPreRenderView() {
        ideaList = Optional.ofNullable(ideaService.loadAll()).orElse(emptyList());
    }

    public void deleteIdea(Idea currentIdea) {
        ideaService.remove(currentIdea.getId());
    }

    public List<Idea> getIdeaList() {
        return ideaList;
    }
}
