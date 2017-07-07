package at.irian.cdiatwork.ideafork.ui.remote.dto;

import javax.xml.bind.annotation.XmlElementRef;
import java.util.Date;

public class PromotionRequest {
    private String id;
    private String ideaId;
    private String description;
    private Long createdAt;
    private Long promotedAt;

    @XmlElementRef
    private Idea idea;

    //needed for
    protected PromotionRequest() {
    }

    public PromotionRequest(String ideaId) {
        this.ideaId = ideaId;
    }

    public Date getCreatedAt() {
        return createdAt != null ? new Date(createdAt) : null;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt != null ? createdAt.getTime() : null;
    }

    public Date getPromotedAt() {
        return promotedAt != null ? new Date(promotedAt) : null;
    }

    public void setPromotedAt(Date promotedAt) {
        this.promotedAt = promotedAt != null ? promotedAt.getTime() : null;
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

    public String getIdeaId() {
        return ideaId;
    }

    public void setIdeaId(String ideaId) {
        this.ideaId = ideaId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Idea getIdea() {
        return idea;
    }

    public void setIdea(Idea idea) {
        this.idea = idea;
    }
}
