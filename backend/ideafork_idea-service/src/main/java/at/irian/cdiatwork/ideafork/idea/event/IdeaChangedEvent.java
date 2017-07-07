package at.irian.cdiatwork.ideafork.idea.event;

import at.irian.cdiatwork.ideafork.idea.domain.Idea;

public class IdeaChangedEvent  {
    private Idea idea;

    public IdeaChangedEvent(Idea idea) {
        this.idea = idea;
    }

    public Idea getIdea() {
        return idea;
    }
}
