package br.com.gori.scb.dao.impl;

import br.com.gori.scb.dao.AbstractDAO;
import static br.com.gori.scb.dao.AbstractDAO.MAX_RESULTS_QUERY;
import br.com.gori.scb.dao.inter.ConfiguracaoEmprestimoDAO;
import br.com.gori.scb.entidade.ConfiguracaoEmprestimo;
import br.com.gori.scb.entidade.Penalidade;
import br.com.gori.scb.entidade.TipoPessoa;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author Leonardo
 */
public class ConfiguracaoEmprestimoDAOImpl extends AbstractDAO<ConfiguracaoEmprestimo> implements ConfiguracaoEmprestimoDAO {

    public ConfiguracaoEmprestimoDAOImpl() {
        super(ConfiguracaoEmprestimo.class);
    }

    @Override
    public List<Penalidade> getPenalidades(String descricao) {
        String sql = "select p.* from penalidade p where lower(p.descricao) like :parte";
        Query q = getEntityManager().createNativeQuery(sql, Penalidade.class);
        q.setParameter("parte", "%" + descricao + "%");
        q.setMaxResults(MAX_RESULTS_QUERY);
        return q.getResultList();
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
    public List<ConfiguracaoEmprestimo> listarConfiguracaoPorDias(Integer dias) {
        Query q = getEntityManager().createNamedQuery("ConfiguracaoEmprestimo.findByDias", ConfiguracaoEmprestimo.class);
        q.setParameter("dias", dias);
        return q.getResultList();
    }

    @Override
    public ConfiguracaoEmprestimo buscarConfiguracaoPorDias(Integer dias) {
        Query q = getEntityManager().createNamedQuery("ConfiguracaoEmprestimo.findByDias", ConfiguracaoEmprestimo.class);
        q.setParameter("dias", dias);
        List<ConfiguracaoEmprestimo> configuracoes = q.getResultList();
        if (configuracoes.isEmpty()) {
            return null;
        }
        if (configuracoes.size() >= 1) {
            return configuracoes.get(0);
        } else {
            throw new NonUniqueResultException();
        }
    }

    @Override
    public ConfiguracaoEmprestimo buscarConfiguracaoPorPessoa(String descricao) {
        Query q = getEntityManager().createNamedQuery("ConfiguracaoEmprestimo.findByPessoa", ConfiguracaoEmprestimo.class);
        q.setParameter("descricao", descricao);
        List<ConfiguracaoEmprestimo> configuracoes = q.getResultList();
        if (configuracoes.isEmpty()) {
            return null;
        }
        if (configuracoes.size() >= 1) {
            return configuracoes.get(0);
        } else {
            throw new NonUniqueResultException();
        }
    }

    public ConfiguracaoEmprestimo buscarDiasEmprestimoPessoa(String nome) {
        String sql = "select c.* from pessoa b, configuracaoemprestimo c, tipopessoa d "
                + "where b.tipopessoa_id = d.id "
                + "and d.id = c.tipopessoa_id "
                + "and lower(b.nome) like :parte";
        System.out.println(sql);
        Query q = getEntityManager().createNativeQuery(sql, ConfiguracaoEmprestimo.class);
        q.setParameter("parte", "%" + nome.toLowerCase() + "%");
        ConfiguracaoEmprestimo ce = (ConfiguracaoEmprestimo) q.getSingleResult();
        return ce;
    }
}
