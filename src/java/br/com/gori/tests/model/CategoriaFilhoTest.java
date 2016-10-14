package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.CategoriaDAOImpl;
import br.com.gori.scb.entidade.Categoria;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Leonardo
 */
public class CategoriaFilhoTest {

    private static CategoriaDAOImpl categoriaDAO;
    private static Categoria superior;
    private static Categoria categoria;
    

    public CategoriaFilhoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        categoriaDAO = new CategoriaDAOImpl();
        superior = categoriaDAO.buscarCategoriaPorDescricao("Infantil");
        if (superior == null) {
            superior = new Categoria("Infantil", null);
            superior = categoriaDAO.merge(superior);
        }
        categoria = new Categoria("Folclore Brasileiro",superior);
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
            c = categoriaDAO.merge(categoria);
        }
        Assert.assertNotNull(c);
    }

    @Test
    public void findAllCategorias() {
        List<Categoria> categorias = new ArrayList<Categoria>();
        categorias = categoriaDAO.listAll();
        Assert.assertFalse(categorias.isEmpty());
    }

    @Test
    public void countCategorias() {
        int value = categoriaDAO.count();
        Assert.assertFalse(value == 0);
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
