package at.irian.cdiatwork.ideafork.user.rest;

import at.irian.cdiatwork.ideafork.user.domain.User;
import at.irian.cdiatwork.ideafork.user.internal.change.UserChangeBroadcaster;
import at.irian.cdiatwork.ideafork.user.repository.UserRepository;
import at.irian.cdiatwork.ideafork.user.rest.request.RegistrationRequest;
import at.irian.cdiatwork.ideafork.user.rest.response.PublicUserResponse;
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

@Path("registration")
@ApplicationScoped
public class SimpleRegistrationResource {
    @Inject
    private UserRepository userRepository;

    @Inject
    private PasswordManager passwordManager;

    @Inject
    private UserChangeBroadcaster userChangeBroadcaster;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(RegistrationRequest registrationRequest,
                             @Context UriInfo uriInfo) {

        if (userRepository.loadByEmail(registrationRequest.getEmail()) == null) {
            String passwordHash = passwordManager.createPasswordHash(registrationRequest.getPassword());
            User userToRegister = new User(registrationRequest.getEmail(), passwordHash);
            userToRegister.setNickName(registrationRequest.getNickName());
            User savedUser = userRepository.save(userToRegister);
            User registeredUser = userRepository.findBy(savedUser.getId());

            if (registeredUser != null) {
                userChangeBroadcaster.onUserChange(registeredUser);

                return Response.created(uriInfo.getBaseUriBuilder().path(SimpleLoginResource.class)
                        .build()).entity(new PublicUserResponse(savedUser, true))
                        .type(MediaType.APPLICATION_JSON_TYPE).build();
            }
        }

        return Response.status(Response.Status.CONFLICT).build();
    }
}
