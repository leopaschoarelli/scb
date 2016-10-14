package br.com.gori.scb.dao.inter;

import br.com.gori.scb.entidade.Endereco;
import java.util.List;

/**
 *
 * @author Leonardo
 */
public interface EnderecoDAO {

    public List<Endereco> listarEnderecoPorLogradouro(String logradouro);
    public Endereco buscarEnderecoPorLogradouro(String logradouro);
}
