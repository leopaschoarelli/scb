package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.GrupoEmprestimoDAOImpl;
import br.com.gori.scb.entidade.GrupoEmprestimo;
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
        grupoEmprestimoDAO = new GrupoEmprestimoDAOImpl();
        grupo = grupoEmprestimoDAO.buscarGrupoPorNome("Professores");
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
        GrupoEmprestimo g = grupoEmprestimoDAO.buscarGrupoPorNome(grupo.getNome());
        if (g == null) {
            GrupoEmprestimo grp = new GrupoEmprestimo("Professores");
            g = grupoEmprestimoDAO.merge(grp);
        }
        Assert.assertNotNull(g);
    }

    @Test
    public void findAllGrupos() {
        Assert.assertFalse(grupoEmprestimoDAO.listAll().isEmpty());
    }

    @Test
    public void countGrupos() {
        Assert.assertNotEquals(0, grupoEmprestimoDAO.count());
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
