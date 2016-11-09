package br.com.gori.scb.dao.impl;

import br.com.gori.scb.connection.EntityManagerProducer;
import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.dao.inter.UserDAO;
import br.com.gori.scb.entidade.User;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
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
        Query q = EntityManagerProducer.getEntityManager().createNamedQuery("User.findByUsername", User.class);
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
        Query q = EntityManagerProducer.getEntityManager().createNamedQuery("User.findByCredentials", User.class);
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

    public User getLogin(String nome) {
        String sql;
        sql = "select u.* from users u where lower(u.username) like :parte";
        Query q = EntityManagerProducer.getEntityManager().createNativeQuery(sql, User.class);
        q.setParameter("parte", "%" + nome + "%");
        q.setMaxResults(MAX_RESULTS_QUERY);
        return (User) q.getSingleResult();
    }

}
