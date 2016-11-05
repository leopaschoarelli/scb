package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.CategoriaDAOImpl;
import br.com.gori.scb.dao.impl.CidadeDAOImpl;
import br.com.gori.scb.dao.impl.EditoraDAOImpl;
import br.com.gori.scb.dao.impl.EstadoDAOImpl;
import br.com.gori.scb.dao.impl.ExemplarDAOImpl;
import br.com.gori.scb.dao.impl.PaisDAOImpl;
import br.com.gori.scb.dao.impl.PublicacaoDAOImpl;
import br.com.gori.scb.entidade.Categoria;
import br.com.gori.scb.entidade.Cidade;
import br.com.gori.scb.entidade.Editora;
import br.com.gori.scb.entidade.Estado;
import br.com.gori.scb.entidade.Exemplar;
import br.com.gori.scb.entidade.Pais;
import br.com.gori.scb.entidade.Publicacao;
import br.com.gori.scb.entidade.util.EstadoExemplar;
import br.com.gori.scb.entidade.util.TipoAquisicao;
import br.com.gori.scb.entidade.util.TipoPublicacao;
import java.util.ArrayList;
import java.util.Date;
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
public class ExemplarTest {

    private static ExemplarDAOImpl exemplarDAO;
    private static Exemplar exemplar;
    private static PublicacaoDAOImpl publicacaoDAO;
    private static Publicacao publicacao;
    private static CategoriaDAOImpl categoriaDAO;
    private static Categoria categoria;
    private static EditoraDAOImpl editoraDAO;
    private static Editora editora;
    private static Date dataAquisicao;
    private static CidadeDAOImpl cidadeDAO;
    private static Cidade cidade;
    private static EstadoDAOImpl estadoDAO;
    private static Estado estado;
    private static PaisDAOImpl paisDAO;
    private static Pais pais;

    public ExemplarTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        exemplarDAO = new ExemplarDAOImpl();
        publicacaoDAO = new PublicacaoDAOImpl();
        cidadeDAO = new CidadeDAOImpl();
        publicacao = publicacaoDAO.buscarPublicacaoPorTitulo("Se7e");
        cidade = cidadeDAO.buscarCidadePorNome("Belo Horizonte");
        exemplar = exemplarDAO.buscarExemplarPorTitulo("Se7e");
        dataAquisicao = new Date();
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
        Exemplar e = exemplarDAO.buscarExemplarPorTitulo(publicacao.getTitulo());
        if (e == null) {
            String tombo = "10976-1-" + publicacao.getId();
            exemplar = new Exemplar(1, publicacao, dataAquisicao, null, TipoAquisicao.DOACAO, false, 0, EstadoExemplar.DISPONIVEL, tombo, null);
            e = exemplarDAO.merge(exemplar);
        }
        Assert.assertNotNull(e);
    }

    @Test
    public void findAllExemplares() {
        Assert.assertFalse(exemplarDAO.listAll().isEmpty());
    }

    @Test
    public void countExemplares() {
        Assert.assertNotEquals(0, exemplarDAO.count());
    }

    @Test
    public void testRecoverExemplar() {
        Exemplar e = exemplarDAO.listAll().get(0);
        Exemplar recovered = exemplarDAO.recover(Exemplar.class, e.getId());
        Assert.assertNotNull(recovered);
        Assert.assertEquals(e, recovered);
    }

    @Test
    public void testFindById() {
        Exemplar e = exemplarDAO.listAll().get(0);
        Exemplar finded = exemplarDAO.findById(e.getId());
        Assert.assertNotNull(finded);
        Assert.assertEquals(e, finded);
    }

    @Test
    public void testBuscarExemplarPorTitulo() {
        Assert.assertNotNull(exemplarDAO.buscarExemplarPorTitulo("Se7e"));
    }
}
