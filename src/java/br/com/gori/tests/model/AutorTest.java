package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.AutorDAOImpl;
import br.com.gori.scb.entidade.Autor;
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
public class AutorTest {

    private static AutorDAOImpl autorDAO;
    private static Autor autor;

    public AutorTest() {

    }

    @BeforeClass
    public static void setUpClass() {
        autorDAO = new AutorDAOImpl();
        autor = new Autor("Leonardo", "Paschoarelli");
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
            a = autorDAO.merge(autor);
        }
        Assert.assertNotNull(a);
    }

    @Test
    public void findAllAutores() {
        List<Autor> autores = new ArrayList<Autor>();
        autores = autorDAO.listAll();
        Assert.assertFalse(autores.isEmpty());
    }

    @Test
    public void countAutores() {
        int value = autorDAO.count();
        Assert.assertFalse(value == 0);
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
