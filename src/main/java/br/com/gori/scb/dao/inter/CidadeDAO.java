package br.com.gori.scb.dao.inter;

import br.com.gori.scb.entidade.Cidade;
import br.com.gori.scb.entidade.Estado;
import java.util.List;

/**
 *
 * @author Leonardo
 */
public interface CidadeDAO {

    public List<Estado> getEstados(String nome); 
    public List<Cidade> listarCidadePorNome(String nome);
    public Cidade buscarCidadePorNome(String nome);
}
