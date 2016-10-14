package br.com.gori.scb.dao.inter;

import br.com.gori.scb.entidade.TipoPessoa;
import java.util.List;

/**
 *
 * @author Leonardo
 */
public interface TipoPessoaDAO {

    public List<TipoPessoa> listarTipoPessoaPorDescricao(String descricao);
    public TipoPessoa buscarTipoPessoaPorDescricao(String descricao);
}
