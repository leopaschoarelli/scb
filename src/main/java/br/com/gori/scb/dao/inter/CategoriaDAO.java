package br.com.gori.scb.dao.inter;

import br.com.gori.scb.entidade.Categoria;
import java.util.List;

/**
 *
 * @author Leonardo
 */
public interface CategoriaDAO {

    public List<Categoria> raizes();
    public List<Categoria> getCategorias(String descricao);
    public List<Categoria> listarCategoriaPorDescricao(String descricao);
    public Categoria buscarCategoriaPorDescricao(String descricao);   
}
