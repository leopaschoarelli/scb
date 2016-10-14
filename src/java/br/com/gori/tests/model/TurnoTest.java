package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.TurnoDAOImpl;
import br.com.gori.scb.entidade.Turno;
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
public class TurnoTest {

    private static TurnoDAOImpl turnoDAO;
    private static Turno turno;

    public TurnoTest() {

    }

    @BeforeClass
    public static void setUpClass() {
        turnoDAO = new TurnoDAOImpl();
        turno = new Turno("Matutino", null, null);
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
        Turno t = turnoDAO.buscarTurnoPorDescricao(turno.getDescricao());
        if (t == null) {
            t = turnoDAO.merge(turno);
        }
        Assert.assertNotNull(t);
    }

    @Test
    public void findAllTurnos() {
        List<Turno> turnos = new ArrayList<Turno>();
        turnos = turnoDAO.listAll();
        Assert.assertFalse(turnos.isEmpty());
    }

    @Test
    public void countTurnos() {
        int value = turnoDAO.count();
        Assert.assertFalse(value == 0);
    }

    @Test
    public void testRecoverTurno() {
        Turno t = turnoDAO.listAll().get(0);
        Turno recovered = turnoDAO.recover(Turno.class, t.getId());
        Assert.assertNotNull(recovered);
        Assert.assertEquals(t, recovered);
    }

    @Test
    public void testFindById() {
        Turno t = turnoDAO.listAll().get(0);
        Turno finded = turnoDAO.findById(t.getId());
        Assert.assertNotNull(finded);
        Assert.assertEquals(t, finded);
    }

    @Test
    public void testBuscarTurnoPorDescricao() {
        Assert.assertNotNull(turnoDAO.buscarTurnoPorDescricao(turno.getDescricao()));
    }
}
