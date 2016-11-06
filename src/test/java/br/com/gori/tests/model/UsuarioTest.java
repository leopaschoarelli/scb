package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.UserDAOImpl;
import br.com.gori.scb.entidade.User;
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
public class UsuarioTest {

    private static UserDAOImpl usuarioDAO;
    private static User usuario;

    public UsuarioTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        usuarioDAO = new UserDAOImpl();
        usuario = usuarioDAO.findByUsername("admin");
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
        User u = usuarioDAO.findByUsername(usuario.getUsername());
        if (u == null) {
            usuario = new User("admin", "admin", true);
            u = usuarioDAO.merge(usuario);
        }
        Assert.assertNotNull(u);
    }

    @Test
    public void findAllUsers() {
        Assert.assertFalse(usuarioDAO.listAll().isEmpty());
    }

    @Test
    public void countUsers() {
        Assert.assertNotEquals(0, usuarioDAO.count());
    }

    @Test
    public void testRecoverUser() {
        User u = usuarioDAO.listAll().get(0);
        User recovered = usuarioDAO.recover(User.class, u.getId());
        Assert.assertNotNull(recovered);
        Assert.assertEquals(u, recovered);
    }

    @Test
    public void testFindById() {
        User u = usuarioDAO.listAll().get(0);
        User finded = usuarioDAO.findById(u.getId());
        Assert.assertNotNull(finded);
        Assert.assertEquals(u, finded);
    }

    @Test
    public void testBuscarUserPorLogin() {
        Assert.assertNotNull(usuarioDAO.findByUsername(usuario.getUsername()));
    }
}
