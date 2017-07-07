package at.irian.cdiatwork.ideafork.user.domain.event;

import at.irian.cdiatwork.ideafork.user.domain.User;

public class UserRegistrationEvent extends UserChangedEvent {
    public UserRegistrationEvent(User user, String token) {
        super(user, token);
    }
}
