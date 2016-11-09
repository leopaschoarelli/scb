package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.ItemReservaDAOImpl;
import br.com.gori.scb.dao.impl.PublicacaoDAOImpl;
import br.com.gori.scb.dao.impl.ReservaDAOImpl;
import br.com.gori.scb.entidade.ItemReserva;
import br.com.gori.scb.entidade.Publicacao;
import br.com.gori.scb.entidade.Reserva;
import java.util.Calendar;
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
public class ItemReservaTest {

    private static ItemReservaDAOImpl itemReservaDAO;
    private static ItemReserva itemReserva;
    private static PublicacaoDAOImpl publicacaoDAO;
    private static Publicacao publicacao;
    private static ReservaDAOImpl reservaDAO;
    private static Reserva reserva;
    private static Date criacao;
    private static Calendar previsoes;
    private static Date previsao;

    public ItemReservaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        itemReservaDAO = new ItemReservaDAOImpl();
        publicacaoDAO = new PublicacaoDAOImpl();
        reservaDAO = new ReservaDAOImpl();
        reserva = reservaDAO.buscarReservaPorPessoa("Leonardo Henrique Paschoarelli Ribeiro");
        previsoes = Calendar.getInstance();
        previsao = previsoes.getTime();
        publicacao = publicacaoDAO.buscarPublicacaoPorTitulo("Se7e");
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
        ItemReserva i = itemReservaDAO.buscarItemReservaPorTitulo(publicacao.getTitulo());
        if (i == null) {
            ItemReserva it = new ItemReserva(reserva, previsao, null, false, publicacao);
            i = itemReservaDAO.merge(it);
        }
        Assert.assertNotNull(i);
    }

    @Test
    public void findAllItemReservas() {
        Assert.assertFalse(itemReservaDAO.listAll().isEmpty());
    }

    @Test
    public void countItensReservas() {
        Assert.assertNotEquals(0, itemReservaDAO.count());
    }

    @Test
    public void testRecoverItemReserva() {
        ItemReserva i = itemReservaDAO.listAll().get(0);
        ItemReserva recovered = itemReservaDAO.recover(ItemReserva.class, i.getId());
        Assert.assertNotNull(recovered);
        Assert.assertEquals(i, recovered);
    }

    @Test
    public void testFindById() {
        ItemReserva i = itemReservaDAO.listAll().get(0);
        ItemReserva finded = itemReservaDAO.findById(i.getId());
        Assert.assertNotNull(finded);
        Assert.assertEquals(i, finded);
    }

    @Test
    public void testBuscarItemPorTitulo() {
        Assert.assertNotNull(itemReservaDAO.buscarItemReservaPorTitulo(publicacao.getTitulo()));
    }

}
