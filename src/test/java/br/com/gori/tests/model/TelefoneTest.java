package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.PessoaDAOImpl;
import br.com.gori.scb.dao.impl.TelefoneDAOImpl;
import br.com.gori.scb.dao.impl.TipoPessoaDAOImpl;
import br.com.gori.scb.entidade.Pessoa;
import br.com.gori.scb.entidade.Telefone;
import br.com.gori.scb.entidade.TipoPessoa;
import br.com.gori.scb.entidade.util.TipoTelefone;
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
    private static TipoPessoaDAOImpl tipoPessoaDAO;
    private static TipoPessoa tipoPessoa;

    public TelefoneTest() {
        telefoneDAO = new TelefoneDAOImpl();
        pessoaDAO = new PessoaDAOImpl();
        tipoPessoaDAO = new TipoPessoaDAOImpl();
        tipoPessoa = tipoPessoaDAO.buscarTipoPessoaPorDescricao("Comunidade");
        pessoa = pessoaDAO.buscarPessoaPorNome("Leonardo Henrique Paschoarelli Ribeiro");
        telefone = telefoneDAO.buscarTelefonePorNumero("(44)9883-0385");
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
        Telefone t = telefoneDAO.buscarTelefonePorNumero(telefone.getNumero());
        if (t == null) {
            Telefone telef = new Telefone("(44)9883-0385", TipoTelefone.CELULAR, pessoa);
            t = telefoneDAO.merge(telef);
        }
        Assert.assertNotNull(t);
    }

    @Test
    public void findAllTelefones() {
        Assert.assertFalse(telefoneDAO.listAll().isEmpty());
    }

    @Test
    public void countTelefones() {
        Assert.assertNotEquals(0, telefoneDAO.count());
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
