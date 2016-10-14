package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.EstadoDAOImpl;
import br.com.gori.scb.dao.impl.PaisDAOImpl;
import br.com.gori.scb.entidade.Estado;
import br.com.gori.scb.entidade.Pais;
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
public class EstadoTest {

    private static EstadoDAOImpl estadoDAO;
    private static Estado estado;
    private static PaisDAOImpl paisDAO;
    private static Pais pais;

    public EstadoTest() {

    }

    @BeforeClass
    public static void setUpClass() {
        estadoDAO = new EstadoDAOImpl();
        paisDAO = new PaisDAOImpl();
        pais = paisDAO.buscarPaisPorNome("Brasil");
        if (pais == null) {
            pais = new Pais("Brasil", "BRA");
            pais = paisDAO.merge(pais);
        }
        estado = new Estado("Paraná", "PR", pais);
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
            e = estadoDAO.merge(estado);
        }
        Assert.assertNotNull(e);
    }

    @Test
    public void findAllEstados() {
        List<Estado> estados = new ArrayList<Estado>();
        estados = estadoDAO.listAll();
        Assert.assertFalse(estados.isEmpty());
    }

    @Test
    public void countEstados() {
        int value = estadoDAO.count();
        Assert.assertFalse(value == 0);
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
