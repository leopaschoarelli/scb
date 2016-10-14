package br.com.gori.scb.dao.impl;

import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.dao.inter.ExemplarDAO;
import br.com.gori.scb.entidade.Exemplar;
import br.com.gori.scb.entidade.ItemReserva;
import br.com.gori.scb.entidade.Publicacao;
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
        Query q = getEntityManager().createNamedQuery("Exemplar.findByTitulo", Exemplar.class);
        q.setParameter("titulo", titulo);
        return q.getResultList();
    }

    @Override
    public Exemplar buscarExemplarPorTitulo(String titulo) {
        Query q = getEntityManager().createNamedQuery("Exemplar.findByTitulo", Exemplar.class);
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

    public List<Exemplar> verificarDispReserva(Publicacao publicacao, ItemReserva itemReserva) {
        String sql = "select b.* from publicacao a, exemplar b "
                + "where a.id = b.publicacao_id "
                + "and a.id = :public "
                + "and (select count(d.id) from exemplar d "
                + "     where d.publicacao_id = :public) > (select count(f.id) from itemreserva f "
                + "					 where f.publicacao_id = :public "
                + "					 and f.previsao between :inicio and :fim "
                + "					 and f.efetivado = 'false') ";
        System.out.println("Inicio: "+itemReserva.getPrevisao()+" - Fim: "+itemReserva.getDevolucao());
        System.out.println("SQL: "+sql);
        Query q = getEntityManager().createNativeQuery(sql, Exemplar.class);
        q.setParameter("public", publicacao.getId());
        q.setParameter("inicio", itemReserva.getPrevisao());
        q.setParameter("fim", itemReserva.getDevolucao());
        return q.getResultList();
    }

    public List<Exemplar> verificarDisponibilidade(Publicacao publicacao, ItemReserva itemReserva) {
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
        Query q = getEntityManager().createNativeQuery(sql, Exemplar.class);
        q.setParameter("public", publicacao.getId());
        q.setParameter("previsao", itemReserva.getPrevisao());
        return q.getResultList();
    }
}
