package br.com.gori.scb.dao.inter;

import br.com.gori.scb.entidade.Telefone;
import java.util.List;

/**
 *
 * @author Leonardo
 */
public interface TelefoneDAO {

    public List<Telefone> listarTelefonePorNumero(String numero);
    public Telefone buscarTelefonePorNumero(String numero);
}
