package at.irian.cdiatwork.ideafork.user.rest;

import at.irian.cdiatwork.ideafork.jwt.api.AuthenticationRequired;
import at.irian.cdiatwork.ideafork.jwt.api.IdentityHolder;
import at.irian.cdiatwork.ideafork.user.domain.User;
import at.irian.cdiatwork.ideafork.user.domain.UserAction;
import at.irian.cdiatwork.ideafork.user.repository.UserActionRepository;
import at.irian.cdiatwork.ideafork.user.repository.UserRepository;
import at.irian.cdiatwork.ideafork.user.rest.response.UserActionEntry;
import at.irian.cdiatwork.ideafork.user.rest.response.UserActionResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@AuthenticationRequired
@Path("user-action")

@ApplicationScoped
public class UserActionResource {
    @Inject
    private UserRepository userRepository;

    @Inject
    private UserActionRepository userActionRepository;

    @Inject
    private IdentityHolder identityHolder;

    @GET
    public UserActionResponse loadCurrentUserDetails() {
        User user = userRepository.loadByEmail(identityHolder.getAuthenticatedEMail());

        List<UserAction> result = Optional.ofNullable(userActionRepository.loadLatestActivities(user, 10)).orElse(emptyList());
        List<UserActionEntry> userActionEntryList = result.stream().map(UserActionEntry::new).collect(toList());
        return new UserActionResponse(user, userActionEntryList);
    }
}
