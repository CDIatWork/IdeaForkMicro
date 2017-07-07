package at.irian.cdiatwork.ideafork.notification.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RecipientDetails {
    @XmlElement
    public String nickName;
}
