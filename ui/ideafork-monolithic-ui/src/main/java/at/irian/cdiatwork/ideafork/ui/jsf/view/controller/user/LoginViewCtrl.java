package at.irian.cdiatwork.ideafork.ui.jsf.view.controller.user;

import at.irian.cdiatwork.ideafork.ui.jsf.message.UserMessage;
import at.irian.cdiatwork.ideafork.ui.jsf.view.JsfIdentityHolder;
import at.irian.cdiatwork.ideafork.ui.jsf.view.config.Pages;
import at.irian.cdiatwork.ideafork.ui.jsf.view.controller.ViewController;
import at.irian.cdiatwork.ideafork.ui.remote.dto.User;
import at.irian.cdiatwork.ideafork.ui.remote.UserActionService;
import at.irian.cdiatwork.ideafork.ui.remote.UserService;
import org.apache.deltaspike.jsf.api.message.JsfMessage;

import javax.inject.Inject;
import java.io.Serializable;

@ViewController
public class LoginViewCtrl implements Serializable {
    @Inject
    private UserActionService.LoginService loginService;

    @Inject
    private UserService userService;

    @Inject
    private JsfIdentityHolder identityHolder;

    @Inject
    private JsfMessage<UserMessage> userMessage;

    private String email;
    private String password;

    public Class<? extends Pages.Idea> login() {
        loginService.login(new User(email, password));

        final Class<? extends Pages.Idea> navigationTarget;
        if (identityHolder.isAuthenticated()) {
            identityHolder.setAuthenticatedEMail(email);
            User user = userService.loadDetailsOfCurrentUser();
            userMessage.addInfo().welcomeNewUser(user.getNickName());
            navigationTarget = Pages.Idea.Overview.class;
        } else {
            userMessage.addError().loginFailed();
            navigationTarget = null;
        }

        return navigationTarget;
    }

    /*
     * generated
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
