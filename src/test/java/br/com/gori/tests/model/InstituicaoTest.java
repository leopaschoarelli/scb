package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.InstituicaoDAOImpl;
import br.com.gori.scb.entidade.Instituicao;
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
public class InstituicaoTest {

    private static InstituicaoDAOImpl instituicaoDAO;
    private static Instituicao instituicao;

    public InstituicaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        instituicaoDAO = new InstituicaoDAOImpl();
        instituicao = instituicaoDAO.buscarInstituicaoPorNomeFantasia("Escola José Luiz Gori");
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
        Instituicao i = instituicaoDAO.buscarInstituicaoPorNomeFantasia(instituicao.getNomeFantasia());
        if (i == null) {
            Date fund = new Date("05/06/1976");
            Instituicao inst = new Instituicao("Escola José Luiz Gori", "Colégio Estadual José Luiz Gori Ensino Fundamental, Médio e Profissional", "76.416.965/0105-18", fund);
            i = instituicaoDAO.merge(inst);
        }
        Assert.assertNotNull(i);
    }

    @Test
    public void findAllInstituicoes() {
        Assert.assertFalse(instituicaoDAO.listAll().isEmpty());
    }

    @Test
    public void countInstituicoes() {
        Assert.assertNotEquals(0, instituicaoDAO.count());
    }

    @Test
    public void testRecoverInstituicao() {
        Instituicao i = instituicaoDAO.listAll().get(0);
        Instituicao recovered = instituicaoDAO.recover(Instituicao.class, i.getId());
        Assert.assertNotNull(recovered);
        Assert.assertEquals(i, recovered);
    }

    @Test
    public void testFindById() {
        Instituicao i = instituicaoDAO.listAll().get(0);
        Instituicao finded = instituicaoDAO.findById(i.getId());
        Assert.assertNotNull(finded);
        Assert.assertEquals(i, finded);
    }

    @Test
    public void testBuscarInstituicaoPorNome() {
        Assert.assertNotNull(instituicaoDAO.buscarInstituicaoPorNomeFantasia(instituicao.getNomeFantasia()));
    }
}
