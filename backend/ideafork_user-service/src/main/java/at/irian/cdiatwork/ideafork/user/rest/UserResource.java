package at.irian.cdiatwork.ideafork.user.rest;

import at.irian.cdiatwork.ideafork.jwt.api.AuthenticationRequired;
import at.irian.cdiatwork.ideafork.jwt.api.IdentityHolder;
import at.irian.cdiatwork.ideafork.user.domain.User;
import at.irian.cdiatwork.ideafork.user.repository.UserRepository;
import at.irian.cdiatwork.ideafork.user.rest.request.UserRequest;
import at.irian.cdiatwork.ideafork.user.rest.response.PublicUserResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@AuthenticationRequired
@Path("user")

@ApplicationScoped
public class UserResource {
    @Inject
    private UserRepository userRepository;

    @Inject
    private IdentityHolder identityHolder;

    @GET
    public PublicUserResponse loadCurrentUserDetails() {
        User user = userRepository.loadByEmail(identityHolder.getAuthenticatedEMail());
        return new PublicUserResponse(user, true);
    }

    @GET
    @Path("{nick}")
    public PublicUserResponse loadUserPerNick(@PathParam("nick") String nick) {
        User user = userRepository.loadByNickName(nick);
        if (user != null) {
            if (user.getEmail().equals(identityHolder.getAuthenticatedEMail())) {
                return new PublicUserResponse(user, true);
            }
            return new PublicUserResponse(user, false);
        }
        return null;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAllUserDetails(UserRequest userRequest) {
        String email = identityHolder.getAuthenticatedEMail();

        User savedUser = userRepository.loadByEmail(email);

        if (savedUser == null) {
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).build();
        }

        savedUser.setNickName(userRequest.getNickName());
        savedUser.setFirstName(userRequest.getFirstName());
        savedUser.setLastName(userRequest.getLastName());

        savedUser = userRepository.save(savedUser);

        return Response.ok().entity(new PublicUserResponse(savedUser, true)).build();
    }
}
