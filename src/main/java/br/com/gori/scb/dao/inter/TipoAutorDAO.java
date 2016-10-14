package br.com.gori.scb.dao.inter;

import br.com.gori.scb.entidade.TipoAutor;
import java.util.List;

/**
 *
 * @author Leonardo
 */
public interface TipoAutorDAO {
    
    public List<TipoAutor> listarTipoAutorPorDescricao(String descricao);
    public TipoAutor buscarTipoAutorPorDescricao(String descricao);
}
