package br.com.gori.scb.dao.impl;

import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.dao.inter.TipoAutorDAO;
import br.com.gori.scb.entidade.TipoAutor;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author Leonardo
 */
public class TipoAutorDAOImpl extends AbstractDAO<TipoAutor> implements TipoAutorDAO {

    public TipoAutorDAOImpl() {
        super(TipoAutor.class);
    }

    @Override
    public List<TipoAutor> listarTipoAutorPorDescricao(String descricao) {
        Query q = getEntityManager().createNamedQuery("TipoAutor.findByDescricao", TipoAutor.class);
        q.setParameter("descricao", descricao);
        return q.getResultList();
    }

    @Override
    public TipoAutor buscarTipoAutorPorDescricao(String descricao) {
        Query q = getEntityManager().createNamedQuery("TipoAutor.findByDescricao", TipoAutor.class);
        q.setParameter("descricao", descricao);
        List<TipoAutor> tipos = q.getResultList();
        if (tipos.isEmpty()) {
            return null;
        }
        if (tipos.size() >= 1) {
            return tipos.get(0);
        } else {
            throw new NonUniqueResultException();
        }
    }

    
}
