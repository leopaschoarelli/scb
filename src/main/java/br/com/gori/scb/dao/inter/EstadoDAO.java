package br.com.gori.scb.dao.inter;

import br.com.gori.scb.entidade.Estado;
import br.com.gori.scb.entidade.Pais;
import java.util.List;

/**
 *
 * @author Leonardo
 */
public interface EstadoDAO {

    public List<Pais> getPaises(String nome);
    public List<Estado> listarEstadoPorNome(String nome);
    public Estado buscarEstadoPorNome(String nome);
}
