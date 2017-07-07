package at.irian.cdiatwork.ideafork.notification.rest;

import at.irian.cdiatwork.ideafork.jwt.api.AuthenticationRequired;
import at.irian.cdiatwork.ideafork.notification.domain.RecipientDetails;
import at.irian.cdiatwork.ideafork.notification.integration.mail.MailService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@AuthenticationRequired
@Path("notifications")

@ApplicationScoped
public class NotificationResource {
    @Inject
    private MailService mailService;

    @POST
    @Path("/welcome")
    public void onNotification(RecipientDetails recipientDetails) {
        mailService.sendWelcomeMessage(recipientDetails.nickName);
    }
}
