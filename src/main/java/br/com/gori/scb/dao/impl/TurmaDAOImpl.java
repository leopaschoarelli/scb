package br.com.gori.scb.dao.impl;

import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.dao.inter.TurmaDAO;
import br.com.gori.scb.entidade.Turma;
import br.com.gori.scb.entidade.Turno;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author Leonardo
 */
public class TurmaDAOImpl extends AbstractDAO<Turma> implements TurmaDAO {

    public TurmaDAOImpl() {
        super(Turma.class);
    }

    @Override
    public List<Turno> getTurnos(String descricao) {
        String sql = "select t.* from turno t where lower(t.descricao) like :parte";
        Query q = getEntityManager().createNativeQuery(sql, Turno.class);
        q.setParameter("parte", "%" + descricao + "%");
        q.setMaxResults(MAX_RESULTS_QUERY);
        return q.getResultList();
    }

    @Override
    public List<Turma> listarTurmaPorDescricao(String descricao) {
        Query q = getEntityManager().createNamedQuery("Turma.findByDescricao", Turma.class);
        q.setParameter("descricao", descricao);
        return q.getResultList();
    }

    @Override
    public Turma buscarTurmaPorDescricao(String descricao) {
        Query q = getEntityManager().createNamedQuery("Turma.findByDescricao", Turma.class);
        q.setParameter("descricao", descricao);
        List<Turma> turnos = q.getResultList();
        if (turnos.isEmpty()) {
            return null;
        }
        if (turnos.size() >= 1) {
            return turnos.get(0);
        } else {
            throw new NonUniqueResultException();
        }
    }
}
