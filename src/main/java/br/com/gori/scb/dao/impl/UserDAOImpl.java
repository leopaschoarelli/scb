package br.com.gori.scb.dao.impl;

import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.dao.inter.UserDAO;
import br.com.gori.scb.entidade.User;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Leonardo
 */
@Repository
public class UserDAOImpl extends AbstractDAO<User> implements UserDAO {

    public UserDAOImpl() {
        super(User.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public User findByUsername(String username) {
        Query q = getEntityManager().createNamedQuery("User.findByUsername", User.class);
        q.setParameter("username", username);
        List<User> users = q.getResultList();
        if (users.isEmpty()) {
            return null;
        } else if (users.size() == 1) {
            return users.get(0);
        } else {
            throw new NonUniqueResultException(username);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public User findByCredentials(String username, String password) {
        Query q = getEntityManager().createNamedQuery("User.findByCredentials", User.class);
        q.setParameter("username", username);
        q.setParameter("password", password);
        List<User> users = q.getResultList();
        if (users.isEmpty()) {
            return null;
        } else if (users.size() == 1) {
            return users.get(0);
        } else {
            throw new NonUniqueResultException(username);
        }
    }

}
