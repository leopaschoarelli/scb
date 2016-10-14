package br.com.gori.scb.dao.impl;

import br.com.gori.scb.dao.AbstractDAO;
import static br.com.gori.scb.dao.AbstractDAO.MAX_RESULTS_QUERY;
import br.com.gori.scb.dao.inter.ReservaDAO;
import br.com.gori.scb.entidade.Pessoa;
import br.com.gori.scb.entidade.Publicacao;
import br.com.gori.scb.entidade.Reserva;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author Leonardo
 */
public class ReservaDAOImpl extends AbstractDAO<Reserva> implements ReservaDAO {

    private final ItemReservaDAOImpl itemReservaDAO;

    public ReservaDAOImpl() {
        super(Reserva.class);
        itemReservaDAO = new ItemReservaDAOImpl();
    }

    @Override
    public List<Reserva> listarReservaPorPessoa(String nome) {
        Query q = getEntityManager().createNamedQuery("Reserva.findByPessoa", Reserva.class);
        q.setParameter("nome", nome);
        return q.getResultList();
    }

    @Override
    public Reserva buscarReservaPorPessoa(String nome) {
        Query q = getEntityManager().createNamedQuery("Reserva.findByPessoa", Reserva.class);
        q.setParameter("nome", nome);
        List<Reserva> estados = q.getResultList();
        if (estados.isEmpty()) {
            return null;
        }
        if (estados.size() >= 1) {
            return estados.get(0);
        } else {
            throw new NonUniqueResultException();
        }
    }

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
            q.setParameter("parte", "%" + nome.toLowerCase() + "%");
        }
        q.setMaxResults(MAX_RESULTS_QUERY);
        return q.getResultList();
    }

    public List<Publicacao> getPublicacao(String titulo) {
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
            q.setParameter("parte", "%" + titulo.toLowerCase() + "%");
        }
        q.setMaxResults(20);
        return q.getResultList();
    }

    public List<Reserva> recuperaReservasAbertas(Pessoa pessoa) {
        String sql = "select distinct a.* from reserva a, itemreserva b "
                + "where a.id = b.reserva_id "
                + "and (b.efetivado is null or b.efetivado = :efetivado) "
                + "and a.pessoa_id = :parte";
        Query q = getEntityManager().createNativeQuery(sql, Reserva.class);
        q.setParameter("parte", pessoa.getId());
        q.setParameter("efetivado", Boolean.FALSE);
        List<Reserva> reservas = q.getResultList();
        for (Reserva rs : reservas) {
            rs.getItemReserva().addAll(itemReservaDAO.recuperaItensReservasAbertas(rs));
        }
        return reservas;
    }

}
