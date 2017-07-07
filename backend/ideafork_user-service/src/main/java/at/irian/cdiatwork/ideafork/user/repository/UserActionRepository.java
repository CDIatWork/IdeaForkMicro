package at.irian.cdiatwork.ideafork.user.repository;

import at.irian.cdiatwork.ideafork.user.domain.User;
import at.irian.cdiatwork.ideafork.user.domain.UserAction;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.MaxResults;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.enterprise.inject.Default;
import java.util.List;

import static org.apache.deltaspike.data.api.SingleResultType.OPTIONAL;

@Transactional(qualifier = Default.class)
@Repository
public interface UserActionRepository extends EntityRepository<UserAction, String> {

    @Query(value = "select ua from UserAction ua where ua.user = ?1 order by ua.createdAt desc", singleResult = OPTIONAL)
    List<UserAction> loadLatestActivities(User user, @MaxResults int maxResults);
}
