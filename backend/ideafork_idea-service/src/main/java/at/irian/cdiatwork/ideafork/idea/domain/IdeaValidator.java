package at.irian.cdiatwork.ideafork.idea.domain;

import javax.enterprise.context.ApplicationScoped;

//hint for later: will be replaced with a bean-validation constraint
@ApplicationScoped
public class IdeaValidator {
    public boolean checkIdea(Idea idea) {
        return idea.getCategory() != null && idea.getTopic() != null && idea.getAuthorEmail() != null;
    }
}
