package at.irian.cdiatwork.ideafork.idea.rest.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IdeaUpdateRequest {
    @XmlElement
    public String id;

    @XmlElement
    public String topic;

    @XmlElement
    public String category;

    @XmlElement
    public String description;
}
