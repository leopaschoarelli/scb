package br.com.gori.scb.dao.impl;

import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.dao.inter.EnderecoDAO;
import br.com.gori.scb.entidade.Endereco;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author Leonardo
 */
public class EnderecoDAOImpl extends AbstractDAO<Endereco> implements EnderecoDAO {

    public EnderecoDAOImpl() {
        super(Endereco.class);
    }

    @Override
    public List<Endereco> listarEnderecoPorLogradouro(String logradouro) {
        Query q = getEntityManager().createNamedQuery("Endereco.findByLogradouro", Endereco.class);
        q.setParameter("logradouro", logradouro);
        return q.getResultList();
    }

    @Override
    public Endereco buscarEnderecoPorLogradouro(String logradouro) {
        Query q = getEntityManager().createNamedQuery("Endereco.findByLogradouro", Endereco.class);
        q.setParameter("logradouro", logradouro);
        List<Endereco> logradouros = q.getResultList();
        if (logradouros.isEmpty()) {
            return null;
        }
        if (logradouros.size() >= 1) {
            return logradouros.get(0);
        } else {
            throw new NonUniqueResultException();
        }
    }
}
