package br.com.gori.scb.dao.inter;

import br.com.gori.scb.entidade.Pais;
import java.util.List;

/**
 *
 * @author Leonardo
 */
public interface PaisDAO {

    public List<Pais> listarPaisPorNome(String nome);
    public Pais buscarPaisPorNome(String nome);
    
}
