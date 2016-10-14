package br.com.gori.scb.dao.inter;

import br.com.gori.scb.entidade.ConfiguracaoEmprestimo;
import br.com.gori.scb.entidade.Penalidade;
import br.com.gori.scb.entidade.TipoPessoa;
import java.util.List;

/**
 *
 * @author Leonardo
 */
public interface ConfiguracaoEmprestimoDAO {

    public List<Penalidade> getPenalidades(String descricao);
    public List<TipoPessoa> getTipoPessoas(String descricao);
    public List<ConfiguracaoEmprestimo> listarConfiguracaoPorDias(Integer dias);
    public ConfiguracaoEmprestimo buscarConfiguracaoPorDias(Integer dias);
    public ConfiguracaoEmprestimo buscarConfiguracaoPorPessoa(String descricao);
}
