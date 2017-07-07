package at.irian.cdiatwork.ideafork.ui.remote;

import at.irian.cdiatwork.ideafork.remote.api.ResourceClient;
import at.irian.cdiatwork.ideafork.ui.remote.dto.User;
import at.irian.cdiatwork.ideafork.ui.remote.dto.ProfileActivity;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

public interface UserActionService {
    @ResourceClient(name = "user-action", version = "v1")
    interface LoginService {
        @POST
        @Path("/login")
        void login(User user);
    }

    @ResourceClient(name = "user-action", version = "v1")
    interface LogoutService {
        @POST
        @Path("/logout")
        void logout(@QueryParam("type") String logoutType);
    }

    @ResourceClient(name = "user-action", version = "v1")
    interface UserStatsService {
        @GET
        ProfileActivity loadLatestActivities();
    }
}
