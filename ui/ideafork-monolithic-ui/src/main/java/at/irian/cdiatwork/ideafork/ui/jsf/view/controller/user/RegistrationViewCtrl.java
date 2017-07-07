package at.irian.cdiatwork.ideafork.ui.jsf.view.controller.user;

import at.irian.cdiatwork.ideafork.ui.jsf.message.UserMessage;
import at.irian.cdiatwork.ideafork.ui.jsf.view.JsfIdentityHolder;
import at.irian.cdiatwork.ideafork.ui.jsf.view.config.Pages;
import at.irian.cdiatwork.ideafork.ui.jsf.view.controller.ViewController;
import at.irian.cdiatwork.ideafork.ui.remote.dto.User;
import at.irian.cdiatwork.ideafork.ui.remote.RegistrationService;
import at.irian.cdiatwork.ideafork.ui.remote.UserActionService;
import at.irian.cdiatwork.ideafork.ui.remote.UserService;
import org.apache.deltaspike.core.api.config.view.ViewConfig;
import org.apache.deltaspike.jsf.api.message.JsfMessage;

import javax.inject.Inject;
import java.io.Serializable;

@ViewController
public class RegistrationViewCtrl implements Serializable {
    @Inject
    private RegistrationService registrationService;

    @Inject
    private UserActionService.LoginService loginService;

    @Inject
    private UserService userService;

    @Inject
    private JsfMessage<UserMessage> userMessage;

    @Inject
    private JsfIdentityHolder identityHolder;

    private User newUser = new User();

    public Class<? extends ViewConfig> register() {
        User registeredUser = registrationService.registerUser(this.newUser);

        if (registeredUser != null) {
            loginService.login(this.newUser);

            if (identityHolder.isAuthenticated()) {
                identityHolder.setAuthenticatedEMail(this.newUser.getEmail());
                registeredUser = userService.updateUserDetails(this.newUser);
            }
        }

        final Class<? extends ViewConfig> targetPage;
        if (registeredUser != null) {
            userMessage.addInfo().registrationSuccessful();
            targetPage = Pages.Idea.Index.class;
        } else {
            userMessage.addError().registrationFailed();
            targetPage = null;
        }

        return targetPage;
    }

    public User getNewUser() {
        return newUser;
    }
}
