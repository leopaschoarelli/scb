package br.com.gori.tests.connection;

import br.com.gori.scb.connection.EntityManagerProducer;
import javax.persistence.EntityManager;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Leonardo
 */
public class ConnectionTest {

    private final EntityManager entityManager;

    public ConnectionTest() {
        entityManager = EntityManagerProducer.getEntityManager();
    }

    @Test
    public void hasValidConnection() {
        Assert.assertNotNull(entityManager);
        Assert.assertTrue(entityManager.isOpen());
    }
}
