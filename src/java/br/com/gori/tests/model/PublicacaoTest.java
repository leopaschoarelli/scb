package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.CategoriaDAOImpl;
import br.com.gori.scb.dao.impl.CidadeDAOImpl;
import br.com.gori.scb.dao.impl.EditoraDAOImpl;
import br.com.gori.scb.dao.impl.EstadoDAOImpl;
import br.com.gori.scb.dao.impl.PaisDAOImpl;
import br.com.gori.scb.dao.impl.PublicacaoDAOImpl;
import br.com.gori.scb.entidade.Categoria;
import br.com.gori.scb.entidade.Cidade;
import br.com.gori.scb.entidade.Editora;
import br.com.gori.scb.entidade.Estado;
import br.com.gori.scb.entidade.Pais;
import br.com.gori.scb.entidade.Publicacao;
import br.com.gori.scb.entidade.util.TipoPublicacao;
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
public class PublicacaoTest {

    private static PublicacaoDAOImpl publicacaoDAO;
    private static Publicacao publicacao;
    private static EditoraDAOImpl editoraDAO;
    private static Editora editora;
    private static CategoriaDAOImpl categoriaDAO;
    private static Categoria categoria;
    private static CidadeDAOImpl cidadeDAO;
    private static Cidade cidade;
    private static EstadoDAOImpl estadoDAO;
    private static Estado estado;
    private static PaisDAOImpl paisDAO;
    private static Pais pais;

    public PublicacaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        publicacaoDAO = new PublicacaoDAOImpl();
        categoriaDAO = new CategoriaDAOImpl();
        editoraDAO = new EditoraDAOImpl();
        cidadeDAO = new CidadeDAOImpl();
        estadoDAO = new EstadoDAOImpl();
        paisDAO = new PaisDAOImpl();
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
        publicacao = new Publicacao("Teste", "Testando", 2016, 300, cidade, "T342L", "3123344", "423", "1º Edição", TipoPublicacao.OBRA, editora, categoria, "423423", null);
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
        Publicacao p = publicacaoDAO.buscarPublicacaoPorTitulo(publicacao.getTitulo());
        if (p == null) {
            p = publicacaoDAO.merge(publicacao);
        }
        Assert.assertNotNull(p);
    }

    @Test
    public void findAllPublicacaos() {
        List<Publicacao> publicacoes = new ArrayList<Publicacao>();
        publicacoes = publicacaoDAO.listAll();
        Assert.assertFalse(publicacoes.isEmpty());
    }

    @Test
    public void countPublicacaos() {
        int value = publicacaoDAO.count();
        Assert.assertFalse(value == 0);
    }

    @Test
    public void testRecoverPublicacao() {
        Publicacao p = publicacaoDAO.listAll().get(0);
        Publicacao recovered = publicacaoDAO.recover(Publicacao.class, p.getId());
        Assert.assertNotNull(recovered);
        Assert.assertEquals(p, recovered);
    }

    @Test
    public void testFindById() {
        Publicacao p = publicacaoDAO.listAll().get(0);
        Publicacao finded = publicacaoDAO.findById(p.getId());
        Assert.assertNotNull(finded);
        Assert.assertEquals(p, finded);
    }

    @Test
    public void testBuscarPublicacaoPorTitulo() {
        Assert.assertNotNull(publicacaoDAO.buscarPublicacaoPorTitulo(publicacao.getTitulo()));
    }
}
