package at.irian.cdiatwork.ideafork.user.internal.change.observer;

import at.irian.cdiatwork.ideafork.user.domain.event.UserActionEvent;
import at.irian.cdiatwork.ideafork.user.repository.UserActionRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

@ApplicationScoped
public class UserActivityObserver {
    @Inject
    private UserActionRepository userActionRepository;

    public void onUserActionEvent(@ObservesAsync UserActionEvent userActionEvent) {
        userActionRepository.save(userActionEvent.getUserAction());
    }
}
