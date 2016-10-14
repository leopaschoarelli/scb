package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.AutorDAOImpl;
import br.com.gori.scb.dao.impl.AutoriaDAOImpl;
import br.com.gori.scb.dao.impl.TipoAutorDAOImpl;
import br.com.gori.scb.entidade.Autor;
import br.com.gori.scb.entidade.Autoria;
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
public class AutoriaTest {

    private static AutoriaDAOImpl autoriaDAO;
    private static Autoria autoria;
    private static AutorDAOImpl autorDAO;
    private static Autor autor;
    private static TipoAutorDAOImpl tipoAutorDAO;
    private static TipoAutor tipoAutor;

    public AutoriaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        autoriaDAO = new AutoriaDAOImpl();
        autorDAO = new AutorDAOImpl();
        autor = autorDAO.buscarAutorPorNome("Leonardo");
        if (autor == null) {
            autor = new Autor("Leonardo", "Paschoarelli");
            autor = autorDAO.merge(autor);
        }
        tipoAutorDAO = new TipoAutorDAOImpl();
        tipoAutor = tipoAutorDAO.buscarTipoAutorPorDescricao("Primário");
        if (tipoAutor == null) {
            tipoAutor = new TipoAutor("Primário");
            tipoAutor = tipoAutorDAO.merge(tipoAutor);
        }
        autoria = new Autoria(autor, tipoAutor);
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
            a = autoriaDAO.merge(autoria);
        }
        Assert.assertNotNull(a);
    }

    @Test
    public void findAllAutorias() {
        List<Autoria> autorias = new ArrayList<Autoria>();
        autorias = autoriaDAO.listAll();
        Assert.assertFalse(autorias.isEmpty());
    }

    @Test
    public void countAutorias() {
        int value = autoriaDAO.count();
        Assert.assertFalse(value == 0);
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
