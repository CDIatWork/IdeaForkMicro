package at.irian.cdiatwork.ideafork.history.rest.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//different approach (public-fields vs. properties) compared to e.g. user-service
@XmlRootElement
public class EntityChangeRequest {
    @XmlElement
    public String entityAsJson;

    @XmlElement
    public String id;

    @XmlElement
    public long version = 0;

    @XmlElement
    public long creationTimestamp;
}
