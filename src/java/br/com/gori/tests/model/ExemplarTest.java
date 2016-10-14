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
        estadoDAO = new EstadoDAOImpl();
        paisDAO = new PaisDAOImpl();
        publicacao = publicacaoDAO.buscarPublicacaoPorTitulo("Teste");
        if (publicacao == null) {
            categoriaDAO = new CategoriaDAOImpl();
            editoraDAO = new EditoraDAOImpl();
            editora = editoraDAO.buscarEditoraPorNome("Abril");
            if (editora == null) {
                editora = new Editora("Abril");
                editora = editoraDAO.merge(editora);
            }
            categoria = categoriaDAO.buscarCategoriaPorDescricao("Infantil");
            if (categoria == null) {
                categoria = new Categoria("Infantil", null);
                categoria = categoriaDAO.merge(categoria);
            }
            cidade = cidadeDAO.buscarCidadePorNome("Mandaguari");
            if (cidade == null) {
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
                cidade = cidadeDAO.merge(cidade);
            }
            publicacao = new Publicacao("Teste", "Testando", 2016, 300, cidade, "T342L", "3123344", "423", "1º Edição", TipoPublicacao.OBRA, editora, categoria,"423423", null);
            publicacao = publicacaoDAO.merge(publicacao);
        }
        dataAquisicao = new Date();
        exemplar = new Exemplar("1", null, publicacao, dataAquisicao, null, TipoAquisicao.DOACAO, false, 0, EstadoExemplar.DISPONIVEL);
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
            e = exemplarDAO.merge(exemplar);
        }
        Assert.assertNotNull(e);
    }

    @Test
    public void findAllExemplares() {
        List<Exemplar> exemplares = new ArrayList<Exemplar>();
        exemplares = exemplarDAO.listAll();
        Assert.assertFalse(exemplares.isEmpty());
    }

    @Test
    public void countExemplares() {
        int value = exemplarDAO.count();
        Assert.assertFalse(value == 0);
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
        Assert.assertNotNull(exemplarDAO.buscarExemplarPorTitulo(publicacao.getTitulo()));
    }

}
