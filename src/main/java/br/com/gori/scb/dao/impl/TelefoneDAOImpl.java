package br.com.gori.scb.dao.impl;

import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.dao.inter.TelefoneDAO;
import br.com.gori.scb.entidade.Telefone;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author Leonardo
 */
public class TelefoneDAOImpl extends AbstractDAO<Telefone> implements TelefoneDAO {

    public TelefoneDAOImpl() {
        super(Telefone.class);
    }

    @Override
    public List<Telefone> listarTelefonePorNumero(String numero) {
        Query q = getEntityManager().createNamedQuery("Telefone.findByNumero", Telefone.class);
        q.setParameter("numero", numero);
        return q.getResultList();
    }

    @Override
    public Telefone buscarTelefonePorNumero(String numero) {
        Query q = getEntityManager().createNamedQuery("Telefone.findByNumero", Telefone.class);
        q.setParameter("numero", numero);
        List<Telefone> telefones = q.getResultList();
        if (telefones.isEmpty()) {
            return null;
        }
        if (telefones.size() >= 1) {
            return telefones.get(0);
        } else {
            throw new NonUniqueResultException();
        }
    }

}
