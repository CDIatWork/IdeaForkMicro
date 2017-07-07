package at.irian.cdiatwork.ideafork.ui.remote;

import at.irian.cdiatwork.ideafork.remote.api.ResourceClient;
import at.irian.cdiatwork.ideafork.ui.remote.dto.User;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

@ResourceClient(name = "user", version = "v1")
public interface UserService {
    @POST
    User updateUserDetails(User newUser);

    @GET
    User loadDetailsOfCurrentUser();
}
