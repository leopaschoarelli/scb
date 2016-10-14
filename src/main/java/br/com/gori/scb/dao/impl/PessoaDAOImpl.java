package br.com.gori.scb.dao.impl;

import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.dao.inter.PessoaDAO;
import br.com.gori.scb.entidade.Cidade;
import br.com.gori.scb.entidade.Pessoa;
import br.com.gori.scb.entidade.TipoPessoa;
import br.com.gori.scb.entidade.Turma;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author Leonardo
 */
public class PessoaDAOImpl extends AbstractDAO<Pessoa> implements PessoaDAO {

    private final ReservaDAOImpl reservaDao;
    private final EmprestimoDAOImpl emprestimoDao;

    public PessoaDAOImpl() {
        super(Pessoa.class);
        reservaDao = new ReservaDAOImpl();
        emprestimoDao = new EmprestimoDAOImpl();
    }

    @Override
    public List<Turma> getTurmas(String descricao) {
        String sql = "select t.* from turma t where lower(t.descricao) like :parte";
        Query q = getEntityManager().createNativeQuery(sql, Turma.class);
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
    public List<Cidade> getCidades(String nome) {
        String sql = "select c.* from cidade c where lower(c.nome) like :parte";
        Query q = getEntityManager().createNativeQuery(sql, Cidade.class);
        q.setParameter("parte", "%" + nome + "%");
        q.setMaxResults(MAX_RESULTS_QUERY);
        return q.getResultList();
    }

    @Override
    public List<Pessoa> listarPessoaPorNome(String nome) {
        Query q = getEntityManager().createNamedQuery("Pessoa.findByNome", Pessoa.class);
        q.setParameter("nome", nome);
        return q.getResultList();
    }

    @Override
    public Pessoa buscarPessoaPorNome(String nome) {
        Query q = getEntityManager().createNamedQuery("Pessoa.findByNome", Pessoa.class);
        q.setParameter("nome", nome);
        List<Pessoa> pessoas = q.getResultList();
        if (pessoas.isEmpty()) {
            return null;
        }
        if (pessoas.size() >= 1) {
            return pessoas.get(0);
        } else {
            throw new NonUniqueResultException();
        }
    }

    public Pessoa recuperaReservasEEmprestimos(Pessoa p) {
        p.getReservasAbertas().addAll(reservaDao.recuperaReservasAbertas(p));
        p.getEmprestimosAbertos().addAll(emprestimoDao.recuperaEmprestimosAbertos(p));
        return p;
    }


}
