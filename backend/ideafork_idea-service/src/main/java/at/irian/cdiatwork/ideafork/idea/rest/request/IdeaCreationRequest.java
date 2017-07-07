package at.irian.cdiatwork.ideafork.idea.rest.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IdeaCreationRequest {
    @XmlElement
    public String baseIdeaId;

    @XmlElement
    public String topic;

    @XmlElement
    public String category;

    @XmlElement
    public String description;
}
