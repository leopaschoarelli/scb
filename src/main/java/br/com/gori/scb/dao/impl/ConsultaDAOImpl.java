package br.com.gori.scb.dao.impl;

import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.entidade.ItemEmprestimo;
import br.com.gori.scb.entidade.Publicacao;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Leonardo
 */
public class ConsultaDAOImpl extends AbstractDAO<Publicacao> {

    public ConsultaDAOImpl() {
        super(Publicacao.class);
    }

    public List<Publicacao> listarFiltros(String nome, String expressao) {
        expressao = expressao.replace("_", " ");
        if ("igual".equals(expressao)) {
            expressao = "=";
        }
        System.out.println(expressao);
        return null;
//        String sql = ""
//                + "select b.* from Emprestimo a, ItemEmprestimo b, Pessoa c, Publicacao d, Exemplar e \n"
//                + "where a.id = b.emprestimo_id \n"
//                + "and a.pessoa_id = c.id \n"
//                + "and b.exemplar_id = e.id \n"
//                + "and d.id = e.publicacao_id \n";
//        if (!"".equals(nome)) {
//            sql = sql + "and lower(c.nome) like :nome";
//        }
//        Query q = getEntityManager().createNativeQuery(sql, ItemEmprestimo.class);
//        if (!"".equals(nome)) {
//            q.setParameter("nome", "'%" + nome + "%'");
//        }
//        return q.getResultList();
    }
}
