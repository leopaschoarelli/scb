package br.com.gori.scb.dao.inter;

import br.com.gori.scb.entidade.Turno;
import java.util.List;

/**
 *
 * @author Leonardo
 */
public interface TurnoDAO {

    public List<Turno> listarTurnoPorDescricao(String descricao);
    public Turno buscarTurnoPorDescricao(String descricao);
}
