package br.com.gori.scb.dao.impl;

import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.dao.inter.EstadoDAO;
import br.com.gori.scb.entidade.Estado;
import br.com.gori.scb.entidade.Pais;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author Leonardo
 */
public class EstadoDAOImpl extends AbstractDAO<Estado> implements EstadoDAO {

    public EstadoDAOImpl() {
        super(Estado.class);
    }

    @Override
    public List<Pais> getPaises(String nome) {
        String sql = "select p.* from pais p where lower(p.nome) like :parte";
        Query q = getEntityManager().createNativeQuery(sql, Pais.class);
        q.setParameter("parte", "%" + nome + "%");
        q.setMaxResults(MAX_RESULTS_QUERY);
        return q.getResultList();
    }

    @Override
    public List<Estado> listarEstadoPorNome(String nome) {
        Query q = getEntityManager().createNamedQuery("Estado.findByNome", Estado.class);
        q.setParameter("nome", nome);
        return q.getResultList();
    }

    @Override
    public Estado buscarEstadoPorNome(String nome) {
        Query q = getEntityManager().createNamedQuery("Estado.findByNome", Estado.class);
        q.setParameter("nome", nome);
        List<Estado> estados = q.getResultList();
        if (estados.isEmpty()) {
            return null;
        }
        if (estados.size() >= 1) {
            return estados.get(0);
        } else {
            throw new NonUniqueResultException();
        }
    }
}
