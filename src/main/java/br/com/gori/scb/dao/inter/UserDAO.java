package br.com.gori.scb.dao.inter;

import br.com.gori.scb.entidade.User;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Leonardo
 */
public interface UserDAO {

    public User findByUsername(String username);

    public User findByCredentials(String username, String password);
    
}
