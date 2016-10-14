package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.EditoraDAOImpl;
import br.com.gori.scb.entidade.Editora;
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
public class EditoraTest {

    private static EditoraDAOImpl editoraDAO;
    private static Editora editora;

    public EditoraTest() {

    }

    @BeforeClass
    public static void setUpClass() {
        editoraDAO = new EditoraDAOImpl();
        editora = new Editora("Abril");
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
            e = editoraDAO.merge(editora);
        }
        Assert.assertNotNull(e);
    }

    @Test
    public void findAllEditoras() {
        List<Editora> editoras = new ArrayList<Editora>();
        editoras = editoraDAO.listAll();
        Assert.assertFalse(editoras.isEmpty());
    }

    @Test
    public void countEditoras() {
        int value = editoraDAO.count();
        Assert.assertFalse(value == 0);
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
