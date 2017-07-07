package at.irian.cdiatwork.ideafork.ui.jsf.view.security;

import at.irian.cdiatwork.ideafork.ui.jsf.message.UserMessage;
import at.irian.cdiatwork.ideafork.ui.jsf.view.JsfIdentityHolder;
import org.apache.deltaspike.security.api.authorization.AbstractAccessDecisionVoter;
import org.apache.deltaspike.security.api.authorization.AccessDecisionVoterContext;
import org.apache.deltaspike.security.api.authorization.SecurityViolation;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.Set;

@RequestScoped
public class UserAwareAccessDecisionVoter extends AbstractAccessDecisionVoter {
    @Inject
    private JsfIdentityHolder identityHolder;

    @Inject
    private UserMessage userMessage;

    @Override
    protected void checkPermission(AccessDecisionVoterContext accessDecisionVoterContext,
                                   Set<SecurityViolation> securityViolations) {
        if (!identityHolder.isAuthenticated()) {
            securityViolations.add(newSecurityViolation(userMessage.pleaseLogin()));
        }
    }
}
