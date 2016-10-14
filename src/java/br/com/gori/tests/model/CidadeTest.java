package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.CidadeDAOImpl;
import br.com.gori.scb.dao.impl.EstadoDAOImpl;
import br.com.gori.scb.dao.impl.PaisDAOImpl;
import br.com.gori.scb.entidade.Cidade;
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
public class CidadeTest {

    private static CidadeDAOImpl cidadeDAO;
    private static EstadoDAOImpl estadoDAO;
    private static PaisDAOImpl paisDAO;
    private static Cidade cidade;
    private static Estado estado;
    private static Pais pais;

    public CidadeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        cidadeDAO = new CidadeDAOImpl();
        estadoDAO = new EstadoDAOImpl();
        paisDAO = new PaisDAOImpl();
        estado = estadoDAO.buscarEstadoPorNome("Paraná");
        if (estado == null) {
            pais = paisDAO.buscarPaisPorNome("Brasil");
            if (pais == null) {
                pais = new Pais("Brasil", "BRA");
                pais = paisDAO.merge(pais);
            }
            estado = new Estado("Paraná", "PR", pais);
            estado = estadoDAO.merge(estado);
        }
        cidade = new Cidade("Mandaguari", estado);
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
        Cidade c = cidadeDAO.buscarCidadePorNome(cidade.getNome());
        if (c == null) {
            c = cidadeDAO.merge(cidade);
        }
        Assert.assertNotNull(c);
    }

    @Test
    public void findAllCidades() {
        List<Cidade> cidades = new ArrayList<Cidade>();
        cidades = cidadeDAO.listAll();
        Assert.assertFalse(cidades.isEmpty());
    }

    @Test
    public void countCidades() {
        int value = cidadeDAO.count();
        Assert.assertFalse(value == 0);
    }

    @Test
    public void testRecoverCidade() {
        Cidade c = cidadeDAO.listAll().get(0);
        Cidade recovered = cidadeDAO.recover(Cidade.class, c.getId());
        Assert.assertNotNull(recovered);
        Assert.assertEquals(c, recovered);
    }

    @Test
    public void testFindById() {
        Cidade c = cidadeDAO.listAll().get(0);
        Cidade finded = cidadeDAO.findById(c.getId());
        Assert.assertNotNull(finded);
        Assert.assertEquals(c, finded);
    }

    @Test
    public void testBuscarCidadePorNome() {
        Assert.assertNotNull(cidadeDAO.buscarCidadePorNome(cidade.getNome()));
    }
}
