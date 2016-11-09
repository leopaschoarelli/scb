package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.CidadeDAOImpl;
import br.com.gori.scb.dao.impl.EnderecoDAOImpl;
import br.com.gori.scb.dao.impl.PessoaDAOImpl;
import br.com.gori.scb.entidade.Cidade;
import br.com.gori.scb.entidade.Endereco;
import br.com.gori.scb.entidade.Pessoa;
import br.com.gori.scb.entidade.util.TipoEndereco;
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
public class EnderecoTest {

    private static EnderecoDAOImpl enderecoDAO;
    private static Endereco endereco;
    private static PessoaDAOImpl pessoaDAO;
    private static Pessoa pessoa;
    private static CidadeDAOImpl cidadeDAO;
    private static Cidade cidade;

    public EnderecoTest() {
        enderecoDAO = new EnderecoDAOImpl();
        pessoaDAO = new PessoaDAOImpl();
        cidadeDAO = new CidadeDAOImpl();
        pessoa = pessoaDAO.buscarPessoaPorNome("Leonardo Henrique Paschoarelli Ribeiro");
        cidade = cidadeDAO.buscarCidadePorNome("Mandaguari");
        endereco = enderecoDAO.buscarEnderecoPorLogradouro("Rua Miguel Couto");
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
        Endereco e = enderecoDAO.buscarEnderecoPorLogradouro(endereco.getLogradouro());
        if (e == null) {
            Endereco end = new Endereco(TipoEndereco.RESIDENCIAL, "Rua Miguel Couto", "409", "Jardim Esplanada", pessoa, cidade, "86975-000");
            e = enderecoDAO.merge(end);
        }
        Assert.assertNotNull(e);
    }

    @Test
    public void findAllEnderecos() {
        Assert.assertFalse(enderecoDAO.listAll().isEmpty());
    }

    @Test
    public void countEnderecos() {
        Assert.assertNotEquals(0, enderecoDAO.count());
    }

    @Test
    public void testRecoverEndereco() {
        Endereco e = enderecoDAO.listAll().get(0);
        Endereco recovered = enderecoDAO.recover(Endereco.class, e.getId());
        Assert.assertNotNull(recovered);
        Assert.assertEquals(e, recovered);
    }

    @Test
    public void testFindById() {
        Endereco e = enderecoDAO.listAll().get(0);
        Endereco finded = enderecoDAO.findById(e.getId());
        Assert.assertNotNull(finded);
        Assert.assertEquals(e, finded);
    }

    @Test
    public void testBuscarEnderecoPorLogradouro() {
        Assert.assertNotNull(enderecoDAO.buscarEnderecoPorLogradouro(endereco.getLogradouro()));
    }
}
