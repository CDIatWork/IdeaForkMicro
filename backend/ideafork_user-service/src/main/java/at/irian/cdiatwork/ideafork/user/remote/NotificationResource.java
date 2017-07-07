package at.irian.cdiatwork.ideafork.user.remote;

import at.irian.cdiatwork.ideafork.remote.api.ResourceClient;
import at.irian.cdiatwork.ideafork.user.rest.request.UserRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@ApplicationScoped
@ResourceClient(name = "notifications", version = "v1")
public interface NotificationResource {
    @POST
    @Path("/welcome")
    void sendWelcomeMessage(UserRequest notificationRequest);
}
