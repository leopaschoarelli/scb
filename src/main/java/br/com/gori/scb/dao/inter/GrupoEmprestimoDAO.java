package br.com.gori.scb.dao.inter;

import br.com.gori.scb.entidade.GrupoEmprestimo;
import br.com.gori.scb.entidade.Publicacao;
import br.com.gori.scb.entidade.TipoPessoa;
import java.util.List;

/**
 *
 * @author Leonardo
 */
public interface GrupoEmprestimoDAO {

    public List<TipoPessoa> getTipoPessoas(String descricao);
    public List<Publicacao> getPublicacoes(String titulo);
    public List<GrupoEmprestimo> listarGrupoPorNome(String nome);
    public GrupoEmprestimo buscarGrupoPorNome(String nome);
}
