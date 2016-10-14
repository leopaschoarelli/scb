package br.com.gori.scb.dao.inter;

import br.com.gori.scb.entidade.Penalidade;
import java.util.List;

/**
 *
 * @author Leonardo
 */
public interface PenalidadeDAO {

    public List<Penalidade> listarPenalidadePorDescricao(String descricao);
    public Penalidade buscarPenalidadePorDescricao(String descricao);
}
