package br.com.gori.scb.dao.impl;

import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.dao.inter.AutoriaDAO;
import br.com.gori.scb.entidade.Autoria;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author Leonardo
 */
public class AutoriaDAOImpl extends AbstractDAO<Autoria> implements AutoriaDAO {

    public AutoriaDAOImpl() {
        super(Autoria.class);
    }

    @Override
    public Autoria buscarAutoriaPorNome(String nome) {
        Query q = getEntityManager().createNamedQuery("Autoria.findByNome", Autoria.class);
        q.setParameter("nome", nome);
        List<Autoria> autorias = q.getResultList();
        if (autorias.isEmpty()) {
            return null;
        }
        if (autorias.size() >= 1) {
            return autorias.get(0);
        } else {
            throw new NonUniqueResultException();
        }
    }

}
