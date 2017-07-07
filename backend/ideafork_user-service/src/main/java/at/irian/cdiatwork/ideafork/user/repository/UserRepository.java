package at.irian.cdiatwork.ideafork.user.repository;

import at.irian.cdiatwork.ideafork.user.domain.User;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.enterprise.inject.Default;

import static org.apache.deltaspike.data.api.SingleResultType.OPTIONAL;

@Transactional(qualifier = Default.class)
@Repository
public interface UserRepository extends EntityRepository<User, String> {
    @Query(value = "select u from User u where u.nickName = ?1", singleResult = OPTIONAL)
    User loadByNickName(String nickName);

    @Query(value = "select u from User u where u.email = ?1", singleResult = OPTIONAL)
    User loadByEmail(String email);
}
