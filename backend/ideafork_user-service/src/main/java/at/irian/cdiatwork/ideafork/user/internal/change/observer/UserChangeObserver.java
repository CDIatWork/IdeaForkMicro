package at.irian.cdiatwork.ideafork.user.internal.change.observer;

import at.irian.cdiatwork.ideafork.jwt.api.IdentityHolder;
import at.irian.cdiatwork.ideafork.user.domain.User;
import at.irian.cdiatwork.ideafork.user.domain.event.UserChangedEvent;
import at.irian.cdiatwork.ideafork.user.remote.ArchiveResource;
import at.irian.cdiatwork.ideafork.user.remote.request.EntityChangeRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.deltaspike.core.util.ExceptionUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;

@ApplicationScoped
public class UserChangeObserver {
    @Inject
    private IdentityHolder identityHolder;

    @Inject
    private ArchiveResource archiveResource;

    public void onUserChange(@ObservesAsync UserChangedEvent userChangedEvent) {
        this.identityHolder.setCurrentToken(userChangedEvent.getToken());

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            User user = userChangedEvent.getUser();
            EntityChangeRequest entityChangeRequest = new EntityChangeRequest();
            entityChangeRequest.setId(user.getId());
            entityChangeRequest.setEntityAsJson(objectMapper.writeValueAsString(user));
            entityChangeRequest.setVersion(user.getVersion());

            archiveResource.recordChange(entityChangeRequest);
        } catch (JsonProcessingException e) {
            throw ExceptionUtils.throwAsRuntimeException(e);
        }
    }
}
