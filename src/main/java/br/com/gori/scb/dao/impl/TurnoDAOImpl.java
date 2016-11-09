package br.com.gori.scb.dao.impl;

import br.com.gori.scb.connection.EntityManagerProducer;
import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.dao.inter.TurnoDAO;
import br.com.gori.scb.entidade.Turno;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author Leonardo
 */
public class TurnoDAOImpl extends AbstractDAO<Turno> implements TurnoDAO {

    public TurnoDAOImpl() {
        super(Turno.class);
    }

    @Override
    public List<Turno> listarTurnoPorDescricao(String descricao) {
        Query q = EntityManagerProducer.getEntityManager().createNamedQuery("Turno.findByDescricao", Turno.class);
        q.setParameter("descricao", descricao);
        return q.getResultList();
    }

    @Override
    public Turno buscarTurnoPorDescricao(String descricao) {
        Query q = EntityManagerProducer.getEntityManager().createNamedQuery("Turno.findByDescricao", Turno.class);
        q.setParameter("descricao", descricao);
        List<Turno> turnos = q.getResultList();
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
