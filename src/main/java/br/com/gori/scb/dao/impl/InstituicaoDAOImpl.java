package br.com.gori.scb.dao.impl;

import br.com.gori.scb.connection.EntityManagerProducer;
import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.dao.inter.InstituicaoDAO;
import br.com.gori.scb.entidade.Instituicao;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author Leonardo
 */
public class InstituicaoDAOImpl extends AbstractDAO<Instituicao> implements InstituicaoDAO {

    public InstituicaoDAOImpl() {
        super(Instituicao.class);
    }

    @Override
    public List<Instituicao> listarInstituicaoPorNomeFantasia(String nomeFantasia) {
        Query q = EntityManagerProducer.getEntityManager().createNamedQuery("Instituicao.findByNomeFantasia", Instituicao.class);
        q.setParameter("nomeFantasia", nomeFantasia);
        return q.getResultList();
    }

    @Override
    public Instituicao buscarInstituicaoPorNomeFantasia(String nomeFantasia) {
        Query q = EntityManagerProducer.getEntityManager().createNamedQuery("Instituicao.findByNomeFantasia", Instituicao.class);
        q.setParameter("nomeFantasia", nomeFantasia);
        List<Instituicao> instituicoes = q.getResultList();
        if (instituicoes.isEmpty()) {
            return null;
        }
        if (instituicoes.size() >= 1) {
            return instituicoes.get(0);
        } else {
            throw new NonUniqueResultException();
        }
    }
}
