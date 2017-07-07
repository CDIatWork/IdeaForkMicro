package at.irian.cdiatwork.ideafork.user.domain.event;

import at.irian.cdiatwork.ideafork.user.domain.UserAction;

public class UserActionEvent {
    private final UserAction userAction;

    public UserActionEvent(UserAction userAction) {
        this.userAction = userAction;
    }

    public UserAction getUserAction() {
        return userAction;
    }
}
