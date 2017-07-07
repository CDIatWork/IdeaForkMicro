package at.irian.cdiatwork.ideafork.idea.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Idea extends BaseEntity {
    private static final long serialVersionUID = -3824813959555007833L;

    @Column(nullable = false)
    private String topic;

    @Column(nullable = false)
    private String category; //specified by the user

    @Column
    private String description;

    @Column(nullable = false)
    private String authorEmail;

    @Column
    private String baseIdeaId;

    @Column
    private Long baseIdeaVersion;

    protected Idea() {
        //needed for data-import
    }

    public Idea(String baseIdeaId, String topic, String category, String authorEmail) {
        this.baseIdeaId = baseIdeaId;
        this.topic = topic;
        this.category = category;
        this.authorEmail = authorEmail;
    }

    public Idea(Idea baseIdea, String authorEmail) {
        this(baseIdea.getBaseIdeaId(), baseIdea.getTopic(), baseIdea.getCategory(), authorEmail);
        this.description = baseIdea.getDescription();
        this.baseIdeaId = baseIdea.id;
        this.baseIdeaVersion = baseIdea.version;
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

    public String getBaseIdeaId() {
        return baseIdeaId;
    }

    public Long getBaseIdeaVersion() {
        return baseIdeaVersion;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }
}
