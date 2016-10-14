package br.com.gori.scb.dao.inter;

import br.com.gori.scb.entidade.Cidade;
import br.com.gori.scb.entidade.Pessoa;
import br.com.gori.scb.entidade.TipoPessoa;
import br.com.gori.scb.entidade.Turma;
import java.util.List;

/**
 *
 * @author Leonardo
 */
public interface PessoaDAO {

    public List<Turma> getTurmas(String descricao);
    public List<TipoPessoa> getTipoPessoas(String descricao);
    public List<Cidade> getCidades(String nome);
    public List<Pessoa> listarPessoaPorNome(String nome);
    public Pessoa buscarPessoaPorNome(String nome);
}
