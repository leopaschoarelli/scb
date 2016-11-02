package br.com.gori.scb.dao.impl;

import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.dao.inter.ItemEmprestimoDAO;
import br.com.gori.scb.entidade.Emprestimo;
import br.com.gori.scb.entidade.ItemEmprestimo;
import br.com.gori.scb.entidade.Pessoa;
import java.util.Date;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author Leonardo
 */
public class ItemEmprestimoDAOImpl extends AbstractDAO<ItemEmprestimo> implements ItemEmprestimoDAO {

    public ItemEmprestimoDAOImpl() {
        super(ItemEmprestimo.class);
    }

    @Override
    public List<ItemEmprestimo> listarItemEmprestimoPorPessoa(String nome) {
        Query q = getEntityManager().createNamedQuery("ItemEmprestimo.findByPessoa", ItemEmprestimo.class);
        q.setParameter("nome", nome);
        return q.getResultList();
    }

    @Override
    public ItemEmprestimo buscarItemEmprestimoPorPessoa(String nome) {
        Query q = getEntityManager().createNamedQuery("ItemEmprestimo.findByPessoa", ItemEmprestimo.class);
        q.setParameter("nome", nome);
        List<ItemEmprestimo> itens = q.getResultList();
        if (itens.isEmpty()) {
            return null;
        }
        if (itens.size() >= 1) {
            return itens.get(0);
        } else {
            throw new NonUniqueResultException();
        }
    }

    @Override
    public List<ItemEmprestimo> listarFiltros(String nomel, String obralit, Date dtEmp) {
//        Query q = getEntityManager().createNamedQuery("ItemEmprestimo.findByPessoa", ItemEmprestimo.class);
//        q.setParameter("nome","%"+nome+"%");
//        q.setParameter("obra","%"+obra+"%");
//        return q.getResultList();
        String sql = ""
                + "select b.* from Emprestimo a, ItemEmprestimo b, Pessoa c, Publicacao d, Exemplar e \n"
                + "where a.id = b.emprestimo_id \n"
                + "and a.pessoa_id = c.id \n"
                + "and b.exemplar_id = e.id \n"
                + "and d.id = e.publicacao_id \n"
                + "and c.nome like :nomel \n"
                + "and d.titulo like :obralit \n";
        if (dtEmp != null) {
            sql = sql + "and a.criacao = :dtEmp";
        }
//        }       

        Query q = getEntityManager().createNativeQuery(sql, ItemEmprestimo.class);
        q.setParameter("nomel", "%" + nomel + "%");
        q.setParameter("obralit", "%" + obralit + "%");
        if (dtEmp != null) {
            q.setParameter("dtEmp", dtEmp, TemporalType.DATE);
        }
//        if (!"".equals(obra)) q.setParameter("obra", "'%" + obra + "%'");
        return q.getResultList();
    }

    public List<ItemEmprestimo> recuperaItensEmprestimosAbertos(Emprestimo emprestimo) {
        String sql;
        sql = "select b.* from emprestimo a, itememprestimo b "
                + "where a.id = b.emprestimo_id "
                + "and b.devolucao is null "
                + "and a.id = :parte";
        Query q = getEntityManager().createNativeQuery(sql, ItemEmprestimo.class);
        q.setParameter("parte", emprestimo.getId());
        return q.getResultList();
    }

    public List<ItemEmprestimo> recuperarEmprestimosAbertosTombo(String tombo) {
        String sql = "select b.* from emprestimo a, itememprestimo b, exemplar c "
                + "where a.id = b.emprestimo_id "
                + "and b.devolucao is null "
                + "and b.exemplar_id = c.id "
                + "and c.tombo like :parte "
                + "and c.estadoexemplar = 'EMPRESTADO' ";
        Query q = getEntityManager().createNativeQuery(sql, ItemEmprestimo.class);
        q.setParameter("parte", "%" + tombo + "%");
        return q.getResultList();
    }

    public List<ItemEmprestimo> recuperaEmprestimosAbertosPessoa(Pessoa pessoa) {
        String sql = "select b.* from emprestimo a, itememprestimo b "
                + "where a.id = b.emprestimo_id "
                + "and b.devolucao is null "
                + "and a.pessoa_id = :parte";
        Query q = getEntityManager().createNativeQuery(sql, ItemEmprestimo.class);
        q.setParameter("parte", pessoa.getId());
        return q.getResultList();
    }

    public List<ItemEmprestimo> filtrarDevolPendente(String publicacao, String pessoa, String tombo, Date dataInicial, Date dataFinal) {
        String sql = "select b.* from emprestimo a, itememprestimo b, publicacao c, pessoa d, exemplar e "
                + "where b.emprestimo_id = a.id "
                + "and b.exemplar_id = e.id "
                + "and a.pessoa_id = d.id "
                + "and c.id = e.publicacao_id "
                + "and b.devolucao is null ";
        if (!"".equals(publicacao)) {
            sql = sql + "and lower(c.titulo) like :publicacao ";
        }
        if (!"".equals(pessoa)) {
            sql = sql + "and lower(d.nome) like :pessoa ";
        }
        if (!"".equals(tombo)) {
            sql = sql + "and e.tombo like :tombo ";
        }
        if (dataInicial != null) {
            System.out.println("Data Inicial: " + dataInicial);
            sql = sql + "and a.criacao >= :inicial ";
        }
        if (dataFinal != null) {
            System.out.println("Data Final: " + dataFinal);
            sql = sql + "and a.criacao <= :final ";
        }
        System.out.println("SQL: " + sql);
        Query q = getEntityManager().createNativeQuery(sql, ItemEmprestimo.class);
        if (!"".equals(publicacao)) {
            q.setParameter("publicacao", "%" + publicacao.toLowerCase() + "%");
        }
        if (!"".equals(pessoa)) {
            q.setParameter("pessoa", "%" + pessoa.toLowerCase() + "%");
        }
        if (!"".equals(tombo)) {
            q.setParameter("tombo", "%" + tombo + "%");
        }
        if (dataInicial != null) {
            q.setParameter("inicial", dataInicial);
        }

        if (dataFinal != null) {
            q.setParameter("final", dataFinal);
        }
        return q.getResultList();
    }
}
