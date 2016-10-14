package br.com.gori.scb.dao.inter;

import br.com.gori.scb.entidade.Autoria;

/**
 *
 * @author Leonardo
 */
public interface AutoriaDAO {

    public Autoria buscarAutoriaPorNome(String nome);
}
