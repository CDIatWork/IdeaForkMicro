package at.irian.cdiatwork.ideafork.user.internal.change;

import at.irian.cdiatwork.ideafork.jwt.api.IdentityHolder;
import at.irian.cdiatwork.ideafork.jwt.impl.AuthenticationManager;
import at.irian.cdiatwork.ideafork.user.domain.User;
import at.irian.cdiatwork.ideafork.user.domain.event.UserChangedEvent;
import at.irian.cdiatwork.ideafork.user.domain.event.UserRegistrationEvent;
import org.apache.deltaspike.core.util.ExceptionUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@ApplicationScoped
public class UserChangeBroadcaster {
    @Inject
    private AuthenticationManager authenticationManager;

    @Inject
    private Event<UserChangedEvent> userChangedEvent;

    @Inject
    private IdentityHolder identityHolder;

    public void onUserChange(User user) {
        try {
            if (user.getVersion() == 0) {
                //at this point the user is registered, but the login which creates the token is just the next step
                //-> to access the notification-service, we need to create a (tmp-)token
                String tmpToken = authenticationManager.createNewToken(user.getEmail());
                userChangedEvent.fireAsync(new UserRegistrationEvent(user, tmpToken));
            } else {
                userChangedEvent.fireAsync(new UserChangedEvent(user, identityHolder.getCurrentToken()));
            }
        } catch (Exception e) {
            throw ExceptionUtils.throwAsRuntimeException(e);
        }
    }
}
