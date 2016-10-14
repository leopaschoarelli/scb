package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.PaisDAOImpl;
import br.com.gori.scb.entidade.Pais;
import java.util.ArrayList;
import java.util.List;
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

    }

    @BeforeClass
    public static void setUpClass() {
        paisDAO = new PaisDAOImpl();
        pais = new Pais("Brasil", "BRA");
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
            p = paisDAO.merge(pais);
        }
        Assert.assertNotNull(p);
    }

    @Test
    public void findAllPaises() {
        List<Pais> paises = new ArrayList<Pais>();
        paises = paisDAO.listAll();
        Assert.assertFalse(paises.isEmpty());
    }

    @Test
    public void countPaises() {
        int value = paisDAO.count();
        Assert.assertFalse(value == 0);
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
