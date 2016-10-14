package br.com.gori.scb.dao.inter;

import br.com.gori.scb.entidade.Exemplar;
import java.util.List;

/**
 *
 * @author Leonardo
 */
public interface ExemplarDAO {

    public List<Exemplar> listarExemplarPorTitulo(String titulo);
    public Exemplar buscarExemplarPorTitulo(String titulo);
}
