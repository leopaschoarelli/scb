package br.com.gori.scb.dao.impl;

import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.dao.inter.EmprestimoDAO;
import br.com.gori.scb.entidade.ConfiguracaoEmprestimo;
import br.com.gori.scb.entidade.Emprestimo;
import br.com.gori.scb.entidade.Exemplar;
import br.com.gori.scb.entidade.ItemEmprestimo;
import br.com.gori.scb.entidade.Pessoa;
import br.com.gori.scb.entidade.Publicacao;
import java.util.Date;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author Leonardo
 */
public class EmprestimoDAOImpl extends AbstractDAO<Emprestimo> implements EmprestimoDAO {

    private final ItemEmprestimoDAOImpl itemEmprestimoDAO;

    public EmprestimoDAOImpl() {
        super(Emprestimo.class);
        itemEmprestimoDAO = new ItemEmprestimoDAOImpl();
    }

    @Override
    public List<Emprestimo> listarEmprestimoPorPessoa(String nome) {
        Query q = getEntityManager().createNamedQuery("Emprestimo.findByPessoa", Emprestimo.class);
        q.setParameter("nome", nome);
        return q.getResultList();
    }

    @Override
    public List<ItemEmprestimo> recuperarEmprestimosPessoa(String nome) {
        String sql = "select b.* from emprestimo a, itememprestimo b, pessoa c, exemplar d\n"
                + "where b.emprestimo_id = a.id "
                + "and a.pessoa_id = c.id "
                + "and b.exemplar_id = d.id "
                + "and b.devolucao is null "
                + "and lower(c.nome) like :parte";
        Query q = getEntityManager().createNativeQuery(sql, ItemEmprestimo.class);
        q.setParameter("parte", "%" + nome.toLowerCase() + "%");
        return q.getResultList();
    }

    @Override
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

    @Override
    public Integer buscarQtdEmprestadaPessoa(String nome) {
        String sql = "select b.* from emprestimo a, itememprestimo b, pessoa c\n"
                + "where b.emprestimo_id = a.id "
                + "and a.pessoa_id = c.id "
                + "and b.devolucao is null "
                + "and b.estadoemprestimo = 'EMPRESTADO' "
                + "and lower(c.nome) like :parte";
        Query q = getEntityManager().createNativeQuery(sql, ItemEmprestimo.class);
        q.setParameter("parte", "%" + nome.toLowerCase() + "%");
        Integer qtdEmprestada = q.getResultList().size();
        System.out.println(qtdEmprestada);
        return qtdEmprestada;
    }

    @Override
    public Emprestimo buscarEmprestimoPorPessoa(String nome) {
        Query q = getEntityManager().createNamedQuery("Emprestimo.findByPessoa", Emprestimo.class);
        q.setParameter("nome", nome);
        List<Emprestimo> emprestimos = q.getResultList();
        if (emprestimos.isEmpty()) {
            return null;
        }
        if (emprestimos.size() >= 1) {
            return emprestimos.get(0);
        } else {
            throw new NonUniqueResultException();
        }
    }

    @Override
    public List<Pessoa> getPessoas(String nome) {
        String sql;
        Boolean caracter;
        if (nome == null || " ".equals(nome)) {
            sql = "select p.* from pessoa p";
            caracter = false;
        } else {
            sql = "select p.* from pessoa p where lower(p.nome) like :parte";
            caracter = true;
        }
        Query q = getEntityManager().createNativeQuery(sql, Pessoa.class);
        if (caracter) {
            q.setParameter("parte", "%" + nome + "%");
        }
        q.setMaxResults(MAX_RESULTS_QUERY);
        return q.getResultList();
    }

    @Override
    public List<Exemplar> getPublicacoes(String tombo) {
        String sql;
        Boolean caracter;
        if (tombo == null || " ".equals(tombo)) {
            sql = "select e.* from exemplar e";
            caracter = false;
        } else {
            sql = "select e.* from publicacao p, exemplar e "
                    + "where e.publicacao_id = p.id "
                    + "and e.estadoexemplar = 'DISPONIVEL'"
                    + "and e.tombo like :parte";
            caracter = true;
        }
        Query q = getEntityManager().createNativeQuery(sql, Exemplar.class);
        if (caracter) {
            q.setParameter("parte", "%" + tombo + "%");
        }
        q.setMaxResults(MAX_RESULTS_QUERY);
        return q.getResultList();
    }

    public List<Exemplar> getAllExemplares(String tombo) {
        String sql;
        Boolean caracter;
        if (tombo == null || " ".equals(tombo)) {
            sql = "select e.* from exemplar e";
            caracter = false;
        } else {
            sql = "select e.* from publicacao p, exemplar e "
                    + "where e.publicacao_id = p.id "
                    + "and e.tombo like :parte";
            caracter = true;
        }
        Query q = getEntityManager().createNativeQuery(sql, Exemplar.class);
        if (caracter) {
            q.setParameter("parte", "%" + tombo + "%");
        }
        q.setMaxResults(MAX_RESULTS_QUERY);
        return q.getResultList();
    }

    @Override
    public List<ItemEmprestimo> listarFiltros(String nomel, String obralit, Date dtInicial, Date dtFinal, Date dtDevolIni, Date dtDevolFinal, Long idPessoa) {
        String sql = ""
                + "select b.* from emprestimo a, itememprestimo b, pessoa c, publicacao d, exemplar e "
                + "where a.id = b.emprestimo_id "
                + "and a.pessoa_id = c.id "
                + "and b.exemplar_id = e.id "
                + "and d.id = e.publicacao_id ";
        if (!"".equals(nomel)) {
            sql = sql + "and lower(c.nome) like :nomel ";
        }
        if (!"".equals(obralit)) {
            sql = sql + "and lower(d.titulo) like :obralit ";
        }
        if (dtInicial != null) {
            sql = sql + "and a.criacao >= :dtInicial ";
        }
        if (dtFinal != null) {
            sql = sql + "and a.cricao <= :dtFinal ";
        }
        if (dtDevolIni != null) {
            sql = sql + "and b.devolucao >= :dtDevolIni ";
        }
        if (dtDevolFinal != null) {
            sql = sql + "and b.devolucao <= :dtDevolFinal ";
        }
        if (idPessoa != null) {
            sql = sql + "and c.id = :idPessoa ";
        }
        Query q = getEntityManager().createNativeQuery(sql, ItemEmprestimo.class);
        if (!"".equals(nomel)) {
            q.setParameter("nomel", "%" + nomel.toLowerCase() + "%");
        }
        if (!"".equals(obralit)) {
            q.setParameter("obralit", "%" + obralit.toLowerCase() + "%");
        }
        if (dtInicial != null) {
            q.setParameter("dtInicial", dtInicial, TemporalType.DATE);
        }
        if (dtInicial != null) {
            q.setParameter("dtFinal", dtFinal, TemporalType.DATE);
        }
        if (dtDevolIni != null) {
            q.setParameter("dtDevolIni", dtDevolIni, TemporalType.DATE);
        }
        if (dtDevolFinal != null) {
            q.setParameter("dtDevolFinal", dtDevolFinal, TemporalType.DATE);
        }
        if (idPessoa != null) {
            q.setParameter("idPessoa", idPessoa);
        }
        return q.getResultList();
    }

    public List<Publicacao> getPublic(String titulo) {
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
    public List<Exemplar> getExemplares(Long id) {
        String sql;
        sql = "select e.* from publicacao p, exemplar e "
                + "where e.publicacao_id = p.id "
                + "and e.estadoexemplar = 'DISPONIVEL'"
                + "and e.publicacao_id = :parte";
        Query q = getEntityManager().createNativeQuery(sql, Exemplar.class);
        q.setParameter("parte", id);
        return q.getResultList();
    }

    public List<Emprestimo> recuperaEmprestimosAbertos(Pessoa pessoa) {
        String sql = "select distinct a.* from emprestimo a, itememprestimo b "
                + "where a.id = b.emprestimo_id "
                + "and b.devolucao is null "
                + "and a.pessoa_id = :parte";
        Query q = getEntityManager().createNativeQuery(sql, Emprestimo.class);
        q.setParameter("parte", pessoa.getId());
        List<Emprestimo> emprestimos = q.getResultList();
        for (Emprestimo emp : emprestimos) {
            emp.getItemEmprestimo().addAll(itemEmprestimoDAO.recuperaItensEmprestimosAbertos(emp));
        }
        return emprestimos;
    }

}
