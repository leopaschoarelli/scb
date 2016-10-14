package br.com.gori.scb.dao.inter;

import br.com.gori.scb.entidade.Role;

import java.util.List;

/**
 *
 * @author Leonardo
 */
public interface RoleDAO {

    public Role findRoleByName(String name);
    public List<Role> getRoles(String nome);
}
