package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.EstadoDAOImpl;
import br.com.gori.scb.dao.impl.PaisDAOImpl;
import br.com.gori.scb.entidade.Estado;
import br.com.gori.scb.entidade.Pais;
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
public class EstadoTest {

    private static EstadoDAOImpl estadoDAO;
    private static Estado estado;
    private static PaisDAOImpl paisDAO;
    private static Pais pais;

    public EstadoTest() {
        estadoDAO = new EstadoDAOImpl();
        paisDAO = new PaisDAOImpl();
        pais = paisDAO.buscarPaisPorNome("Brasil");
        estado = estadoDAO.buscarEstadoPorNome("Paraná");
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
        Estado e = estadoDAO.buscarEstadoPorNome(estado.getNome());
        if (e == null) {
            Estado est = new Estado("Paraná", "PR", pais);
            e = estadoDAO.merge(est);
        }
        Assert.assertNotNull(e);
    }

    @Test
    public void findAllEstados() {
        Assert.assertFalse(estadoDAO.listAll().isEmpty());
    }

    @Test
    public void countEstados() {
        Assert.assertNotEquals(0, estadoDAO.count());
    }

    @Test
    public void testRecoverEstado() {
        Estado e = estadoDAO.listAll().get(0);
        Estado recovered = estadoDAO.recover(Estado.class, e.getId());
        Assert.assertNotNull(recovered);
        Assert.assertEquals(e, recovered);
    }

    @Test
    public void testFindById() {
        Estado e = estadoDAO.listAll().get(0);
        Estado finded = estadoDAO.findById(e.getId());
        Assert.assertNotNull(finded);
        Assert.assertEquals(e, finded);
    }

    @Test
    public void testBuscarEstadoPorNome() {
        Assert.assertNotNull(estadoDAO.buscarEstadoPorNome(estado.getNome()));
    }

}
