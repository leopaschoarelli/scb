package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.TurmaDAOImpl;
import br.com.gori.scb.dao.impl.TurnoDAOImpl;
import br.com.gori.scb.entidade.Turma;
import br.com.gori.scb.entidade.Turno;
import br.com.gori.scb.entidade.util.Modalidade;
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
public class TurmaTest {

    private static TurmaDAOImpl turmaDAO;
    private static Turma turma;
    private static TurnoDAOImpl turnoDAO;
    private static Turno turno;

    public TurmaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        turmaDAO = new TurmaDAOImpl();
        turnoDAO = new TurnoDAOImpl();
        turno = turnoDAO.buscarTurnoPorDescricao("Matutino");
        turma = turmaDAO.buscarTurmaPorDescricao("6º Ano");
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
        Turma t = turmaDAO.buscarTurmaPorDescricao(turma.getDescricao());
        if (t == null) {
            Turma turm = new Turma("6º Ano", Modalidade.ENS_FUN, "A", turno);
            t = turmaDAO.merge(turm);
        }
        Assert.assertNotNull(t);
    }

    @Test
    public void findAllTurmas() {
        Assert.assertFalse(turmaDAO.listAll().isEmpty());
    }

    @Test
    public void countTurmas() {
        Assert.assertNotEquals(0, turmaDAO.count());
    }

    @Test
    public void testRecoverTurma() {
        Turma t = turmaDAO.listAll().get(0);
        Turma recovered = turmaDAO.recover(Turma.class, t.getId());
        Assert.assertNotNull(recovered);
        Assert.assertEquals(t, recovered);
    }

    @Test
    public void testFindById() {
        Turma t = turmaDAO.listAll().get(0);
        Turma finded = turmaDAO.findById(t.getId());
        Assert.assertNotNull(finded);
        Assert.assertEquals(t, finded);
    }

    @Test
    public void testBuscarTurmaPorDescricao() {
        Assert.assertNotNull(turmaDAO.buscarTurmaPorDescricao(turma.getDescricao()));
    }
}
