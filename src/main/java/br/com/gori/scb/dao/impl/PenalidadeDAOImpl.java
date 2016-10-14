package br.com.gori.scb.dao.impl;

import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.dao.inter.PenalidadeDAO;
import br.com.gori.scb.entidade.Penalidade;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author Leonardo
 */
public class PenalidadeDAOImpl extends AbstractDAO<Penalidade> implements PenalidadeDAO {

    public PenalidadeDAOImpl() {
        super(Penalidade.class);
    }

    @Override
    public List<Penalidade> listarPenalidadePorDescricao(String descricao) {
        Query q = getEntityManager().createNamedQuery("Penalidade.findByDescricao", Penalidade.class);
        q.setParameter("descricao", descricao);
        return q.getResultList();
    }

    @Override
    public Penalidade buscarPenalidadePorDescricao(String descricao) {
        Query q = getEntityManager().createNamedQuery("Penalidade.findByDescricao", Penalidade.class);
        q.setParameter("descricao", descricao);
        List<Penalidade> penalidades = q.getResultList();
        if (penalidades.isEmpty()) {
            return null;
        }
        if (penalidades.size() >= 1) {
            return penalidades.get(0);
        } else {
            throw new NonUniqueResultException();
        }
    }
}
