package br.com.gori.scb.dao.impl;

import br.com.gori.scb.connection.EntityManagerProducer;
import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.dao.inter.EditoraDAO;
import br.com.gori.scb.entidade.Editora;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author Leonardo
 */
public class EditoraDAOImpl extends AbstractDAO<Editora> implements EditoraDAO {

    public EditoraDAOImpl() {
        super(Editora.class);
    }

    @Override
    public List<Editora> listarEditoraPorNome(String nome) {
        Query q = EntityManagerProducer.getEntityManager().createNamedQuery("Editora.findByNome", Editora.class);
        q.setParameter("nome", nome);
        return q.getResultList();
    }

    @Override
    public Editora buscarEditoraPorNome(String nome) {
        Query q = EntityManagerProducer.getEntityManager().createNamedQuery("Editora.findByNome", Editora.class);
        q.setParameter("nome", nome);
        List<Editora> editoras = q.getResultList();
        if (editoras.isEmpty()) {
            return null;
        }
        if (editoras.size() >= 1) {
            return editoras.get(0);
        } else {
            throw new NonUniqueResultException();
        }
    }

}
