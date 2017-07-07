package at.irian.cdiatwork.ideafork.idea.event;

import at.irian.cdiatwork.ideafork.idea.domain.Idea;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@RequestScoped
public class IdeaChangedBroadcaster {
    @Inject
    private Event<IdeaChangedEvent> ideaChangedEvent;

    public void onIdeaChange(Idea entity) {
        IdeaChangedEvent userChangedEvent = new IdeaChangedEvent(entity);
        this.ideaChangedEvent.fireAsync(userChangedEvent);
    }
}
