package br.com.gori.scb.dao.impl;

import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.dao.inter.GrupoEmprestimoDAO;
import br.com.gori.scb.entidade.GrupoEmprestimo;
import br.com.gori.scb.entidade.Publicacao;
import br.com.gori.scb.entidade.TipoPessoa;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author Leonardo
 */
public class GrupoEmprestimoDAOImpl extends AbstractDAO<GrupoEmprestimo> implements GrupoEmprestimoDAO {

    public GrupoEmprestimoDAOImpl() {
        super(GrupoEmprestimo.class);
    }

    @Override
    public List<TipoPessoa> getTipoPessoas(String descricao) {
        String sql = "select t.* from tipopessoa t where lower(t.descricao) like :parte";
        Query q = getEntityManager().createNativeQuery(sql, TipoPessoa.class);
        q.setParameter("parte", "%" + descricao + "%");
        q.setMaxResults(MAX_RESULTS_QUERY);
        return q.getResultList();
    }

    @Override
    public List<Publicacao> getPublicacoes(String titulo) {
        String sql = "select p.* from publicacao p where lower(p.titulo) like :parte";
        Query q = getEntityManager().createNativeQuery(sql, Publicacao.class);
        q.setParameter("parte", "%" + titulo + "%");
        q.setMaxResults(MAX_RESULTS_QUERY);
        return q.getResultList();
    }

    @Override
    public List<GrupoEmprestimo> listarGrupoPorNome(String nome) {
        Query q = getEntityManager().createNamedQuery("GrupoEmprestimo.findByNome", GrupoEmprestimo.class);
        q.setParameter("nome", nome);
        return q.getResultList();
    }

    @Override
    public GrupoEmprestimo buscarGrupoPorNome(String nome) {
        Query q = getEntityManager().createNamedQuery("GrupoEmprestimo.findByNome", GrupoEmprestimo.class);
        q.setParameter("nome", nome);
        List<GrupoEmprestimo> grupos = q.getResultList();
        if (grupos.isEmpty()) {
            return null;
        }
        if (grupos.size() >= 1) {
            return grupos.get(0);
        } else {
            throw new NonUniqueResultException();
        }
    }
}
