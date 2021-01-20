package configuration;

import javax.ejb.Singleton;
import javax.persistence.*;

@Singleton
public class EntityManagerProviderImpl implements EntityManagerProvider {

    @PersistenceUnit
    private final EntityManagerFactory emfactory;
    @PersistenceContext
    private final EntityManager entitymanager;

    public EntityManagerProviderImpl() {
        emfactory = Persistence.createEntityManagerFactory("Auctions");
        entitymanager = emfactory.createEntityManager();
    }

    @Override
    public EntityManager getEntityManager() {
        return this.entitymanager;
    }
}
