package br.com.gori.scb.dao.impl;

import br.com.gori.scb.dao.AbstractDAO;
import static br.com.gori.scb.dao.AbstractDAO.MAX_RESULTS_QUERY;
import br.com.gori.scb.dao.inter.EmprestimoDAO;
import br.com.gori.scb.entidade.ConfiguracaoEmprestimo;
import br.com.gori.scb.entidade.Emprestimo;
import br.com.gori.scb.entidade.Exemplar;
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
public class EmpDAOImpl extends AbstractDAO<Emprestimo> {

    public EmpDAOImpl() {
        super(Emprestimo.class);
    }

    public List<Emprestimo> listarEmprestimoPorPessoa(String nome) {
        Query q = getEntityManager().createNamedQuery("Emprestimo.findByPessoa", Emprestimo.class);
        q.setParameter("nome", nome);
        return q.getResultList();
    }

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

}
