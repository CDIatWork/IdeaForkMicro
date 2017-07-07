package at.irian.cdiatwork.ideafork.config.repository;

import at.irian.cdiatwork.ideafork.config.domain.ConfigEntry;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import static org.apache.deltaspike.data.api.SingleResultType.OPTIONAL;

@Transactional
@Repository //from DeltaSpike
public interface ConfigRepository extends EntityRepository<ConfigEntry, String> {

    @Query(singleResult = OPTIONAL)
    ConfigEntry findByEntryKey(String key);
}
