package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.TipoAutorDAOImpl;
import br.com.gori.scb.entidade.TipoAutor;
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
public class TipoAutorTest {

    private static TipoAutorDAOImpl tipoAutorDAO;
    private static TipoAutor tipoAutor;

    public TipoAutorTest() {

    }

    @BeforeClass
    public static void setUpClass() {
        tipoAutorDAO = new TipoAutorDAOImpl();
        tipoAutor = new TipoAutor("Primário");
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
        TipoAutor t = tipoAutorDAO.buscarTipoAutorPorDescricao(tipoAutor.getDescricao());
        if (t == null) {
            t = tipoAutorDAO.merge(tipoAutor);
        }
        Assert.assertNotNull(t);
    }

    @Test
    public void findAllTipoAutores() {
        List<TipoAutor> tipoAutores = new ArrayList<TipoAutor>();
        tipoAutores = tipoAutorDAO.listAll();
        Assert.assertFalse(tipoAutores.isEmpty());
    }

    @Test
    public void countTipoAutores() {
        int value = tipoAutorDAO.count();
        Assert.assertFalse(value == 0);
    }

    @Test
    public void testRecoverTipoAutor() {
        TipoAutor t = tipoAutorDAO.listAll().get(0);
        TipoAutor recovered = tipoAutorDAO.recover(TipoAutor.class, t.getId());
        Assert.assertNotNull(recovered);
        Assert.assertEquals(t, recovered);
    }

    @Test
    public void testFindById() {
        TipoAutor t = tipoAutorDAO.listAll().get(0);
        TipoAutor finded = tipoAutorDAO.findById(t.getId());
        Assert.assertNotNull(finded);
        Assert.assertEquals(t, finded);
    }

    @Test
    public void testBuscarTipoAutorPorDescricao() {
        Assert.assertNotNull(tipoAutorDAO.buscarTipoAutorPorDescricao(tipoAutor.getDescricao()));
    }
}
