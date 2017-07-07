package at.irian.cdiatwork.ideafork.user.internal.change.observer;

import at.irian.cdiatwork.ideafork.jwt.api.IdentityHolder;
import at.irian.cdiatwork.ideafork.user.domain.event.UserRegistrationEvent;
import at.irian.cdiatwork.ideafork.user.remote.NotificationResource;
import at.irian.cdiatwork.ideafork.user.rest.request.UserRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class RegistrationNotificationObserver {
    @Inject
    private NotificationResource notificationResource;

    @Inject
    private IdentityHolder identityHolder;

    public void onUserRegisteredEvent(@ObservesAsync UserRegistrationEvent userRegistrationEvent) {
        this.identityHolder.setCurrentToken(userRegistrationEvent.getToken()); //we need to set the token, because we are in an async-thread here
        String userSpecificText = Optional.ofNullable(userRegistrationEvent.getUser().getNickName()).orElse(userRegistrationEvent.getUser().getEmail());
        UserRequest userRequest = new UserRequest();
        userRequest.setNickName(userSpecificText);
        this.notificationResource.sendWelcomeMessage(userRequest);
    }
}
