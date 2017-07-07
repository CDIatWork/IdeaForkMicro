package at.irian.cdiatwork.ideafork.user.rest.response;

import at.irian.cdiatwork.ideafork.user.domain.User;

import java.util.List;

public class UserActionResponse {
    private String nickName;

    private String email;

    private String firstName;

    private String lastName;

    private final List<UserActionEntry> userActionEntryList;

    public UserActionResponse(User user, List<UserActionEntry> userActionEntryList) {
        this.nickName = user.getNickName();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.userActionEntryList = userActionEntryList;
    }

    public String getNickName() {
        return nickName;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<UserActionEntry> getUserActionEntryList() {
        return userActionEntryList;
    }
}
