package at.irian.cdiatwork.ideafork.ui.remote.dto;

public class IdeaExport {
    private String topic;

    private String category;

    private String description;

    public IdeaExport(Idea idea) {
        this.topic = idea.getTopic();
        this.category = idea.getCategory();
        this.description = idea.getDescription();
    }

    /*
     * generated
     */

    public String getTopic() {
        return topic;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }
}
