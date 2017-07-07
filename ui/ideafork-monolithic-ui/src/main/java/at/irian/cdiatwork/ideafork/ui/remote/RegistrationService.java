package at.irian.cdiatwork.ideafork.ui.remote;

import at.irian.cdiatwork.ideafork.remote.api.IgnoreResultWithStatusCode;
import at.irian.cdiatwork.ideafork.remote.api.ResourceClient;
import at.irian.cdiatwork.ideafork.ui.remote.dto.User;

import javax.ws.rs.POST;

@ResourceClient(name = "registration", version = "v1")
public interface RegistrationService {
    @POST
    @IgnoreResultWithStatusCode(409)
    User registerUser(User newUser);
}
