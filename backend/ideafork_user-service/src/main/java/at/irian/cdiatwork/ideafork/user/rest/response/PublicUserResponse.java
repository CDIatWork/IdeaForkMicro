package at.irian.cdiatwork.ideafork.user.rest.response;

import at.irian.cdiatwork.ideafork.user.domain.User;

public class PublicUserResponse {
    private String nickName;

    private String email;

    private String firstName;

    private String lastName;

    public PublicUserResponse(User user, boolean includeEMail) {
        nickName = user.getNickName();
        firstName = user.getFirstName();
        lastName = user.getLastName();

        if (includeEMail) {
            email = user.getEmail();
        }
    }

    /*
     * generated
     */

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
}
