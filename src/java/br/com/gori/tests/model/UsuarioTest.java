package br.com.gori.tests.model;

import br.com.gori.scb.dao.impl.UsuarioDAOImpl;
import br.com.gori.scb.entidade.Usuario;
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

    private static UsuarioDAOImpl usuarioDAO;
    private static Usuario usuario;

    public UsuarioTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        usuarioDAO = new UsuarioDAOImpl();
        usuario = new Usuario("leo95h", "admin", true);
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
        Usuario u = usuarioDAO.buscarUsuarioPorLogin(usuario.getLogin());
        if (u == null) {
            u = usuarioDAO.merge(usuario);
        }
        Assert.assertNotNull(u);
    }

    @Test
    public void findAllUsuarios() {
        List<Usuario> usuarios = new ArrayList<Usuario>();
        usuarios = usuarioDAO.listAll();
        Assert.assertFalse(usuarios.isEmpty());
    }

    @Test
    public void countUsuarios() {
        int value = usuarioDAO.count();
        Assert.assertFalse(value == 0);
    }

    @Test
    public void testRecoverUsuario() {
        Usuario u = usuarioDAO.listAll().get(0);
        Usuario recovered = usuarioDAO.recover(Usuario.class, u.getId());
        Assert.assertNotNull(recovered);
        Assert.assertEquals(u, recovered);
    }

    @Test
    public void testFindById() {
        Usuario u = usuarioDAO.listAll().get(0);
        Usuario finded = usuarioDAO.findById(u.getId());
        Assert.assertNotNull(finded);
        Assert.assertEquals(u, finded);
    }

    @Test
    public void testBuscarUsuarioPorLogin() {
        Assert.assertNotNull(usuarioDAO.buscarUsuarioPorLogin(usuario.getLogin()));
    }
}
