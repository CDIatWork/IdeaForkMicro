package at.irian.cdiatwork.ideafork.ui.jsf.message;

import org.apache.deltaspike.core.api.message.MessageBundle;
import org.apache.deltaspike.core.api.message.MessageTemplate;

import javax.inject.Named;

@Named
@MessageBundle
public interface UserMessage {
    @MessageTemplate("Welcome %s!")
    String welcomeNewUser(String nickName);

    @MessageTemplate("Login failed!")
    String loginFailed();

    @MessageTemplate("Please login")
    String pleaseLogin();

    @MessageTemplate("Registration successful!")
    String registrationSuccessful();

    @MessageTemplate("Registration failed!")
    String registrationFailed();

    @MessageTemplate("Warning!")
    String warning();

    @MessageTemplate("Unexpected service failure")
    String unhandledFailure();
}
