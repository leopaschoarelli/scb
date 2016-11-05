//package br.com.gori.tests.model;
//
//import br.com.gori.scb.dao.impl.EmprestimoDAOImpl;
//import br.com.gori.scb.dao.impl.PessoaDAOImpl;
//import br.com.gori.scb.dao.impl.TipoPessoaDAOImpl;
//import br.com.gori.scb.entidade.Emprestimo;
//import br.com.gori.scb.entidade.Pessoa;
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
//public class EmprestimoTest {
//
//    private final EmprestimoDAOImpl emprestimoDAO;
//    private static PessoaDAOImpl pessoaDAO;
//    private static Date criacao;
//    private static Pessoa teste;
//
//    public EmprestimoTest() {
//        emprestimoDAO = new EmprestimoDAOImpl();
//        pessoaDAO = new PessoaDAOImpl();
//    }
//
//    @BeforeClass
//    public static void setUpClass() {
//        criacao = new Date();
//        pessoaDAO = new PessoaDAOImpl();
//        teste = pessoaDAO.buscarPessoaPorNome("Leonardo Henrique Paschoarelli Ribeiro"); 
//    }
//
//    @Test
//    public void persistIfNotExists() {        
//        Emprestimo e = emprestimoDAO.buscarEmprestimoPorPessoa(teste.getNome());
//        if (e == null) {
//            Emprestimo emp = new Emprestimo(teste, criacao);
//            e = emprestimoDAO.merge(emp);
//        }
//        Assert.assertNotNull(e);
//    }
//
//    @Test
//    public void findAllEmprestimos() {
//        Assert.assertFalse(emprestimoDAO.listAll().isEmpty());
//    }
//
//    @Test
//    public void countEmprestimos() {
//        Assert.assertNotEquals(0, emprestimoDAO.count());
//    }
//
//    @Test
//    public void testRecoverEmprestimo() {
//        Emprestimo e = emprestimoDAO.listAll().get(1);
//        Emprestimo recovered = emprestimoDAO.recover(Emprestimo.class, e.getId());
//        Assert.assertNotNull(recovered);
//        Assert.assertEquals(e, recovered);
//    }
//
//    @Test
//    public void testFindById() {
//        Emprestimo e = emprestimoDAO.listAll().get(0);
//        Emprestimo finded = emprestimoDAO.findById(e.getId());
//        Assert.assertNotNull(finded);
//        Assert.assertEquals(e, finded);
//    }
//
//    @Test
//    public void testBuscarEmprestimoPorPessoa() {
//        Assert.assertNotNull(emprestimoDAO.buscarEmprestimoPorPessoa(teste.getNome()));
//    }
//}
