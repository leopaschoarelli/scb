//package br.com.gori.tests.model;
//
//import br.com.gori.scb.dao.impl.CategoriaDAOImpl;
//import br.com.gori.scb.dao.impl.CidadeDAOImpl;
//import br.com.gori.scb.dao.impl.EditoraDAOImpl;
//import br.com.gori.scb.dao.impl.EstadoDAOImpl;
//import br.com.gori.scb.dao.impl.ItemReservaDAOImpl;
//import br.com.gori.scb.dao.impl.PaisDAOImpl;
//import br.com.gori.scb.dao.impl.PublicacaoDAOImpl;
//import br.com.gori.scb.entidade.Categoria;
//import br.com.gori.scb.entidade.Cidade;
//import br.com.gori.scb.entidade.Editora;
//import br.com.gori.scb.entidade.Estado;
//import br.com.gori.scb.entidade.ItemReserva;
//import br.com.gori.scb.entidade.Pais;
//import br.com.gori.scb.entidade.Publicacao;
//import br.com.gori.scb.entidade.util.TipoPublicacao;
//import java.util.ArrayList;
//import java.util.Calendar;
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
//public class ItemReservaTest {
//
//    private static ItemReservaDAOImpl itemReservaDAO;
//    private static ItemReserva itemReserva;
//    private static PublicacaoDAOImpl publicacaoDAO;
//    private static Publicacao publicacao;
//    private static CategoriaDAOImpl categoriaDAO;
//    private static Categoria categoria;
//    private static EditoraDAOImpl editoraDAO;
//    private static Editora editora;
//    private static Date criacao;
//    private static Calendar previsoes;
//    private static Date previsao;
//    private static CidadeDAOImpl cidadeDAO;
//    private static Cidade cidade;
//    private static EstadoDAOImpl estadoDAO;
//    private static Estado estado;
//    private static PaisDAOImpl paisDAO;
//    private static Pais pais;
//
//    public ItemReservaTest() {
//    }
//
//    @BeforeClass
//    public static void setUpClass() {
//        itemReservaDAO = new ItemReservaDAOImpl();
//        publicacaoDAO = new PublicacaoDAOImpl();
//        categoriaDAO = new CategoriaDAOImpl();
//        editoraDAO = new EditoraDAOImpl();
//        cidadeDAO = new CidadeDAOImpl();
//        estadoDAO = new EstadoDAOImpl();
//        paisDAO = new PaisDAOImpl();
//        criacao = new Date();
//        previsoes = Calendar.getInstance();
//        publicacao = publicacaoDAO.buscarPublicacaoPorTitulo("Teste");
//        if (publicacao == null) {
//            editora = editoraDAO.buscarEditoraPorNome("Abril");
//            if (editora == null) {
//                editora = new Editora("Abril");
//                editora = editoraDAO.merge(editora);
//            }
//            categoria = categoriaDAO.buscarCategoriaPorDescricao("Infantil");
//            if (categoria == null) {
//                categoria = new Categoria("Infantil", null);
//                categoria = categoriaDAO.merge(categoria);
//            }
//            cidade = cidadeDAO.buscarCidadePorNome("Mandaguari");
//            if (cidade == null) {
//                estado = estadoDAO.buscarEstadoPorNome("Paraná");
//                if (estado == null) {
//                    pais = paisDAO.buscarPaisPorNome("Brasil");
//                    if (pais == null) {
//                        pais = new Pais("Brasil", "BRA");
//                        pais = paisDAO.merge(pais);
//                    }
//                    estado = new Estado("Paraná", "PR", pais);
//                    estado = estadoDAO.merge(estado);
//                }
//                cidade = new Cidade("Mandaguari", estado);
//                cidade = cidadeDAO.merge(cidade);
//            }
////            publicacao = new Publicacao("Teste", "Testando", 2016, 300, cidade, "T342L", "3123344", "423", "1º Edição", TipoPublicacao.OBRA, editora, categoria, "423423", null);
//        }
//        previsao = previsoes.getTime();
////        itemReserva = new ItemReserva(previsao, false, publicacao);
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
//        ItemReserva i = itemReservaDAO.buscarItemReservaPorTitulo(publicacao.getTitulo());
//        if (i == null) {
//            i = itemReservaDAO.merge(itemReserva);
//        }
//        Assert.assertNotNull(i);
//    }
//
//    @Test
//    public void findAllItemReservas() {
//        List<ItemReserva> itensReservas = new ArrayList<ItemReserva>();
//        itensReservas = itemReservaDAO.listAll();
//        Assert.assertFalse(itensReservas.isEmpty());
//    }
//
//    @Test
//    public void countItensReservas() {
//        int value = itemReservaDAO.count();
//        Assert.assertFalse(value == 0);
//    }
//
//    @Test
//    public void testRecoverItemReserva() {
//        ItemReserva i = itemReservaDAO.listAll().get(0);
//        ItemReserva recovered = itemReservaDAO.recover(ItemReserva.class, i.getId());
//        Assert.assertNotNull(recovered);
//        Assert.assertEquals(i, recovered);
//    }
//
//    @Test
//    public void testFindById() {
//        ItemReserva i = itemReservaDAO.listAll().get(0);
//        ItemReserva finded = itemReservaDAO.findById(i.getId());
//        Assert.assertNotNull(finded);
//        Assert.assertEquals(i, finded);
//    }
//
//    @Test
//    public void testBuscarItemPorTitulo() {
//        Assert.assertNotNull(itemReservaDAO.buscarItemReservaPorTitulo(publicacao.getTitulo()));
//    }
//
//}
