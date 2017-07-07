package at.irian.cdiatwork.ideafork.user.domain.event;

import at.irian.cdiatwork.ideafork.user.domain.User;

public class UserChangedEvent {
    private final User user;
    private final String token;

    public UserChangedEvent(User user, String currentToken) {
        this.user = user;
        this.token = currentToken;
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}
