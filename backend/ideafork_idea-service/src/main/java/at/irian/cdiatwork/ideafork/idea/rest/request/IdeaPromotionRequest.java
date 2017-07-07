package at.irian.cdiatwork.ideafork.idea.rest.request;

import at.irian.cdiatwork.ideafork.idea.domain.PromotionRequest;
import at.irian.cdiatwork.ideafork.idea.rest.response.IdeaResponse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IdeaPromotionRequest {
    @XmlElement
    public String id;

    @XmlElement
    public String ideaId;

    @XmlElement
    public String description;

    @XmlElement
    public Long createdAt;

    @XmlElement
    public Long promotedAt;

    @XmlElementRef
    public IdeaResponse idea;

    public static IdeaPromotionRequest convert(PromotionRequest promotionRequest) {
        IdeaPromotionRequest result = new IdeaPromotionRequest();
        result.id = promotionRequest.getId();
        result.ideaId = promotionRequest.getIdeaForPromotion().getId();
        result.description = promotionRequest.getDescription();
        if (promotionRequest.getCreatedAt() != null) {
            result.createdAt = promotionRequest.getCreatedAt().getTime();
        }
        if (promotionRequest.getPromotedAt() != null) {
            result.promotedAt = promotionRequest.getPromotedAt().getTime();
        }
        result.idea = new IdeaResponse(promotionRequest.getIdeaForPromotion());
        return result;
    }
}
