package br.com.gori.scb.controle;

import br.com.gori.scb.controle.util.JsfUtil;
import br.com.gori.scb.entidade.ConfiguracaoEmprestimo;
import br.com.gori.scb.dao.impl.ConfiguracaoEmprestimoDAOImpl;
import br.com.gori.scb.dao.impl.PenalidadeDAOImpl;
import br.com.gori.scb.dao.impl.TipoPessoaDAOImpl;
import br.com.gori.scb.dao.inter.TipoPessoaDAO;
import br.com.gori.scb.entidade.Penalidade;
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
public class ConfiguracaoEmprestimoControlador implements Serializable {

    private ConfiguracaoEmprestimo configuracaoEmprestimo;
    private ConfiguracaoEmprestimo configuracaoSelecionada;
    private ConfiguracaoEmprestimoDAOImpl configuracaoEmprestimoDAO;
    private TipoPessoaDAOImpl tipoPessoaDAO;
    private PenalidadeDAOImpl penalidadeDAO;
    private Penalidade penalidade;
    private ConverterAutoComplete converterTipoPessoa;
    private ConverterAutoComplete converterPenalidade;
    private boolean visualizar;
    private boolean edicao;
    private String header;

    public ConfiguracaoEmprestimoControlador() {
        newInstances();
    }

    public String salvar() {
        try {
            if (validaPenalidade()) {
                if (edicao) {
                    configuracaoEmprestimoDAO.update(configuracaoEmprestimo);
                } else {
                    configuracaoEmprestimoDAO.save(configuracaoEmprestimo);
                }
            }
            JsfUtil.addSuccessMessage("Configuração salvo com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao salvar configuração");
            return null;
        }
    }

    public boolean validaPenalidade() {
        try {
            if (configuracaoEmprestimo.getPenalidades().isEmpty()) {
                JsfUtil.addErrorMessage("É necessario informar um tipo de penalidade!");
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao validar tipo de penalidade");
            return false;
        }
    }

    public String excluir() {
        try {
            configuracaoEmprestimoDAO.delete(configuracaoSelecionada);
            configuracaoSelecionada = null;
            JsfUtil.addSuccessMessage("Configuração removido com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao excluir configuração");
            return null;
        }
    }

    public String prepararLista() {
        newInstances();
        if (configuracaoSelecionada != null) {
            configuracaoSelecionada = null;
        }
        return "lista";
    }

    public String prepararEdicao() {
        newInstances();
        configuracaoEmprestimo = configuracaoSelecionada;
        header = "Editar Configuração";
        edicao = true;
        visualizar = false;
        return "edita";
    }

    public String prepararNovo() {
        newInstances();
        header = "Cadastro de Configuração";
        visualizar = false;
        edicao = false;
        return "edita";
    }

    public String prepararVer() {
        newInstances();
        configuracaoEmprestimo = configuracaoSelecionada;
        visualizar = true;
        edicao = false;
        header = "Visualizar Configuração";
        return "edita";
    }

    private void newInstances() {
        this.configuracaoEmprestimo = new ConfiguracaoEmprestimo();
        this.configuracaoEmprestimoDAO = new ConfiguracaoEmprestimoDAOImpl();
        this.tipoPessoaDAO = new TipoPessoaDAOImpl();
        this.penalidadeDAO = new PenalidadeDAOImpl();
        this.penalidade = new Penalidade();
    }

    public List<ConfiguracaoEmprestimo> getConfiguracoes() {
        return configuracaoEmprestimoDAO.listAll();
    }

    public ConfiguracaoEmprestimo getConfiguracaoEmprestimo() {
        return configuracaoEmprestimo;
    }

    public void setConfiguracaoEmprestimo(ConfiguracaoEmprestimo configuracaoEmprestimo) {
        this.configuracaoEmprestimo = configuracaoEmprestimo;
    }

    public ConfiguracaoEmprestimoDAOImpl getConfiguracaoEmprestimoDAO() {
        return configuracaoEmprestimoDAO;
    }

    public boolean isVisualizar() {
        return visualizar;
    }

    public void setVisualizar(boolean visualizar) {
        this.visualizar = visualizar;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public TipoPessoaDAO getTipoPessoaDAOImpl() {
        return tipoPessoaDAO;
    }

    public void setTipoPessoaDAOImpl(TipoPessoaDAOImpl tipoPessoaDAO) {
        this.tipoPessoaDAO = tipoPessoaDAO;
    }

    public ConverterAutoComplete getConverterTipoPessoa() {
        if (converterTipoPessoa == null) {
            converterTipoPessoa = new ConverterAutoComplete(TipoPessoa.class, tipoPessoaDAO);
        }
        return converterTipoPessoa;
    }

    public void setConverterTipoPessoa(ConverterAutoComplete converterTipoPessoa) {
        this.converterTipoPessoa = converterTipoPessoa;
    }

    public List<TipoPessoa> completaTipoPessoas(String value) {
        return configuracaoEmprestimoDAO.getTipoPessoas(value.toLowerCase());
    }

    public void adicionaPenalidade() {
        configuracaoEmprestimo.getPenalidades().add(penalidade);
        penalidade = new Penalidade();
    }

    public TipoPessoaDAOImpl getTipoPessoaDAO() {
        return tipoPessoaDAO;
    }

    public void setTipoPessoaDAO(TipoPessoaDAOImpl tipoPessoaDAO) {
        this.tipoPessoaDAO = tipoPessoaDAO;
    }

    public PenalidadeDAOImpl getPenalidadeDAO() {
        return penalidadeDAO;
    }

    public void setPenalidadeDAO(PenalidadeDAOImpl penalidadeDAO) {
        this.penalidadeDAO = penalidadeDAO;
    }

    public Penalidade getPenalidade() {
        return penalidade;
    }

    public void setPenalidade(Penalidade penalidade) {
        this.penalidade = penalidade;
    }

    public ConverterAutoComplete getConverterPenalidade() {
        if (converterPenalidade == null) {
            converterPenalidade = new ConverterAutoComplete(Penalidade.class, penalidadeDAO);
        }
        return converterPenalidade;
    }

    public List<Penalidade> completaPenalidades(String value) {
        return configuracaoEmprestimoDAO.getPenalidades(value.toLowerCase());
    }

    public void setConverterPenalidade(ConverterAutoComplete converterPenalidade) {
        this.converterPenalidade = converterPenalidade;
    }

    public boolean isEdicao() {
        return edicao;
    }

    public void setEdicao(boolean edicao) {
        this.edicao = edicao;
    }

    public void setConfiguracaoEmprestimoDAO(ConfiguracaoEmprestimoDAOImpl configuracaoEmprestimoDAO) {
        this.configuracaoEmprestimoDAO = configuracaoEmprestimoDAO;
    }

    public ConfiguracaoEmprestimo getConfiguracaoSelecionada() {
        return configuracaoSelecionada;
    }

    public void setConfiguracaoSelecionada(ConfiguracaoEmprestimo configuracaoSelecionada) {
        this.configuracaoSelecionada = configuracaoSelecionada;
    }

}
