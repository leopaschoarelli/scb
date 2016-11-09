package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.EmprestimoDAOImpl;
import br.com.gori.scb.dao.impl.ExemplarDAOImpl;
import br.com.gori.scb.dao.impl.ItemEmprestimoDAOImpl;
import br.com.gori.scb.dao.impl.PessoaDAOImpl;
import br.com.gori.scb.entidade.Emprestimo;
import br.com.gori.scb.entidade.Exemplar;
import br.com.gori.scb.entidade.ItemEmprestimo;
import br.com.gori.scb.entidade.Pessoa;
import br.com.gori.scb.entidade.util.EstadoEmprestimo;
import java.util.Date;
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
    private static PessoaDAOImpl pessoaDAO;
    private static Pessoa pessoa;
    private static Exemplar exemplar;
    private static ItemEmprestimoDAOImpl itemEmprestimoDAO;
    private static ItemEmprestimo itemEmprestimo;
    private static Date criacao;
    private static Date prazo;

    public ItemEmprestimoTest() {
        emprestimoDAO = new EmprestimoDAOImpl();
        exemplarDAO = new ExemplarDAOImpl();
        itemEmprestimoDAO = new ItemEmprestimoDAOImpl();
        emprestimo = emprestimoDAO.buscarEmprestimoPorPessoa("Leonardo Henrique Paschoarelli Ribeiro");
        pessoaDAO = new PessoaDAOImpl();
        pessoa = pessoaDAO.buscarPessoaPorNome("Leonardo Henrique Paschoarelli Ribeiro");
        exemplar = exemplarDAO.buscarExemplarPorTitulo("Se7e");
        itemEmprestimo = itemEmprestimoDAO.buscarItemEmprestimoPorPessoa("Leonardo Henrique Paschoarelli Ribeiro");
        criacao = new Date();
        prazo = new Date();
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
        ItemEmprestimo i = itemEmprestimoDAO.buscarItemEmprestimoPorPessoa(pessoa.getNome());
        if (i == null) {
            ItemEmprestimo it = new ItemEmprestimo(emprestimo, prazo, criacao, EstadoEmprestimo.EMPRESTADO, exemplar);
            i = itemEmprestimoDAO.merge(it);
        }
        Assert.assertNotNull(i);
    }

    @Test
    public void findAllItemEmprestimos() {
        Assert.assertFalse(itemEmprestimoDAO.listAll().isEmpty());
    }

    @Test
    public void countItemEmprestimos() {
        Assert.assertNotEquals(0, itemEmprestimoDAO.count());
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
