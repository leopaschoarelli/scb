package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.GrupoEmprestimoDAOImpl;
import br.com.gori.scb.entidade.GrupoEmprestimo;
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
public class GrupoEmprestimoTest {

    private static GrupoEmprestimoDAOImpl grupoEmprestimoDAO;
    private static GrupoEmprestimo grupo;

    public GrupoEmprestimoTest() {

    }

    @BeforeClass
    public static void setUpClass() {
        grupoEmprestimoDAO = new GrupoEmprestimoDAOImpl();
        grupo = new GrupoEmprestimo("Professores");
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
        GrupoEmprestimo g = grupoEmprestimoDAO.buscarGrupoPorNome(grupo.getNome());
        if (g == null) {
            g = grupoEmprestimoDAO.merge(grupo);
        }
        Assert.assertNotNull(g);
    }

    @Test
    public void findAllGrupos() {
        List<GrupoEmprestimo> grupos = new ArrayList<GrupoEmprestimo>();
        grupos = grupoEmprestimoDAO.listAll();
        Assert.assertFalse(grupos.isEmpty());
    }

    @Test
    public void countGrupos() {
        int value = grupoEmprestimoDAO.count();
        Assert.assertFalse(value == 0);
    }

    @Test
    public void testRecoverGrupoEmprestimo() {
        GrupoEmprestimo g = grupoEmprestimoDAO.listAll().get(0);
        GrupoEmprestimo recovered = grupoEmprestimoDAO.recover(GrupoEmprestimo.class, g.getId());
        Assert.assertNotNull(recovered);
        Assert.assertEquals(g, recovered);
    }

    @Test
    public void testFindById() {
        GrupoEmprestimo g = grupoEmprestimoDAO.listAll().get(0);
        GrupoEmprestimo finded = grupoEmprestimoDAO.findById(g.getId());
        Assert.assertNotNull(finded);
        Assert.assertEquals(g, finded);
    }

    @Test
    public void testBuscarGrupoEmprestimoPorNome() {
        Assert.assertNotNull(grupoEmprestimoDAO.buscarGrupoPorNome(grupo.getNome()));
    }
}
