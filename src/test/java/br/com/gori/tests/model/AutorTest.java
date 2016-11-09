package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.AutorDAOImpl;
import br.com.gori.scb.entidade.Autor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Leonardo
 */
public class AutorTest {

    private static AutorDAOImpl autorDAO;
    private static Autor autor;

    public AutorTest() {
        autorDAO = new AutorDAOImpl();
        autor = new Autor();
        autor = autorDAO.buscarAutorPorNome("Lima");
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
        Autor a = autorDAO.buscarAutorPorNome(autor.getNome());
        if (a == null) {
            Autor aut = new Autor("Lima", "Barreto");
            a = autorDAO.merge(aut);
        }
        Assert.assertNotNull(a);
    }

    @Test
    public void findAllAutores() {
        Assert.assertFalse(autorDAO.listAll().isEmpty());
    }

    @Test
    public void countAutores() {
        Assert.assertNotEquals(0, autorDAO.count());
    }

    @Test
    public void testRecoverAutor() {
        Autor a = autorDAO.listAll().get(0);
        Autor recovered = autorDAO.recover(Autor.class, a.getId());
        Assert.assertNotNull(recovered);
        Assert.assertEquals(a, recovered);
    }

    @Test
    public void testFindById() {
        Autor a = autorDAO.listAll().get(0);
        Autor finded = autorDAO.findById(a.getId());
        Assert.assertNotNull(finded);
        Assert.assertEquals(a, finded);
    }

    @Test
    public void testBuscarAutorPorNome() {
        Assert.assertNotNull(autorDAO.buscarAutorPorNome(autor.getNome()));
    }

}
