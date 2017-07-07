package at.irian.cdiatwork.ideafork.ui.remote.dto;

import java.util.List;

public class ProfileActivity {
    private String nickName;
    private String firstName;
    private String lastName;
    private String email;
    private List<UserAction> userActionEntryList;

    /*
     * generated
     */

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<UserAction> getUserActionEntryList() {
        return userActionEntryList;
    }

    public void setUserActionEntryList(List<UserAction> userActionEntryList) {
        this.userActionEntryList = userActionEntryList;
    }
}
