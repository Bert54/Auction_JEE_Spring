package configuration;

import javax.ejb.Local;
import javax.persistence.EntityManager;

@Local
public interface EntityManagerProvider {

    public EntityManager getEntityManager();

}
