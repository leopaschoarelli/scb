package br.com.gori.scb.dao.impl;

import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.dao.inter.PublicacaoDAO;
import br.com.gori.scb.entidade.Autor;
import br.com.gori.scb.entidade.Categoria;
import br.com.gori.scb.entidade.Cidade;
import br.com.gori.scb.entidade.Editora;
import br.com.gori.scb.entidade.Exemplar;
import br.com.gori.scb.entidade.Publicacao;
import br.com.gori.scb.entidade.TipoAutor;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author Leonardo
 */
public class PublicacaoDAOImpl extends AbstractDAO<Publicacao> implements PublicacaoDAO {

    public PublicacaoDAOImpl() {
        super(Publicacao.class);
    }

    @Override
    public Exemplar getMaxNumExe(Long id) {
        String sql = "select b.* from publicacao a, exemplar b "
                + " where b.publicacao_id = a.id "
                + "and b.publicacao_id = :parte "
                + "order by numexe desc "
                + "limit 1";
        Query q = getEntityManager().createNativeQuery(sql, Exemplar.class);
        q.setParameter("parte", id);
        Exemplar exem = (Exemplar) q.getSingleResult();
        return exem;
    }

    public List<Publicacao> getPublicacoes(String titulo) {
        String sql;
        Boolean caracter;
        if (titulo == null || " ".equals(titulo)) {
            sql = "select p.* from publicacao p";
            caracter = false;
        } else {
            sql = "select p.* from publicacao p where lower(p.titulo) like :parte";
            caracter = true;
        }
        Query q = getEntityManager().createNativeQuery(sql, Publicacao.class);
        if (caracter) {
            q.setParameter("parte", "%" + titulo + "%");
        }
        q.setMaxResults(20);
        return q.getResultList();
    }

    @Override
    public List<Editora> getEditoras(String nome) {
        String sql = "select e.* from editora e where lower(e.nome) like :parte";
        Query q = getEntityManager().createNativeQuery(sql, Editora.class);
        q.setParameter("parte", "%" + nome + "%");
        q.setMaxResults(MAX_RESULTS_QUERY);
        return q.getResultList();
    }

    @Override
    public List<Cidade> getCidades(String nome) {
        String sql = "select c.* from cidade c where lower(c.nome) like :parte";
        Query q = getEntityManager().createNativeQuery(sql, Cidade.class);
        q.setParameter("parte", "%" + nome + "%");
        q.setMaxResults(MAX_RESULTS_QUERY);
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
    public List<Autor> getAutores(String nome) {
        String sql = "select a.* from autor a where lower(a.nome) like :parte";
        Query q = getEntityManager().createNativeQuery(sql, Autor.class);
        q.setParameter("parte", "%" + nome + "%");
        q.setMaxResults(MAX_RESULTS_QUERY);
        return q.getResultList();
    }

    @Override
    public List<TipoAutor> getTipoAutores(String descricao) {
        String sql = "select t.* from tipoautor t where lower(t.descricao) like :parte";
        Query q = getEntityManager().createNativeQuery(sql, TipoAutor.class);
        q.setParameter("parte", "%" + descricao + "%");
        q.setMaxResults(MAX_RESULTS_QUERY);
        return q.getResultList();
    }

    @Override
    public List<Publicacao> listarPublicacaoPorTitulo(String titulo) {
        Query q = getEntityManager().createNamedQuery("Publicacao.findByNome", Publicacao.class);
        q.setParameter("titulo", titulo);
        return q.getResultList();
    }

    @Override
    public List<Publicacao> listarPublicacaoPorSubtitulo(String subtitulo) {
        Query q = getEntityManager().createNamedQuery("Publicacao.findByNome", Publicacao.class);
        q.setParameter("subtitulo", subtitulo);
        return q.getResultList();
    }

    @Override
    public Publicacao buscarPublicacaoPorTitulo(String titulo) {
        Query q = getEntityManager().createNamedQuery("Publicacao.findByTitulo", Publicacao.class);
        q.setParameter("titulo", titulo);
        List<Publicacao> publicacoes = q.getResultList();
        if (publicacoes.isEmpty()) {
            return null;
        }
        if (publicacoes.size() >= 1) {
            return publicacoes.get(0);
        } else {
            throw new NonUniqueResultException();
        }
    }

    public List<Publicacao> listarFiltros(String titulo, String autor, String descricao) {
        String sql = ""
                + "select * from publicacao a, categoria b, autoria c, publicacao_autoria d, autor e "
                + "where a.categoria_id = b.id "
                + "and d.publicacao_id = a.id "
                + "and d.autoria_id = c.id "
                + "and c.autor_id = e.id ";
        if (!"".equals(titulo)) {
            sql = sql + "and lower(a.titulo) like :titulo ";
        }
        if (!"".equals(autor)) {
            sql = sql + "and lower(e.nome) like :autor ";
        }
        if (!"".equals(descricao)) {
            sql = sql + "and lower(b.descricao) like :categ ";
        }
        System.out.println("SQL: "+sql);
        Query q = getEntityManager().createNativeQuery(sql, Publicacao.class);
        if (!"".equals(titulo)) {
            q.setParameter("titulo", "%" + titulo + "%");
        }
        if (!"".equals(autor)) {
            q.setParameter("autor", "%" + autor + "%");
        }
        if (!"".equals(descricao)) {
            q.setParameter("categ", "%" + descricao + "%");
        }

        return q.getResultList();
    }
}
