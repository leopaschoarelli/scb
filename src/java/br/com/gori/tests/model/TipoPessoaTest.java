package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.TipoPessoaDAOImpl;
import br.com.gori.scb.entidade.TipoPessoa;
import java.util.ArrayList;
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
public class TipoPessoaTest {

    private static TipoPessoaDAOImpl tipoPessoaDAO;
    private static TipoPessoa tipoPessoa;

    public TipoPessoaTest() {

    }

    @BeforeClass
    public static void setUpClass() {
        tipoPessoaDAO = new TipoPessoaDAOImpl();
        tipoPessoa = new TipoPessoa("Comunidade");
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
            t = tipoPessoaDAO.merge(tipoPessoa);
        }
        Assert.assertNotNull(t);
    }

    @Test
    public void findAllTipoPessoaes() {
        List<TipoPessoa> tipoPessoas = new ArrayList<TipoPessoa>();
        tipoPessoas = tipoPessoaDAO.listAll();
        Assert.assertFalse(tipoPessoas.isEmpty());
    }

    @Test
    public void countTipoPessoas() {
        int value = tipoPessoaDAO.count();
        Assert.assertFalse(value == 0);
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
