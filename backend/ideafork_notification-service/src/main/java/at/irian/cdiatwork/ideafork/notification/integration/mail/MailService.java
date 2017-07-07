package at.irian.cdiatwork.ideafork.notification.integration.mail;

import at.irian.cdiatwork.ideafork.jwt.api.IdentityHolder;
import at.irian.cdiatwork.ideafork.notification.integration.mail.spring.SpringMailSender;
import org.apache.deltaspike.core.api.config.ConfigResolver;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class MailService {
    @Inject
    private IdentityHolder identityHolder;

    @Inject
    private SpringMailSender mailSender;

    public void sendWelcomeMessage(String nickName) {
        String senderAddress = ConfigResolver.getProjectStageAwarePropertyValue("ideafork.sender", "admin@ideafork.com");
        String subject = "Welcome " + nickName;
        String text = "Welcome @ IdeaFork!";
        this.mailSender.send(senderAddress, identityHolder.getAuthenticatedEMail(), subject, text);
    }
}
