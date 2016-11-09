package br.com.gori.scb.dao.impl;

import br.com.gori.scb.connection.EntityManagerProducer;
import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.dao.inter.TipoPessoaDAO;
import br.com.gori.scb.entidade.TipoPessoa;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author Leonardo
 */
public class TipoPessoaDAOImpl extends AbstractDAO<TipoPessoa> implements TipoPessoaDAO {

    public TipoPessoaDAOImpl() {
        super(TipoPessoa.class);
    }

    @Override
    public List<TipoPessoa> listarTipoPessoaPorDescricao(String descricao) {
        Query q = EntityManagerProducer.getEntityManager().createNamedQuery("TipoPessoa.findByDescricao", TipoPessoa.class);
        q.setParameter("descricao", descricao);
        return q.getResultList();
    }

    @Override
    public TipoPessoa buscarTipoPessoaPorDescricao(String descricao) {
        Query q = EntityManagerProducer.getEntityManager().createNamedQuery("TipoPessoa.findByDescricao", TipoPessoa.class);
        q.setParameter("descricao", descricao);
        List<TipoPessoa> tipoPessoas = q.getResultList();
        if (tipoPessoas.isEmpty()) {
            return null;
        }
        if (tipoPessoas.size() >= 1) {
            return tipoPessoas.get(0);
        } else {
            throw new NonUniqueResultException();
        }
    }
}
