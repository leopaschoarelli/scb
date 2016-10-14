package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.EmprestimoDAOImpl;
import br.com.gori.scb.dao.impl.PessoaDAOImpl;
import br.com.gori.scb.dao.impl.TipoPessoaDAOImpl;
import br.com.gori.scb.entidade.Emprestimo;
import br.com.gori.scb.entidade.Pessoa;
import br.com.gori.scb.entidade.TipoPessoa;
import br.com.gori.scb.entidade.util.Sexo;
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
public class EmprestimoTest {

    private static EmprestimoDAOImpl emprestimoDAO;
    private static Emprestimo emprestimo;
    private static PessoaDAOImpl pessoaDAO;
    private static Pessoa pessoa;
    private static Date criacao;
    private static Date nascimento;
    private static TipoPessoaDAOImpl tipoPessoaDAO;
    private static TipoPessoa tipoPessoa;

    public EmprestimoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        emprestimoDAO = new EmprestimoDAOImpl();
        pessoaDAO = new PessoaDAOImpl();
        tipoPessoaDAO = new TipoPessoaDAOImpl();
        criacao = new Date();
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
        Emprestimo e = emprestimoDAO.buscarEmprestimoPorPessoa(pessoa.getNome());
        if (e == null) {
            e = emprestimoDAO.merge(emprestimo);
        }
        Assert.assertNotNull(e);
    }

    @Test
    public void findAllEmprestimos() {
        List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
        emprestimos = emprestimoDAO.listAll();
        Assert.assertFalse(emprestimos.isEmpty());
    }

    @Test
    public void countEmprestimos() {
        int value = emprestimoDAO.count();
        Assert.assertFalse(value == 0);
    }

    @Test
    public void testRecoverEmprestimo() {
        Emprestimo e = emprestimoDAO.listAll().get(0);
        Emprestimo recovered = emprestimoDAO.recover(Emprestimo.class, e.getId());
        Assert.assertNotNull(recovered);
        Assert.assertEquals(e, recovered);
    }

    @Test
    public void testFindById() {
        Emprestimo e = emprestimoDAO.listAll().get(0);
        Emprestimo finded = emprestimoDAO.findById(e.getId());
        Assert.assertNotNull(finded);
        Assert.assertEquals(e, finded);
    }

    @Test
    public void testBuscarEmprestimoPorPessoa() {
        Assert.assertNotNull(emprestimoDAO.buscarEmprestimoPorPessoa(pessoa.getNome()));
    }
}
