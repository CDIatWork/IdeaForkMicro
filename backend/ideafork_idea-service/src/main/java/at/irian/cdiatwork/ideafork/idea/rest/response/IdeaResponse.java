package at.irian.cdiatwork.ideafork.idea.rest.response;

import at.irian.cdiatwork.ideafork.idea.domain.Idea;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
public class IdeaResponse {
    @XmlElement
    public String id;

    @XmlElement
    public String baseIdeaId;

    @XmlElement
    public String topic;

    @XmlElement
    public String category;

    @XmlElement
    public String description;

    protected IdeaResponse() {
    }

    public IdeaResponse(Idea idea) {
        this.id = idea.getId();
        this.baseIdeaId = idea.getBaseIdeaId();
        this.topic = idea.getTopic();
        this.category = idea.getCategory();
        this.description = idea.getDescription();
    }

    public IdeaResponse fork() {
        this.id = null;
        return this;
    }
}
