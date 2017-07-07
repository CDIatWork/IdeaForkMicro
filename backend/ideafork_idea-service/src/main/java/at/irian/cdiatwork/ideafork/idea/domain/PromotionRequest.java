package at.irian.cdiatwork.ideafork.idea.domain;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.TemporalType.DATE;

@Entity
public class PromotionRequest extends BaseEntity {
    private static final long serialVersionUID = -2824813959555007833L;

    @ManyToOne
    private Idea ideaForPromotion;

    @Column(nullable = false)
    private String description;

    @Column
    @Temporal(DATE)
    private Date createdAt;

    @Column
    @Temporal(DATE)
    private Date promotedAt;

    public PromotionRequest() {
    }

    public PromotionRequest(Idea ideaForPromotion) {
        this.ideaForPromotion = ideaForPromotion;
    }

    @PrePersist
    protected void onSave() {
        this.createdAt = new Date();
    }

    public Idea getIdeaForPromotion() {
        return ideaForPromotion;
    }

    public void setIdeaForPromotion(Idea ideaForPromotion) {
        this.ideaForPromotion = ideaForPromotion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getPromotedAt() {
        return promotedAt;
    }

    public void setPromotedAt(Date promotedAt) {
        this.promotedAt = promotedAt;
    }
}
