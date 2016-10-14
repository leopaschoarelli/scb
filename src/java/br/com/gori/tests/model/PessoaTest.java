package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.PessoaDAOImpl;
import br.com.gori.scb.dao.impl.TipoPessoaDAOImpl;
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
public class PessoaTest {

    private static PessoaDAOImpl pessoaDAO;
    private static Pessoa pessoa;
    private static TipoPessoaDAOImpl tipoPessoaDAO;
    private static TipoPessoa tipoPessoa;
    private static Date nascimento;

    public PessoaTest() {
        pessoaDAO = new PessoaDAOImpl();
        tipoPessoaDAO = new TipoPessoaDAOImpl();
        tipoPessoa = tipoPessoaDAO.buscarTipoPessoaPorDescricao("Comunidade");
        if (tipoPessoa == null) {
            tipoPessoa = new TipoPessoa("Comunidade");
            tipoPessoa = tipoPessoaDAO.merge(tipoPessoa);
        }
        nascimento = new Date("03/10/1995");
        pessoa = new Pessoa("Leonardo Henrique Paschoarelli Ribeiro", nascimento, Sexo.MASCULINO, "125514960", Boolean.TRUE, null, tipoPessoa,null, null, null);
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
        Pessoa p = pessoaDAO.buscarPessoaPorNome(pessoa.getNome());
        if (p == null) {
            p = pessoaDAO.merge(pessoa);
        }
        Assert.assertNotNull(p);
    }

    @Test
    public void findAllPessoas() {
        List<Pessoa> pessoas = new ArrayList<Pessoa>();
        pessoas = pessoaDAO.listAll();
        Assert.assertFalse(pessoas.isEmpty());
    }

    @Test
    public void countPessoas() {
        int value = pessoaDAO.count();
        Assert.assertFalse(value == 0);
    }

    @Test
    public void testRecoverPessoa() {
        Pessoa p = pessoaDAO.listAll().get(0);
        Pessoa recovered = pessoaDAO.recover(Pessoa.class, p.getId());
        Assert.assertNotNull(recovered);
        Assert.assertEquals(p, recovered);
    }

    @Test
    public void testFindById() {
        Pessoa p = pessoaDAO.listAll().get(0);
        Pessoa finded = pessoaDAO.findById(p.getId());
        Assert.assertNotNull(finded);
        Assert.assertEquals(p, finded);
    }

    @Test
    public void testBuscarPessoaPorNome() {
        Assert.assertNotNull(pessoaDAO.buscarPessoaPorNome(pessoa.getNome()));
    }

}
