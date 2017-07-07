package at.irian.cdiatwork.ideafork.history.repository;

import at.irian.cdiatwork.ideafork.history.domain.EntityChange;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.enterprise.inject.Default;

import static org.apache.deltaspike.data.api.SingleResultType.OPTIONAL;

@Transactional(qualifier = Default.class)
@Repository
public interface EntityChangeRepository extends EntityRepository<EntityChange, String> {
    @Query(value = "select ec from EntityChange ec where ec.entityId = ?1 and ec.entityVersion = ?2", singleResult = OPTIONAL)
    EntityChange findRevision(String entityId, long entityVersionToFind);
}
