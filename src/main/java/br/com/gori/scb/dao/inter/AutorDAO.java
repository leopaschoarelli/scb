package br.com.gori.scb.dao.inter;

import br.com.gori.scb.entidade.Autor;
import java.util.List;

/**
 *
 * @author Leonardo
 */
public interface AutorDAO {

    public List<Autor> listarAutorPorNome(String nome);
    public Autor buscarAutorPorNome(String nome);
}
