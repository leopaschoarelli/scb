package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.PessoaDAOImpl;
import br.com.gori.scb.dao.impl.ReservaDAOImpl;
import br.com.gori.scb.dao.impl.TipoPessoaDAOImpl;
import br.com.gori.scb.entidade.Pessoa;
import br.com.gori.scb.entidade.Reserva;
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
public class ReservaTest {

    private static ReservaDAOImpl reservaDAO;
    private static Reserva reserva;
    private static PessoaDAOImpl pessoaDAO;
    private static Pessoa pessoa;
    private static Date criacao;
    private static Date nascimento;
    private static TipoPessoaDAOImpl tipoPessoaDAO;
    private static TipoPessoa tipoPessoa;

    public ReservaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        reservaDAO = new ReservaDAOImpl();
        pessoaDAO = new PessoaDAOImpl();
        criacao = new Date();
        pessoa = pessoaDAO.buscarPessoaPorNome("Leonardo Henrique Paschoarelli Ribeiro");
        if (pessoa == null) {
            pessoaDAO = new PessoaDAOImpl();
            tipoPessoaDAO = new TipoPessoaDAOImpl();
            tipoPessoa = tipoPessoaDAO.buscarTipoPessoaPorDescricao("Comunidade");
            if (tipoPessoa == null) {
                tipoPessoa = new TipoPessoa("Comunidade");
                tipoPessoa = tipoPessoaDAO.merge(tipoPessoa);
            }
            nascimento = new Date("03/10/1995");
            pessoa = new Pessoa("Leonardo Henrique Paschoarelli Ribeiro", nascimento, Sexo.MASCULINO, "125514960", Boolean.TRUE, null, tipoPessoa, null, null, null);
        }
        reserva = new Reserva(pessoa, criacao);
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
        Reserva e = reservaDAO.buscarReservaPorPessoa(pessoa.getNome());
        if (e == null) {
            e = reservaDAO.merge(reserva);
        }
        Assert.assertNotNull(e);
    }

    @Test
    public void findAllReservas() {
        List<Reserva> reservas = new ArrayList<Reserva>();
        reservas = reservaDAO.listAll();
        Assert.assertFalse(reservas.isEmpty());
    }

    @Test
    public void countReservas() {
        int value = reservaDAO.count();
        Assert.assertFalse(value == 0);
    }

    @Test
    public void testRecoverReserva() {
        Reserva e = reservaDAO.listAll().get(0);
        Reserva recovered = reservaDAO.recover(Reserva.class, e.getId());
        Assert.assertNotNull(recovered);
        Assert.assertEquals(e, recovered);
    }

    @Test
    public void testFindById() {
        Reserva e = reservaDAO.listAll().get(0);
        Reserva finded = reservaDAO.findById(e.getId());
        Assert.assertNotNull(finded);
        Assert.assertEquals(e, finded);
    }

    @Test
    public void testBuscarReservaPorPessoa() {
        Assert.assertNotNull(reservaDAO.buscarReservaPorPessoa(pessoa.getNome()));
    }
}
