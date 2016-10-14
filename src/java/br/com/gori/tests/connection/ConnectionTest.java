package br.com.gori.tests.connection;

import br.com.gori.scb.connection.DatabaseConnection;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Leonardo
 */
public class ConnectionTest {

    private static DatabaseConnection dc;

    public ConnectionTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("@BeforeClass");
        dc = DatabaseConnection.createInstance();
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("@AfterClass");
    }

    @Before
    public void setUp() {
        System.out.println("@Before");
    }

    @After
    public void tearDown() {
        System.out.println("@After");
    }

    @Test
    public void getEntityManagerInstance() {
        EntityManager em = dc.getEntityManager();
        Assert.assertTrue(em.isOpen());
    }
}
