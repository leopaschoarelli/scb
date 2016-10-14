package br.com.gori.scb.dao.impl;

import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.dao.inter.PaisDAO;
import br.com.gori.scb.entidade.Pais;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author Leonardo
 */
public class PaisDAOImpl extends AbstractDAO<Pais> implements PaisDAO {

    public PaisDAOImpl() {
        super(Pais.class);
    }

    @Override
    public List<Pais> listarPaisPorNome(String nome) {
        Query q = getEntityManager().createNamedQuery("Pais.findByNome", Pais.class);
        q.setParameter("nome", nome);
        return q.getResultList();
    }

    @Override
    public Pais buscarPaisPorNome(String nome) {
        Query q = getEntityManager().createNamedQuery("Pais.findByNome", Pais.class);
        q.setParameter("nome", nome);
        List<Pais> paises = q.getResultList();
        if (paises.isEmpty()) {
            return null;
        }
        if (paises.size() >= 1) {
            return paises.get(0);
        } else {
            throw new NonUniqueResultException();
        }
    }
}
