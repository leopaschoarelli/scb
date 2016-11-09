package br.com.gori.scb.dao.impl;

import br.com.gori.scb.connection.EntityManagerProducer;
import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.entidade.Emprestimo;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author Leonardo
 */
public class EmpDAOImpl extends AbstractDAO<Emprestimo> {

    public EmpDAOImpl() {
        super(Emprestimo.class);
    }

    public List<Emprestimo> listarEmprestimoPorPessoa(String nome) {
        Query q = EntityManagerProducer.getEntityManager().createNamedQuery("Emprestimo.findByPessoa", Emprestimo.class);
        q.setParameter("nome", nome);
        return q.getResultList();
    }

    public Emprestimo buscarEmprestimoPorPessoa(String nome) {
        Query q = EntityManagerProducer.getEntityManager().createNamedQuery("Emprestimo.findByPessoa", Emprestimo.class);
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
