package at.irian.cdiatwork.ideafork.ui.remote.dto;

public class Idea {
    private String id;
    private String topic;
    private String category;
    private String description;
    private String baseIdeaId;

    protected Idea() {
    }

    public Idea(String id, String baseIdeaId, String topic, String category, String description) {
        this.id = id;
        this.baseIdeaId = baseIdeaId;
        this.topic = topic;
        this.category = category;
        this.description = description;
    }

    /*
     * generated
     */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getBaseIdeaId() {
        return baseIdeaId;
    }

    public void setBaseIdeaId(String baseIdeaId) {
        this.baseIdeaId = baseIdeaId;
    }
}
