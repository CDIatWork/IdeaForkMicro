package at.irian.cdiatwork.ideafork.user.rest;

import at.irian.cdiatwork.ideafork.jwt.api.IdentityHolder;
import at.irian.cdiatwork.ideafork.jwt.api.LoginEntryPoint;
import at.irian.cdiatwork.ideafork.user.domain.User;
import at.irian.cdiatwork.ideafork.user.repository.UserRepository;
import at.irian.cdiatwork.ideafork.user.rest.request.LoginRequest;
import at.irian.cdiatwork.ideafork.user.security.PasswordManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("user-action")
@ApplicationScoped
public class SimpleLoginResource {
    @Inject
    private UserRepository userRepository;

    @Inject
    private PasswordManager passwordManager;

    @Inject
    private IdentityHolder identityHolder;

    @LoginEntryPoint
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest loginRequest,
                          @Context UriInfo uriInfo) {

        if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).build();
        }

        User loadedUser = userRepository.loadByEmail(loginRequest.getEmail());

        if (loadedUser == null) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).build();
        }

        String passwordHash = passwordManager.createPasswordHash(loginRequest.getPassword());

        if (passwordHash.equals(loadedUser.getPassword())) {
            identityHolder.setAuthenticatedEMail(loginRequest.getEmail());

            return Response.ok().build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
