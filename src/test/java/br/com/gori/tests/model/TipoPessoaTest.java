package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.TipoPessoaDAOImpl;
import br.com.gori.scb.entidade.TipoPessoa;
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
public class TipoPessoaTest {

    private static TipoPessoaDAOImpl tipoPessoaDAO;
    private static TipoPessoa tipoPessoa;

    public TipoPessoaTest() {
        tipoPessoaDAO = new TipoPessoaDAOImpl();
        tipoPessoa = tipoPessoaDAO.buscarTipoPessoaPorDescricao("Comunidade");
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
        TipoPessoa t = tipoPessoaDAO.buscarTipoPessoaPorDescricao(tipoPessoa.getDescricao());
        if (t == null) {
            TipoPessoa tp = new TipoPessoa("Comunidade");
            t = tipoPessoaDAO.merge(tipoPessoa);
        }
        Assert.assertNotNull(t);
    }

    @Test
    public void findAllTipoPessoaes() {
        Assert.assertFalse(tipoPessoaDAO.listAll().isEmpty());
    }

    @Test
    public void countTipoPessoas() {
        Assert.assertNotEquals(0, tipoPessoaDAO.count());
    }

    @Test
    public void testRecoverTipoPessoa() {
        TipoPessoa t = tipoPessoaDAO.listAll().get(0);
        TipoPessoa recovered = tipoPessoaDAO.recover(TipoPessoa.class, t.getId());
        Assert.assertNotNull(recovered);
        Assert.assertEquals(t, recovered);
    }

    @Test
    public void testFindById() {
        TipoPessoa t = tipoPessoaDAO.listAll().get(0);
        TipoPessoa finded = tipoPessoaDAO.findById(t.getId());
        Assert.assertNotNull(finded);
        Assert.assertEquals(t, finded);
    }

    @Test
    public void testBuscarTipoPessoaPorDescricao() {
        Assert.assertNotNull(tipoPessoaDAO.buscarTipoPessoaPorDescricao(tipoPessoa.getDescricao()));
    }
}
