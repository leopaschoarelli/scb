package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.CategoriaDAOImpl;
import br.com.gori.scb.entidade.Categoria;
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
public class CategoriaFilhoTest {

    private static CategoriaDAOImpl categoriaDAO;
    private static Categoria superior;
    private static Categoria categoria;

    public CategoriaFilhoTest() {
        categoriaDAO = new CategoriaDAOImpl();
        categoria = categoriaDAO.buscarCategoriaPorDescricao("Folclore Brasileiro");
        superior = categoriaDAO.buscarCategoriaPorDescricao("Infantil");
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
        Categoria c = categoriaDAO.buscarCategoriaPorDescricao(categoria.getDescricao());
        if (c == null) {
            Categoria categ = new Categoria("Folclore Brasileiro", superior);
            c = categoriaDAO.merge(categ);
        }
        Assert.assertNotNull(c);
    }

    @Test
    public void findAllCategorias() {
        Assert.assertFalse(categoriaDAO.listAll().isEmpty());
    }

    @Test
    public void countCategorias() {
        Assert.assertNotEquals(0, categoriaDAO.count());
    }

    @Test
    public void testRecoverCategoria() {
        Categoria c = categoriaDAO.listAll().get(0);
        Categoria recovered = categoriaDAO.recover(Categoria.class, c.getId());
        Assert.assertNotNull(recovered);
        Assert.assertEquals(c, recovered);
    }

    @Test
    public void testFindById() {
        Categoria c = categoriaDAO.listAll().get(0);
        Categoria finded = categoriaDAO.findById(c.getId());
        Assert.assertNotNull(finded);
        Assert.assertEquals(c, finded);
    }

    @Test
    public void testBuscarCategoriaPorDescricao() {
        Assert.assertNotNull(categoriaDAO.buscarCategoriaPorDescricao(categoria.getDescricao()));
    }

}
