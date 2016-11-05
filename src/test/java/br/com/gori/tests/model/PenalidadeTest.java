package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.PenalidadeDAOImpl;
import br.com.gori.scb.entidade.Penalidade;
import br.com.gori.scb.entidade.util.TipoPenalidade;
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
public class PenalidadeTest {

    private static PenalidadeDAOImpl penalidadeDAO;
    private static Penalidade penalidade;

    public PenalidadeTest() {

    }

    @BeforeClass
    public static void setUpClass() {
        penalidadeDAO = new PenalidadeDAOImpl();
        penalidade = penalidadeDAO.buscarPenalidadePorDescricao("Atraso Emprestimo");
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
        Penalidade p = penalidadeDAO.buscarPenalidadePorDescricao(penalidade.getDescricao());
        if (p == null) {
            Penalidade penal = new Penalidade("Atraso Emprestimo", 1.00, TipoPenalidade.SUSPENSAO);
            p = penalidadeDAO.merge(penal);
        }
        Assert.assertNotNull(p);
    }

    @Test
    public void findAllPenalidades() {
        Assert.assertFalse(penalidadeDAO.listAll().isEmpty());
    }

    @Test
    public void countPenalidades() {
        Assert.assertNotEquals(0, penalidadeDAO.count());
    }

    @Test
    public void testRecoverPenalidade() {
        Penalidade p = penalidadeDAO.listAll().get(0);
        Penalidade recovered = penalidadeDAO.recover(Penalidade.class, p.getId());
        Assert.assertNotNull(recovered);
        Assert.assertEquals(p, recovered);
    }

    @Test
    public void testFindById() {
        Penalidade p = penalidadeDAO.listAll().get(0);
        Penalidade finded = penalidadeDAO.findById(p.getId());
        Assert.assertNotNull(finded);
        Assert.assertEquals(p, finded);
    }

    @Test
    public void testBuscarPenalidadePorDescricao() {
        Assert.assertNotNull(penalidadeDAO.buscarPenalidadePorDescricao(penalidade.getDescricao()));
    }
}
