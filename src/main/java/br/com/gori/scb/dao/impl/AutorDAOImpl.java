package br.com.gori.scb.dao.impl;

import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.dao.inter.AutorDAO;
import br.com.gori.scb.entidade.Autor;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author Leonardo
 */
public class AutorDAOImpl extends AbstractDAO<Autor> implements AutorDAO {

    public AutorDAOImpl() {
        super(Autor.class);
    }

    @Override
    public List<Autor> listarAutorPorNome(String nome) {
        Query q = getEntityManager().createNamedQuery("Autor.findByNome", Autor.class);
        q.setParameter("nome", nome);
        return q.getResultList();
    }
    
    @Override
    public Autor buscarAutorPorNome(String nome) {
        Query q = getEntityManager().createNamedQuery("Autor.findByNome", Autor.class);
        q.setParameter("nome", nome);
        List<Autor> autores = q.getResultList();
        if (autores.isEmpty()) {
            return null;
        }
        if (autores.size() >= 1) {
            return autores.get(0);
        } else {
            throw new NonUniqueResultException();
        }
    }
    
}
