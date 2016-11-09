package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.ConfiguracaoEmprestimoDAOImpl;
import br.com.gori.scb.dao.impl.TipoPessoaDAOImpl;
import br.com.gori.scb.entidade.ConfiguracaoEmprestimo;
import br.com.gori.scb.entidade.TipoPessoa;
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
public class ConfiguracaoEmprestimoTest {

    private static ConfiguracaoEmprestimoDAOImpl configuracaoDAO;
    private static ConfiguracaoEmprestimo configuracao;
    private static TipoPessoaDAOImpl tipoPessoaDAO;
    private static TipoPessoa tipoPessoa;
    private static Date criacao;

    public ConfiguracaoEmprestimoTest() {
        configuracaoDAO = new ConfiguracaoEmprestimoDAOImpl();
        tipoPessoaDAO = new TipoPessoaDAOImpl();
        configuracao = configuracaoDAO.buscarConfiguracaoPorPessoa("Comunidade");
        tipoPessoa = tipoPessoaDAO.buscarTipoPessoaPorDescricao("Comunidade");
        criacao = new Date();
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
        ConfiguracaoEmprestimo c = configuracaoDAO.buscarConfiguracaoPorDias(configuracao.getDias());
        if (c == null) {
            ConfiguracaoEmprestimo config = new ConfiguracaoEmprestimo(tipoPessoa, 7, 1, null, criacao);
            c = configuracaoDAO.merge(config);
        }
        Assert.assertNotNull(c);
    }

    @Test
    public void findAllConfiguracao() {
        Assert.assertFalse(configuracaoDAO.listAll().isEmpty());
    }

    @Test
    public void countConfiguracao() {
        Assert.assertNotEquals(0, configuracaoDAO.count());
    }

    @Test
    public void testRecoverConfiguracao() {
        ConfiguracaoEmprestimo c = configuracaoDAO.listAll().get(0);
        ConfiguracaoEmprestimo recovered = configuracaoDAO.recover(ConfiguracaoEmprestimo.class, c.getId());
        Assert.assertNotNull(recovered);
        Assert.assertEquals(c, recovered);
    }

    @Test
    public void testFindById() {
        ConfiguracaoEmprestimo c = configuracaoDAO.listAll().get(0);
        ConfiguracaoEmprestimo finded = configuracaoDAO.findById(c.getId());
        Assert.assertNotNull(finded);
        Assert.assertEquals(c, finded);
    }

    @Test
    public void testBuscarConfiguracaoPorDias() {
        Assert.assertNotNull(configuracaoDAO.buscarConfiguracaoPorDias(configuracao.getDias()));
    }
}
