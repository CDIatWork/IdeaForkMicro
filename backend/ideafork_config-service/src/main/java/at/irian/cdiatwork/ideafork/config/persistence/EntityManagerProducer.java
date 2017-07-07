package at.irian.cdiatwork.ideafork.config.persistence;

import org.apache.deltaspike.jpa.api.entitymanager.PersistenceUnitName;
import org.apache.deltaspike.jpa.api.transaction.TransactionScoped;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@ApplicationScoped
public class EntityManagerProducer {
    @Inject
    @PersistenceUnitName("ideaForkConfigPU")
    private EntityManagerFactory entityManagerFactory;

    @Produces
    @TransactionScoped
    protected EntityManager exposeEntityManagerProxy() {
        return entityManagerFactory.createEntityManager();
    }

    protected void onTransactionEnd(@Disposes EntityManager entityManager) {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
