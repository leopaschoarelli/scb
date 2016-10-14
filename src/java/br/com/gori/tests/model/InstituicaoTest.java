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
    private static Date fundacao;
    
    public InstituicaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        instituicaoDAO = new InstituicaoDAOImpl();
        fundacao = new Date("05/06/1976");
        instituicao = new Instituicao("Escola José Luiz Gori","Colégio Estadual José Luiz Gori Ensino Fundamental, Médio e Profissional","76.416.965/0105-18",fundacao);
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
            i = instituicaoDAO.merge(instituicao);
        }
        Assert.assertNotNull(i);
    }

    @Test
    public void findAllInstituicoes() {
        List<Instituicao> instituicoes = new ArrayList<Instituicao>();
        instituicoes = instituicaoDAO.listAll();
        Assert.assertFalse(instituicoes.isEmpty());
    }

    @Test
    public void countInstituicoes() {
        int value = instituicaoDAO.count();
        Assert.assertFalse(value == 0);
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
