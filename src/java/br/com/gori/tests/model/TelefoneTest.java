package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.PessoaDAOImpl;
import br.com.gori.scb.dao.impl.TelefoneDAOImpl;
import br.com.gori.scb.dao.impl.TipoPessoaDAOImpl;
import br.com.gori.scb.entidade.Pessoa;
import br.com.gori.scb.entidade.Telefone;
import br.com.gori.scb.entidade.TipoPessoa;
import br.com.gori.scb.entidade.util.Sexo;
import br.com.gori.scb.entidade.util.TipoTelefone;
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
public class TelefoneTest {

    private static TelefoneDAOImpl telefoneDAO;
    private static Telefone telefone;
    private static PessoaDAOImpl pessoaDAO;
    private static Pessoa pessoa;
    private static Date nascimento;
    private static TipoPessoaDAOImpl tipoPessoaDAO;
    private static TipoPessoa tipoPessoa;

    public TelefoneTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        telefoneDAO = new TelefoneDAOImpl();
        pessoaDAO = new PessoaDAOImpl();
        tipoPessoaDAO = new TipoPessoaDAOImpl();
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
        telefone = new Telefone("(44)9883-0385", TipoTelefone.CELULAR, pessoa);
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
        Telefone t = telefoneDAO.buscarTelefonePorNumero(telefone.getNumero());
        if (t == null) {
            t = telefoneDAO.merge(telefone);
        }
        Assert.assertNotNull(t);
    }

    @Test
    public void findAllTelefones() {
        List<Telefone> telefones = new ArrayList<Telefone>();
        telefones = telefoneDAO.listAll();
        Assert.assertFalse(telefones.isEmpty());
    }

    @Test
    public void countTelefones() {
        int value = telefoneDAO.count();
        Assert.assertFalse(value == 0);
    }

    @Test
    public void testRecoverTelefone() {
        Telefone t = telefoneDAO.listAll().get(0);
        Telefone recovered = telefoneDAO.recover(Telefone.class, t.getId());
        Assert.assertNotNull(recovered);
        Assert.assertEquals(t, recovered);
    }

    @Test
    public void testFindById() {
        Telefone t = telefoneDAO.listAll().get(0);
        Telefone finded = telefoneDAO.findById(t.getId());
        Assert.assertNotNull(finded);
        Assert.assertEquals(t, finded);
    }

    @Test
    public void testBuscarTelefonePorNumero() {
        Assert.assertNotNull(telefoneDAO.buscarTelefonePorNumero(telefone.getNumero()));
    }
}
