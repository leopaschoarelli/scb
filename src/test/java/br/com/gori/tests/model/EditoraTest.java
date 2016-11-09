package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.EditoraDAOImpl;
import br.com.gori.scb.entidade.Editora;
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
public class EditoraTest {

    private static EditoraDAOImpl editoraDAO;
    private static Editora editora;

    public EditoraTest() {
        editoraDAO = new EditoraDAOImpl();
        editora = editoraDAO.buscarEditoraPorNome("Editora Globo");

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
        Editora e = editoraDAO.buscarEditoraPorNome(editora.getNome());
        if (e == null) {
            Editora edit = new Editora("Editora Globo");
            e = editoraDAO.merge(edit);
        }
        Assert.assertNotNull(e);
    }

    @Test
    public void findAllEditoras() {
        Assert.assertFalse(editoraDAO.listAll().isEmpty());
    }

    @Test
    public void countEditoras() {
        Assert.assertNotEquals(0, editoraDAO.count());
    }

    @Test
    public void testRecoverEditora() {
        Editora e = editoraDAO.listAll().get(0);
        Editora recovered = editoraDAO.recover(Editora.class, e.getId());
        Assert.assertNotNull(recovered);
        Assert.assertEquals(e, recovered);
    }

    @Test
    public void testFindById() {
        Editora e = editoraDAO.listAll().get(0);
        Editora finded = editoraDAO.findById(e.getId());
        Assert.assertNotNull(finded);
        Assert.assertEquals(e, finded);
    }

    @Test
    public void testBuscarEditoraPorNome() {
        Assert.assertNotNull(editoraDAO.buscarEditoraPorNome(editora.getNome()));
    }
}
