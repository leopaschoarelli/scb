package br.com.gori.scb.dao.impl;

import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.dao.inter.RoleDAO;
import br.com.gori.scb.entidade.Role;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
/**
 *
 * @author Leonardo
 */
public class RoleDAOImpl extends AbstractDAO<Role> implements RoleDAO {

    public RoleDAOImpl() {
        super(Role.class);
    }

    @Override
    public Role findRoleByName(String name) {
        Query q = getEntityManager().createNamedQuery("Role.findRoleByName", Role.class);
        q.setParameter("name", name);
        List<Role> roles = q.getResultList();
        if (roles.isEmpty()) {
            return null;
        } else if (roles.size() == 1) {
            return roles.get(0);
        } else {
            throw new NonUniqueResultException(name);
        }
    }

    @Override
    public List<Role> getRoles(String nome) {
        String sql;
        Boolean caracter;
        if (nome == null || " ".equals(nome)) {
            sql = "select r.* from roles r";
            caracter = false;
        } else {
            sql = "select r.* from roles r where lower(r.name) like :parte";
            caracter = true;
        }
        Query q = getEntityManager().createNativeQuery(sql, Role.class);
        if (caracter) {
            q.setParameter("parte", "%" + nome + "%");
        }
        q.setMaxResults(MAX_RESULTS_QUERY);
        return q.getResultList();
    }

}
