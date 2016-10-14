package br.com.gori.scb.dao.inter;

import br.com.gori.scb.entidade.Editora;
import java.util.List;

/**
 *
 * @author Leonardo
 */
public interface EditoraDAO {

    public List<Editora> listarEditoraPorNome(String nome);
    public Editora buscarEditoraPorNome(String nome);
}
