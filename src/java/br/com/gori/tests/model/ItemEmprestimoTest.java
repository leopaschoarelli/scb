package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.CategoriaDAOImpl;
import br.com.gori.scb.dao.impl.CidadeDAOImpl;
import br.com.gori.scb.dao.impl.ConfiguracaoEmprestimoDAOImpl;
import br.com.gori.scb.dao.impl.EditoraDAOImpl;
import br.com.gori.scb.dao.impl.EmprestimoDAOImpl;
import br.com.gori.scb.dao.impl.EstadoDAOImpl;
import br.com.gori.scb.dao.impl.ExemplarDAOImpl;
import br.com.gori.scb.dao.impl.ItemEmprestimoDAOImpl;
import br.com.gori.scb.dao.impl.PaisDAOImpl;
import br.com.gori.scb.dao.impl.PessoaDAOImpl;
import br.com.gori.scb.dao.impl.PublicacaoDAOImpl;
import br.com.gori.scb.dao.impl.TipoPessoaDAOImpl;
import br.com.gori.scb.entidade.Categoria;
import br.com.gori.scb.entidade.Cidade;
import br.com.gori.scb.entidade.ConfiguracaoEmprestimo;
import br.com.gori.scb.entidade.Editora;
import br.com.gori.scb.entidade.Emprestimo;
import br.com.gori.scb.entidade.Estado;
import br.com.gori.scb.entidade.Exemplar;
import br.com.gori.scb.entidade.ItemEmprestimo;
import br.com.gori.scb.entidade.Pais;
import br.com.gori.scb.entidade.Pessoa;
import br.com.gori.scb.entidade.Publicacao;
import br.com.gori.scb.entidade.TipoPessoa;
import br.com.gori.scb.entidade.util.EstadoEmprestimo;
import br.com.gori.scb.entidade.util.EstadoExemplar;
import br.com.gori.scb.entidade.util.Sexo;
import br.com.gori.scb.entidade.util.TipoAquisicao;
import br.com.gori.scb.entidade.util.TipoPublicacao;
import java.util.ArrayList;
import java.util.Calendar;
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
public class ItemEmprestimoTest {

    private static EmprestimoDAOImpl emprestimoDAO;
    private static Emprestimo emprestimo;
    private static ExemplarDAOImpl exemplarDAO;
    private static Exemplar exemplar;
    private static ItemEmprestimoDAOImpl itemEmprestimoDAO;
    private static ItemEmprestimo itemEmprestimo;
    private static PessoaDAOImpl pessoaDAO;
    private static Pessoa pessoa;
    private static Date criacao;
    private static Date nascimento;
    private static Calendar prazos;
    private static Date prazo;
    private static Date dataAquisicao;
    private static TipoPessoaDAOImpl tipoPessoaDAO;
    private static TipoPessoa tipoPessoa;
    private static ConfiguracaoEmprestimoDAOImpl configuracaoEmprestimoDAO;
    private static ConfiguracaoEmprestimo configuracaoEmprestimo;
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

    public ItemEmprestimoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        emprestimoDAO = new EmprestimoDAOImpl();
        exemplarDAO = new ExemplarDAOImpl();
        itemEmprestimoDAO = new ItemEmprestimoDAOImpl();
        pessoaDAO = new PessoaDAOImpl();
        tipoPessoaDAO = new TipoPessoaDAOImpl();
        configuracaoEmprestimoDAO = new ConfiguracaoEmprestimoDAOImpl();
        publicacaoDAO = new PublicacaoDAOImpl();
        categoriaDAO = new CategoriaDAOImpl();
        editoraDAO = new EditoraDAOImpl();
        criacao = new Date();
        cidadeDAO = new CidadeDAOImpl();
        estadoDAO = new EstadoDAOImpl();
        paisDAO = new PaisDAOImpl();
        prazos = Calendar.getInstance();
        emprestimo = emprestimoDAO.buscarEmprestimoPorPessoa("Leonardo Henrique Paschoarelli Ribeiro");
        if (emprestimo == null) {
            pessoa = pessoaDAO.buscarPessoaPorNome("Leonardo Henrique Paschoarelli Ribeiro");
            if (pessoa == null) {
                tipoPessoa = tipoPessoaDAO.buscarTipoPessoaPorDescricao("Comunidade");
                if (tipoPessoa == null) {
                    tipoPessoa = new TipoPessoa("Comunidade");
                    tipoPessoa = tipoPessoaDAO.merge(tipoPessoa);
                }
                nascimento = new Date("03/10/1995");
                pessoa = new Pessoa("Leonardo Henrique Paschoarelli Ribeiro", nascimento, Sexo.MASCULINO, "125514960", Boolean.TRUE, null, tipoPessoa, null, null, null);
                pessoa = pessoaDAO.merge(pessoa);
            }
            emprestimo = new Emprestimo(pessoa, criacao);
            emprestimo = emprestimoDAO.merge(emprestimo);
        } else {
            tipoPessoa = tipoPessoaDAO.buscarTipoPessoaPorDescricao("Comunidade");
            if (tipoPessoa == null) {
                tipoPessoa = new TipoPessoa("Comunidade");
                tipoPessoa = tipoPessoaDAO.merge(tipoPessoa);
            }
            pessoa = pessoaDAO.buscarPessoaPorNome("Leonardo Henrique Paschoarelli Ribeiro");
            if (pessoa == null) {
                pessoaDAO = new PessoaDAOImpl();
                nascimento = new Date("03/10/1995");
                pessoa = new Pessoa("Leonardo Henrique Paschoarelli Ribeiro", nascimento, Sexo.MASCULINO, "125514960", Boolean.TRUE, null, tipoPessoa, null, null, null);
                pessoa = pessoaDAO.merge(pessoa);
            }
        }
        configuracaoEmprestimo = configuracaoEmprestimoDAO.buscarConfiguracaoPorPessoa(tipoPessoa.getDescricao());
        if (configuracaoEmprestimo == null) {
            configuracaoEmprestimo = new ConfiguracaoEmprestimo(tipoPessoa, 7, 1, null, criacao);
        }
        prazo = prazos.getTime();
        exemplar = exemplarDAO.buscarExemplarPorTitulo("Teste");
        if (exemplar == null) {
            publicacao = publicacaoDAO.buscarPublicacaoPorTitulo("Teste");
            if (publicacao == null) {
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
                publicacao = publicacaoDAO.merge(publicacao);
            }
            dataAquisicao = new Date();
            exemplar = new Exemplar("1", 1, publicacao, dataAquisicao, null, TipoAquisicao.DOACAO, false, 0, EstadoExemplar.DISPONIVEL);
            exemplar = exemplarDAO.merge(exemplar);
        }
        itemEmprestimo = new ItemEmprestimo(emprestimo, prazo, criacao, EstadoEmprestimo.EMPRESTADO, exemplar);
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
        ItemEmprestimo i = itemEmprestimoDAO.buscarItemEmprestimoPorPessoa(pessoa.getNome());
        if (i == null) {
            i = itemEmprestimoDAO.merge(itemEmprestimo);
        }
        Assert.assertNotNull(i);
    }

    @Test
    public void findAllItemEmprestimos() {
        List<ItemEmprestimo> itensEmprestimos = new ArrayList<ItemEmprestimo>();
        itensEmprestimos = itemEmprestimoDAO.listAll();
        Assert.assertFalse(itensEmprestimos.isEmpty());
    }

    @Test
    public void countItemEmprestimos() {
        int value = itemEmprestimoDAO.count();
        Assert.assertFalse(value == 0);
    }

    @Test
    public void testRecoverItemEmprestimo() {
        ItemEmprestimo i = itemEmprestimoDAO.listAll().get(0);
        ItemEmprestimo recovered = itemEmprestimoDAO.recover(ItemEmprestimo.class, i.getId());
        Assert.assertNotNull(recovered);
        Assert.assertEquals(i, recovered);
    }

    @Test
    public void testFindById() {
        ItemEmprestimo i = itemEmprestimoDAO.listAll().get(0);
        ItemEmprestimo finded = itemEmprestimoDAO.findById(i.getId());
        Assert.assertNotNull(finded);
        Assert.assertEquals(i, finded);
    }

    @Test
    public void testBuscarItemEmprestimoPorPessoa() {
        Assert.assertNotNull(itemEmprestimoDAO.buscarItemEmprestimoPorPessoa(pessoa.getNome()));
    }

}
