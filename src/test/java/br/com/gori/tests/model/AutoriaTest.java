package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.AutorDAOImpl;
import br.com.gori.scb.dao.impl.AutoriaDAOImpl;
import br.com.gori.scb.dao.impl.TipoAutorDAOImpl;
import br.com.gori.scb.entidade.Autor;
import br.com.gori.scb.entidade.Autoria;
import br.com.gori.scb.entidade.TipoAutor;
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
public class AutoriaTest {

    private static AutoriaDAOImpl autoriaDAO;
    private static Autoria autoria;
    private static AutorDAOImpl autorDAO;
    private static Autor autor;
    private static TipoAutorDAOImpl tipoAutorDAO;
    private static TipoAutor tipoAutor;

    public AutoriaTest() {
        autoriaDAO = new AutoriaDAOImpl();
        autorDAO = new AutorDAOImpl();
        tipoAutorDAO = new TipoAutorDAOImpl();
        autor = autorDAO.buscarAutorPorNome("Lima");
        tipoAutor = tipoAutorDAO.buscarTipoAutorPorDescricao("Primário");
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
        Autoria a = autoriaDAO.buscarAutoriaPorNome(autor.getNome());
        if (a == null) {
            Autoria aut = new Autoria(autor, tipoAutor);
            a = autoriaDAO.merge(aut);
        }
        Assert.assertNotNull(a);
    }

    @Test
    public void findAllAutorias() {
        Assert.assertFalse(autoriaDAO.listAll().isEmpty());
    }

    @Test
    public void countAutorias() {
        Assert.assertNotEquals(0, autoriaDAO.count());
    }

    @Test
    public void testRecoverAutoria() {
        Autoria a = autoriaDAO.listAll().get(0);
        Autoria recovered = autoriaDAO.recover(Autoria.class, a.getId());
        Assert.assertNotNull(recovered);
        Assert.assertEquals(a, recovered);
    }

    @Test
    public void testFindById() {
        Autoria a = autoriaDAO.listAll().get(0);
        Autoria finded = autoriaDAO.findById(a.getId());
        Assert.assertNotNull(finded);
        Assert.assertEquals(a, finded);
    }

    @Test
    public void testBuscarAutoriaPorNome() {
        Assert.assertNotNull(autoriaDAO.buscarAutoriaPorNome(autor.getNome()));
    }
}
