package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.ConfiguracaoEmprestimoDAOImpl;
import br.com.gori.scb.dao.impl.TipoPessoaDAOImpl;
import br.com.gori.scb.entidade.ConfiguracaoEmprestimo;
import br.com.gori.scb.entidade.TipoPessoa;
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
public class ConfiguracaoEmprestimoTest {
    
    private static ConfiguracaoEmprestimoDAOImpl configuracaoDAO;
    private static ConfiguracaoEmprestimo configuracao;
    private static TipoPessoaDAOImpl tipoPessoaDAO;
    private static TipoPessoa tipoPessoa;
    private static Date criacao;
    
    public ConfiguracaoEmprestimoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        configuracaoDAO = new ConfiguracaoEmprestimoDAOImpl();
        tipoPessoaDAO = new TipoPessoaDAOImpl();
        tipoPessoa = tipoPessoaDAO.buscarTipoPessoaPorDescricao("Comunidade");
        if (tipoPessoa == null){
            tipoPessoa = new TipoPessoa("Comunidade");
            tipoPessoa = tipoPessoaDAO.merge(tipoPessoa);
        }
        criacao = new Date();
        configuracao = new ConfiguracaoEmprestimo(tipoPessoa,7,1,null,criacao);
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
            c = configuracaoDAO.merge(configuracao);
        }
        Assert.assertNotNull(c);
    }

    @Test
    public void findAllConfiguracao() {
        List<ConfiguracaoEmprestimo> configuracoes = new ArrayList<ConfiguracaoEmprestimo>();
        configuracoes = configuracaoDAO.listAll();
        Assert.assertFalse(configuracoes.isEmpty());
    }

    @Test
    public void countConfiguracao() {
        int value = configuracaoDAO.count();
        Assert.assertFalse(value == 0);
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
