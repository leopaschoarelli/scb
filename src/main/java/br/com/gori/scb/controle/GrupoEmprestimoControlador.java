package br.com.gori.scb.controle;

import br.com.gori.scb.controle.util.JsfUtil;
import br.com.gori.scb.dao.impl.GrupoEmprestimoDAOImpl;
import br.com.gori.scb.dao.impl.PublicacaoDAOImpl;
import br.com.gori.scb.dao.impl.TipoPessoaDAOImpl;
import br.com.gori.scb.entidade.GrupoEmprestimo;
import br.com.gori.scb.entidade.Publicacao;
import br.com.gori.scb.entidade.TipoPessoa;
import br.com.gori.scb.util.ConverterAutoComplete;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Leonardo
 */
@ManagedBean
@SessionScoped
public class GrupoEmprestimoControlador implements Serializable {

    private GrupoEmprestimo grupoEmprestimo;
    private GrupoEmprestimo grupoSelecionado;
    private GrupoEmprestimoDAOImpl grupoEmprestimoDAO;
    private boolean visualizar;
    private boolean edicao;
    private String header;
    private Publicacao publicacao;
    private PublicacaoDAOImpl publicacaoDAO;
    private TipoPessoaDAOImpl tipoPessoaDAO;
    private TipoPessoa tipoPessoa;
    private ConverterAutoComplete converterTipoPessoa;
    private ConverterAutoComplete converterPublicacao;

    public GrupoEmprestimoControlador() {
        newInstances();
    }

    private void newInstances() {
        this.grupoEmprestimo = new GrupoEmprestimo();
        this.grupoEmprestimoDAO = new GrupoEmprestimoDAOImpl();
        this.publicacaoDAO = new PublicacaoDAOImpl();
        this.tipoPessoaDAO = new TipoPessoaDAOImpl();
        this.publicacao = new Publicacao();
        this.tipoPessoa = new TipoPessoa();
    }

    public String salvar() {
        try {
            if (validaTipoPessoa() && validaObras()) {
                if (edicao) {
                    grupoEmprestimoDAO.update(grupoEmprestimo);
                } else {
                    grupoEmprestimoDAO.save(grupoEmprestimo);
                }
            }
            JsfUtil.addSuccessMessage("Grupo de Emprestimo salva com sucesso!");
            return prepararLista();
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Erro ao salvar grupo de emprestimo: " + ex.getMessage());
            return null;
        }
    }

    public boolean validaTipoPessoa() {
        try {
            if (grupoEmprestimo.getTipoPessoas().isEmpty()) {
                JsfUtil.addErrorMessage("É necessario informar um tipo de pessoa!");
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao validar tipo de pessoa");
            return false;
        }
    }

    public boolean validaObras() {
        try {
            if (grupoEmprestimo.getGrupoPublicacoes().isEmpty()) {
                JsfUtil.addErrorMessage("É necessario informar uma obra litéraria!");
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao validar obra litéraria");
            return false;
        }
    }

    public String excluir() {
        try {
            grupoEmprestimoDAO.delete(grupoSelecionado);
            grupoSelecionado = null;
            JsfUtil.addSuccessMessage("Grupo de Emprestimo removido com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao excluir Grupo de Emprestimo");
            return null;
        }
    }

    public String prepararLista() {
        newInstances();
        if (grupoSelecionado != null) {
            grupoSelecionado = null;
        }
        return "lista";
    }

    public String prepararEdicao() {
        newInstances();
        grupoEmprestimo = grupoSelecionado;
        visualizar = false;
        edicao = true;
        header = "Editar Grupo de Emprestimo";
        return "edita";
    }

    public String prepararNovo() {
        newInstances();
        visualizar = false;
        edicao = false;
        header = "Cadastrar Grupo de Emprestimo";
        return "edita";
    }

    public String prepararVer() {
        newInstances();
        grupoEmprestimo = grupoSelecionado;
        edicao = false;
        header = "Visualizar Grupo de Emprestimo";
        visualizar = true;
        return "edita";
    }

    public List<GrupoEmprestimo> getGrupos() {
        return grupoEmprestimoDAO.listAll();
    }

    public GrupoEmprestimo getGrupoEmprestimo() {
        return grupoEmprestimo;
    }

    public void setGrupoEmprestimo(GrupoEmprestimo grupoEmprestimo) {
        this.grupoEmprestimo = grupoEmprestimo;
    }

    public GrupoEmprestimoDAOImpl getGrupoEmprestimoDAO() {
        return grupoEmprestimoDAO;
    }

    public void setGrupoEmprestimoDAO(GrupoEmprestimoDAOImpl grupoEmprestimoDAO) {
        this.grupoEmprestimoDAO = grupoEmprestimoDAO;
    }

    public void setEdicao(boolean edicao) {
        this.edicao = edicao;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public PublicacaoDAOImpl getPublicacaoDAO() {
        return publicacaoDAO;
    }

    public void setPublicacaoDAO(PublicacaoDAOImpl publicacaoDAO) {
        this.publicacaoDAO = publicacaoDAO;
    }

    public TipoPessoaDAOImpl getTipoPessoaDAO() {
        return tipoPessoaDAO;
    }

    public void setTipoPessoaDAO(TipoPessoaDAOImpl tipoPessoaDAO) {
        this.tipoPessoaDAO = tipoPessoaDAO;
    }

    public ConverterAutoComplete getConverterTipoPessoa() {
        if (converterTipoPessoa == null) {
            converterTipoPessoa = new ConverterAutoComplete(TipoPessoa.class, tipoPessoaDAO);
        }
        return converterTipoPessoa;
    }

    public void setConverterTipoPessoa(ConverterAutoComplete conveterTipoPessoa) {
        this.converterTipoPessoa = conveterTipoPessoa;
    }

    public ConverterAutoComplete getConverterPublicacao() {
        if (converterPublicacao == null) {
            converterPublicacao = new ConverterAutoComplete(Publicacao.class, publicacaoDAO);
        }
        return converterPublicacao;
    }

    public List<TipoPessoa> completaTipoPessoa(String value) {
        return grupoEmprestimoDAO.getTipoPessoas(value.toLowerCase());
    }

    public List<Publicacao> completaPublicacoes(String value) {
        return grupoEmprestimoDAO.getPublicacoes(value.toLowerCase());
    }

    public void setConverterPublicacao(ConverterAutoComplete converterPublicacao) {
        this.converterPublicacao = converterPublicacao;
    }

    public void adicionaTipoPessoa() {
        grupoEmprestimo.getTipoPessoas().add(tipoPessoa);
        tipoPessoa = new TipoPessoa();
    }

    public void adicionaPublicacao() {
        grupoEmprestimo.getGrupoPublicacoes().add(publicacao);
        publicacao = new Publicacao();
    }

    public Publicacao getPublicacao() {
        return publicacao;
    }

    public void setPublicacao(Publicacao publicacao) {
        this.publicacao = publicacao;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public void excluirTipoPessoa(TipoPessoa tipoPessoa) {
        grupoEmprestimo.getTipoPessoas().remove(tipoPessoa);
    }

    public void excluirPublicacao(Publicacao publicacao) {
        grupoEmprestimo.getGrupoPublicacoes().remove(publicacao);
    }

    public boolean isVisualizar() {
        return visualizar;
    }

    public boolean isEdicao() {
        return edicao;
    }

    public void setVisualizar(boolean visualizar) {
        this.visualizar = visualizar;
    }

    public GrupoEmprestimo getGrupoSelecionado() {
        return grupoSelecionado;
    }

    public void setGrupoSelecionado(GrupoEmprestimo grupoSelecionado) {
        this.grupoSelecionado = grupoSelecionado;
    }

}
