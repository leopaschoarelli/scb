package br.com.gori.scb.dao.inter;

import br.com.gori.scb.entidade.ConfiguracaoEmprestimo;
import br.com.gori.scb.entidade.Emprestimo;
import br.com.gori.scb.entidade.Exemplar;
import br.com.gori.scb.entidade.ItemEmprestimo;
import br.com.gori.scb.entidade.Pessoa;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Leonardo
 */
public interface EmprestimoDAO {

    public List<Emprestimo> listarEmprestimoPorPessoa(String nome);
    public Emprestimo buscarEmprestimoPorPessoa(String nome);
    public ConfiguracaoEmprestimo buscarDiasEmprestimoPessoa(String nome);
    public Integer buscarQtdEmprestadaPessoa(String nome);
    public List<ItemEmprestimo> listarFiltros(String nomel, String obralit, Date dtInicial, Date dtFinal, Date dtDevolIni, Date dtDevolFinal, Long idPessoa);
    public List<ItemEmprestimo> recuperarEmprestimosPessoa(String nome);
    public List<Pessoa> getPessoas(String nome);
    public List<Exemplar> getPublicacoes(String tombo);
    public List<Exemplar> getExemplares(Long id);
}
