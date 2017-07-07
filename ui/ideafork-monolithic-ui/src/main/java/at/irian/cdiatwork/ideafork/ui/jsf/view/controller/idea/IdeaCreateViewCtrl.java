package at.irian.cdiatwork.ideafork.ui.jsf.view.controller.idea;

import at.irian.cdiatwork.ideafork.ui.jsf.view.config.Pages;
import at.irian.cdiatwork.ideafork.ui.jsf.view.controller.ViewController;
import at.irian.cdiatwork.ideafork.ui.remote.dto.Idea;
import at.irian.cdiatwork.ideafork.ui.remote.IdeaService;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@ViewController
public class IdeaCreateViewCtrl implements Serializable {

    @Inject
    private IdeaService ideaService;

    @NotNull
    @Size(min = 1, max = 64)
    private String topic;

    @NotNull
    @Size(min = 1, max = 64)
    private String category;

    private String description;

    public Class<? extends Pages.Idea> save() {
        Idea ideaToSave = createIdea();
        ideaService.save(ideaToSave);
        return Pages.Idea.Overview.class;
    }

    private Idea createIdea() {
        Idea idea = new Idea(null, null, topic, category, description);
        return idea;
    }

    public Class<? extends Pages.Idea> createWith(String category) {
        this.category = category;
        return Pages.Idea.Create.class;
    }

    /*
     * generated
     */

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
