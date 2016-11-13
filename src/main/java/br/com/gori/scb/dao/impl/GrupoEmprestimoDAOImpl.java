package br.com.gori.scb.dao.impl;

import br.com.gori.scb.connection.EntityManagerProducer;
import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.dao.inter.GrupoEmprestimoDAO;
import br.com.gori.scb.entidade.Exemplar;
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
        Query q = EntityManagerProducer.getEntityManager().createNativeQuery(sql, TipoPessoa.class);
        q.setParameter("parte", "%" + descricao + "%");
        q.setMaxResults(MAX_RESULTS_QUERY);
        return q.getResultList();
    }

    public GrupoEmprestimo getPublicacaoTpPessoa(Publicacao publicacao, Exemplar exemplar) {
        String sql = "select a.* from grupoemprestimo a, grupo_pessoa b, grupo_publicacao c, tipopessoa d, "
                + "pessoa e, publicacao f, exemplar g "
                + "where a.id = b.grupoemprestimo_id "
                + "and a.id = c.grupoemprestimo_id "
                + "and b.tipopessoa_id = d.id "
                + "and e.tipopessoa_id = d.id "
                + "and c.publicacao_id = f.id "
                + "and f.id = g.publicacao_id ";
        if (publicacao != null) {
            sql = sql + "and f.id = :publicacao ";
        }
        if (exemplar != null) {
            sql = sql + "and g.publicacao_id = :exemplar ";
        }
        System.out.println("SQL!!!: " + sql);
        Query q = EntityManagerProducer.getEntityManager().createNativeQuery(sql, GrupoEmprestimo.class);
        if (publicacao != null) {
            q.setParameter("publicacao", publicacao.getId());
        }
        if (exemplar != null) {
            q.setParameter("exemplar", exemplar.getPublicacao().getId());
        }
        if (!q.getResultList().isEmpty()) {
            GrupoEmprestimo gp = (GrupoEmprestimo) q.getSingleResult();
            return gp;
        } else {
            return null;
        }
    }

    @Override
    public List<Publicacao> getPublicacoes(String titulo) {
        String sql = "select p.* from publicacao p where lower(p.titulo) like :parte";
        Query q = EntityManagerProducer.getEntityManager().createNativeQuery(sql, Publicacao.class
        );
        q.setParameter("parte", "%" + titulo + "%");
        q.setMaxResults(MAX_RESULTS_QUERY);
        return q.getResultList();
    }

    @Override
    public List<GrupoEmprestimo> listarGrupoPorNome(String nome) {
        Query q = EntityManagerProducer.getEntityManager().createNamedQuery("GrupoEmprestimo.findByNome", GrupoEmprestimo.class
        );
        q.setParameter("nome", nome);
        return q.getResultList();
    }

    @Override
    public GrupoEmprestimo
            buscarGrupoPorNome(String nome) {
        Query q = EntityManagerProducer.getEntityManager().createNamedQuery("GrupoEmprestimo.findByNome", GrupoEmprestimo.class
        );
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
