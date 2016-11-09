package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.PaisDAOImpl;
import br.com.gori.scb.entidade.Pais;
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
public class PaisTest {

    private static PaisDAOImpl paisDAO;
    private static Pais pais;

    public PaisTest() {
        paisDAO = new PaisDAOImpl();
        pais = paisDAO.buscarPaisPorNome("Brasil");
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void persistIfNotExists() {
        Pais p = paisDAO.buscarPaisPorNome(pais.getNome());
        if (p == null) {
            Pais ps = new Pais("Brasil", "BRA");
            p = paisDAO.merge(ps);
        }
        Assert.assertNotNull(p);
    }

    @Test
    public void findAllPaises() {
        Assert.assertFalse(paisDAO.listAll().isEmpty());
    }

    @Test
    public void countPaises() {
        Assert.assertNotEquals(0, paisDAO.count());
    }

    @Test
    public void testRecoverPais() {
        Pais p = paisDAO.listAll().get(0);
        Pais recovered = paisDAO.recover(Pais.class, p.getId());
        Assert.assertNotNull(recovered);
        Assert.assertEquals(p, recovered);
    }

    @Test
    public void testFindById() {
        Pais p = paisDAO.listAll().get(0);
        Pais finded = paisDAO.findById(p.getId());
        Assert.assertNotNull(finded);
        Assert.assertEquals(p, finded);
    }

    @Test
    public void testBuscarPaisPorNome() {
        Assert.assertNotNull(paisDAO.buscarPaisPorNome(pais.getNome()));
    }
}
