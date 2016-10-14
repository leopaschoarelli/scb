package br.com.gori.scb.dao.impl;

import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.dao.inter.CategoriaDAO;
import br.com.gori.scb.entidade.Categoria;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author Leonardo
 */
public class CategoriaDAOImpl extends AbstractDAO<Categoria> implements CategoriaDAO {

    public CategoriaDAOImpl() {
        super(Categoria.class);
    }
    
    @Override
    public List<Categoria> raizes() {
        String sql = "select c.* from categoria c where c.categsup_id is null";
        Query q = getEntityManager().createNativeQuery(sql, Categoria.class);
        return q.getResultList();
    }

    @Override
    public List<Categoria> getCategorias(String descricao) {
        String sql = "select c.* from categoria c where lower(c.descricao) like :parte";
        Query q = getEntityManager().createNativeQuery(sql, Categoria.class);
        q.setParameter("parte", "%" + descricao + "%");
        q.setMaxResults(MAX_RESULTS_QUERY);
        return q.getResultList();
    }

    @Override
    public List<Categoria> listarCategoriaPorDescricao(String descricao) {
        Query q = getEntityManager().createNamedQuery("Categoria.findByDescricao", Categoria.class);
        q.setParameter("descricao", descricao);
        return q.getResultList();
    }

    @Override
    public Categoria buscarCategoriaPorDescricao(String descricao) {
        Query q = getEntityManager().createNamedQuery("Categoria.findByDescricao", Categoria.class);
        q.setParameter("descricao", descricao);
        List<Categoria> categorias = q.getResultList();
        if (categorias.isEmpty()) {
            return null;
        }
        if (categorias.size() >= 1) {
            return categorias.get(0);
        } else {
            throw new NonUniqueResultException();
        }
    }
}
