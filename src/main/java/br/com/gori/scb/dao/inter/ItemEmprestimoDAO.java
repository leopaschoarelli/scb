package br.com.gori.scb.dao.inter;

import br.com.gori.scb.entidade.ItemEmprestimo;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Leonardo
 */
public interface ItemEmprestimoDAO {

    public List<ItemEmprestimo> listarItemEmprestimoPorPessoa(String nome);

    public ItemEmprestimo buscarItemEmprestimoPorPessoa(String nome);

    public List<ItemEmprestimo> listarFiltros(String nomel, String obralit, Date dtEmp);
}
