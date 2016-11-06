package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.CategoriaDAOImpl;
import br.com.gori.scb.dao.impl.CidadeDAOImpl;
import br.com.gori.scb.dao.impl.EditoraDAOImpl;
import br.com.gori.scb.dao.impl.PublicacaoDAOImpl;
import br.com.gori.scb.entidade.Categoria;
import br.com.gori.scb.entidade.Cidade;
import br.com.gori.scb.entidade.Editora;
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

    public PublicacaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        publicacaoDAO = new PublicacaoDAOImpl();
        categoriaDAO = new CategoriaDAOImpl();
        editoraDAO = new EditoraDAOImpl();
        cidadeDAO = new CidadeDAOImpl();
        editora = editoraDAO.buscarEditoraPorNome("Mazza Edições");
        categoria = categoriaDAO.buscarCategoriaPorDescricao("Contos Brasileiros");
        cidade = cidadeDAO.buscarCidadePorNome("Belo Horizonte");
        publicacao = publicacaoDAO.buscarPublicacaoPorTitulo("Se7e");
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
            Publicacao publ = new Publicacao("Se7e", "Diásporas Íntimas", 2011, cidade, "O66s", "B869.308", "1º Edição", TipoPublicacao.OBRA, editora, categoria, "978-85-7160-540-4", null);
            p = publicacaoDAO.merge(publ);
        }
        Assert.assertNotNull(p);
    }

    @Test
    public void findAllPublicacaos() {
        Assert.assertFalse(publicacaoDAO.listAll().isEmpty());
    }

    @Test
    public void countPublicacoes() {
        Assert.assertNotEquals(0, publicacaoDAO.count());
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
