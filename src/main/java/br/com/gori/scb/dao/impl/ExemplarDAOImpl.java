package br.com.gori.scb.dao.impl;

import br.com.gori.scb.connection.EntityManagerProducer;
import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.dao.inter.ExemplarDAO;
import br.com.gori.scb.entidade.Exemplar;
import br.com.gori.scb.entidade.ItemEmprestimo;
import br.com.gori.scb.entidade.ItemReserva;
import br.com.gori.scb.entidade.Publicacao;
import java.util.Date;
import java.util.List;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author Leonardo
 */
public class ExemplarDAOImpl extends AbstractDAO<Exemplar> implements ExemplarDAO {

    public ExemplarDAOImpl() {
        super(Exemplar.class);
    }

    @Override
    public List<Exemplar> listarExemplarPorTitulo(String titulo) {
        Query q = EntityManagerProducer.getEntityManager().createNamedQuery("Exemplar.findByTitulo", Exemplar.class);
        q.setParameter("titulo", titulo);
        return q.getResultList();
    }

    @Override
    public Exemplar buscarExemplarPorTitulo(String titulo) {
        Query q = EntityManagerProducer.getEntityManager().createNamedQuery("Exemplar.findByTitulo", Exemplar.class);
        q.setParameter("titulo", titulo);
        List<Exemplar> exemplares = q.getResultList();
        if (exemplares.isEmpty()) {
            return null;
        }
        if (exemplares.size() >= 1) {
            return exemplares.get(0);
        } else {
            throw new NonUniqueResultException();
        }
    }

    public Exemplar recuperaExemplarEmpPorTombo(String tombo) {
        String sql = "select b.* from publicacao a, exemplar b "
                + "where b.publicacao_id = a.id "
                + "and b.databaixa is null "
                + "and b.estadoexemplar = 'EMPRESTADO' "
                + "and b.tombo like :parte "
                + "limit 1 ";
        Query q = EntityManagerProducer.getEntityManager().createNativeQuery(sql, Exemplar.class);
        q.setParameter("parte", "%" + tombo + "%");
        if (q.getResultList().isEmpty()) {
            return null;
        }
        return (Exemplar) q.getSingleResult();
    }

    public Exemplar recuperaExemplarPorTombo(String tombo) {
        String sql = "select b.* from publicacao a, exemplar b "
                + "where b.publicacao_id = a.id "
                + "and b.databaixa is null "
                + "and b.estadoexemplar = 'DISPONIVEL' "
                + "and b.tombo like :parte "
                + "limit 1 ";
        Query q = EntityManagerProducer.getEntityManager().createNativeQuery(sql, Exemplar.class);
        q.setParameter("parte", "%" + tombo + "%");
        if (q.getResultList().isEmpty()) {
            return null;
        }
        return (Exemplar) q.getSingleResult();
    }

    public List<Exemplar> verificarDispReserva(Publicacao publicacao, ItemReserva itemReserva, ItemEmprestimo itemEmprestimo) {
        String sql = "select b.* from publicacao a, exemplar b "
                + "where a.id = b.publicacao_id "
                + "and a.id = :public "
                + "and (select count(d.id) from exemplar d "
                + "     where d.publicacao_id = :public) > (select count(f.id) from itemreserva f "
                + "					 where f.publicacao_id = :public "
                + "					 and (f.previsao between :inicio and :fim "
                + "					 or f.devolucao between :inicio and :fim )"
                + "					 and f.efetivado = 'false') ";
        if (itemReserva != null) {
            System.out.println("Inicio: " + itemReserva.getPrevisao() + " - Fim: " + itemReserva.getDevolucao());
        }
        System.out.println("SQL: " + sql);
        Query q = EntityManagerProducer.getEntityManager().createNativeQuery(sql, Exemplar.class);
        q.setParameter("public", publicacao.getId());
        if (itemReserva != null) {
            q.setParameter("inicio", itemReserva.getPrevisao());
            q.setParameter("fim", itemReserva.getDevolucao());
        } else if (itemEmprestimo != null) {
            q.setParameter("inicio", new Date());
            q.setParameter("fim", itemEmprestimo.getPrazo());
        }
        return q.getResultList();
    }

    public List<Exemplar> verificarDisponibilidade(Publicacao publicacao, ItemReserva itemReserva, ItemEmprestimo itemEmprestimo) {
        String sql = "select ex.* from publicacao pub "
                + "inner join exemplar ex on ex.publicacao_id = pub.id "
                + "where ex.estadoexemplar = 'DISPONIVEL' "
                + "and pub.id = :public "
                + "union "
                + "select ex.* from publicacao pub "
                + "inner join exemplar ex on ex.publicacao_id = pub.id "
                + "inner join itememprestimo it on it.exemplar_id = ex.id "
                + "where ex.estadoexemplar = 'EMPRESTADO' "
                + "and pub.id = :public "
                + "and it.prazo <= :previsao ";
        Query q = EntityManagerProducer.getEntityManager().createNativeQuery(sql, Exemplar.class);
        q.setParameter("public", publicacao.getId());
        if (itemReserva != null) {
            q.setParameter("previsao", itemReserva.getPrevisao());
        } else if (itemEmprestimo != null) {
            q.setParameter("previsao", new Date());
        }
        return q.getResultList();
    }
    
    public List<Exemplar> getExemplares(String tombo) {
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
        Query q = EntityManagerProducer.getEntityManager().createNativeQuery(sql, Exemplar.class);
        if (caracter) {
            q.setParameter("parte", "%" + tombo + "%");
        }
        q.setMaxResults(MAX_RESULTS_QUERY);
        return q.getResultList();
    }
}
