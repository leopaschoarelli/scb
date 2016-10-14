package br.com.gori.scb.dao.impl;

import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.dao.inter.CidadeDAO;
import br.com.gori.scb.entidade.Cidade;
import br.com.gori.scb.entidade.Estado;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author Leonardo
 */
public class CidadeDAOImpl extends AbstractDAO<Cidade> implements CidadeDAO {

    public CidadeDAOImpl() {
        super(Cidade.class);
    }

    @Override
    public List<Estado> getEstados(String nome) {
        String sql;
        Boolean caracter;
        if (nome == null || " ".equals(nome)) {
            sql = "select e.* from estado e";
            caracter = false;
        } else {
            sql = "select e.* from estado e where lower(e.nome) like :parte";
            caracter = true;
        }
        Query q = getEntityManager().createNativeQuery(sql, Estado.class);
        if (caracter) q.setParameter("parte", "%" + nome + "%");
        q.setMaxResults(MAX_RESULTS_QUERY);
        return q.getResultList();
    }

    @Override
    public List<Cidade> listarCidadePorNome(String nome) {
        Query q = getEntityManager().createNamedQuery("Cidade.findByNome", Cidade.class);
        q.setParameter("nome", nome);
        return q.getResultList();
    }

    @Override
    public Cidade buscarCidadePorNome(String nome) {
        Query q = getEntityManager().createNamedQuery("Cidade.findByNome", Cidade.class);
        q.setParameter("nome", nome);
        List<Cidade> autores = q.getResultList();
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
