//package br.com.gori.tests.model;
//
//import br.com.gori.scb.dao.impl.PessoaDAOImpl;
//import br.com.gori.scb.dao.impl.ReservaDAOImpl;
//import br.com.gori.scb.dao.impl.TipoPessoaDAOImpl;
//import br.com.gori.scb.entidade.Pessoa;
//import br.com.gori.scb.entidade.Reserva;
//import br.com.gori.scb.entidade.TipoPessoa;
//import br.com.gori.scb.entidade.util.Sexo;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
///**
// *
// * @author Leonardo
// */
//public class ReservaTest {
//
//    private static ReservaDAOImpl reservaDAO;
//    private static Reserva reserva;
//    private static PessoaDAOImpl pessoaDAO;
//    private static Pessoa pessoa;
//    private static Date criacao;
//
//    public ReservaTest() {
//    }
//
//    @BeforeClass
//    public static void setUpClass() {
//        reservaDAO = new ReservaDAOImpl();
//        pessoaDAO = new PessoaDAOImpl();
//        criacao = new Date();
//        pessoa = pessoaDAO.buscarPessoaPorNome("Leonardo Henrique Paschoarelli Ribeiro");
//        reserva = reservaDAO.buscarReservaPorPessoa("Leonardo Henrique Paschoarelli Ribeiro");
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//    }
//
//    @Before
//    public void setUp() {
//    }
//
//    @After
//    public void tearDown() {
//    }
//
//    @Test
//    public void persistIfNotExists() {
//        Reserva r = reservaDAO.buscarReservaPorPessoa(pessoa.getNome());
//        if (r == null) {
//            Reserva rsv = new Reserva(pessoa, criacao);
//            r = reservaDAO.merge(rsv);
//        }
//        Assert.assertNotNull(r);
//    }
//
//    @Test
//    public void findAllReservas() {
//        Assert.assertFalse(reservaDAO.listAll().isEmpty());
//    }
//
//    @Test
//    public void countReservas() {
//        Assert.assertNotEquals(0, reservaDAO.count());
//    }
//
//    @Test
//    public void testRecoverReserva() {
//        Reserva e = reservaDAO.listAll().get(0);
//        Reserva recovered = reservaDAO.recover(Reserva.class, e.getId());
//        Assert.assertNotNull(recovered);
//        Assert.assertEquals(e, recovered);
//    }
//
//    @Test
//    public void testFindById() {
//        Reserva e = reservaDAO.listAll().get(0);
//        Reserva finded = reservaDAO.findById(e.getId());
//        Assert.assertNotNull(finded);
//        Assert.assertEquals(e, finded);
//    }
//
//    @Test
//    public void testBuscarReservaPorPessoa() {
//        Assert.assertNotNull(reservaDAO.buscarReservaPorPessoa(pessoa.getNome()));
//    }
//}
