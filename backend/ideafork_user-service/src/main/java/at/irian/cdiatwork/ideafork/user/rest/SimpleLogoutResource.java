package at.irian.cdiatwork.ideafork.user.rest;

import at.irian.cdiatwork.ideafork.jwt.api.AuthenticationRequired;
import at.irian.cdiatwork.ideafork.jwt.api.IdentityHolder;
import at.irian.cdiatwork.ideafork.user.domain.User;
import at.irian.cdiatwork.ideafork.user.domain.UserAction;
import at.irian.cdiatwork.ideafork.user.domain.event.UserActionEvent;
import at.irian.cdiatwork.ideafork.user.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@AuthenticationRequired
@Path("user-action")
@ApplicationScoped
public class SimpleLogoutResource {
    @Inject
    private UserRepository userRepository;

    @Inject
    private IdentityHolder identityHolder;

    @Inject
    private Event<UserActionEvent> userActionEvent;

    @POST
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    public void logout(@QueryParam("type") String logoutType,
                       @Context UriInfo uriInfo) {

        User loadedUser = userRepository.loadByEmail(identityHolder.getAuthenticatedEMail());

        if (loadedUser != null) {
            UserAction.Type userActionType = UserAction.Type.LOGOUT;
            if (logoutType != null) {
                userActionType = UserAction.Type.valueOf(logoutType);
            }
            userActionEvent.fireAsync(new UserActionEvent(new UserAction(userActionType, loadedUser)));
        }
    }
}
