package at.irian.cdiatwork.ideafork.ui.jsf.view;

import at.irian.cdiatwork.ideafork.jwt.api.IdentityHolder;
import at.irian.cdiatwork.ideafork.ui.remote.UserActionService;

import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named("identityHolder")
@Specializes
@SessionScoped
public class JsfIdentityHolder extends IdentityHolder implements Serializable {
    private boolean logoutSent = false;

    public boolean isAuthenticated() {
        return getCurrentToken() != null;
    }

    @Inject
    private UserActionService.LogoutService logoutService;

    @PreDestroy
    protected void onTimeout() {
        onLogout(false);
    }

    @Override
    public void setCurrentToken(String currentToken) {
        super.setCurrentToken(currentToken);
        this.logoutSent = false;
    }

    public void onLogout(boolean manualLogout) {
        try {
            if (logoutSent) {
                return;
            }

            if (manualLogout) {
                logoutService.logout("LOGOUT");
            } else {
                logoutService.logout("AUTO_LOGOUT");
            }
        } finally {
            logoutSent = true;
            reset();
        }
    }
}
