package at.irian.cdiatwork.ideafork.user.rest.response;

import at.irian.cdiatwork.ideafork.user.domain.UserAction;

public class UserActionEntry {
    private final String userAction;

    private final String createdAt;

    public UserActionEntry(UserAction userAction) {
        this.userAction = userAction.getUserAction().name();
        this.createdAt = userAction.getCreatedAt().toString();
    }

    public String getUserAction() {
        return userAction;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
