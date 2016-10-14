package br.com.gori.scb.controle;

import br.com.gori.scb.controle.util.JsfUtil;
import br.com.gori.scb.dao.impl.TipoPessoaDAOImpl;
import br.com.gori.scb.entidade.TipoPessoa;
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
public class TipoPessoaControlador implements Serializable {

    private TipoPessoaDAOImpl tipoPessoaDAO;
    private TipoPessoa tipoPessoa;
    private TipoPessoa tipoPessoaSelecionada;
    private String header;
    private boolean visualiza;
    private boolean edicao;

    public TipoPessoaControlador() {
        newInstances();
    }

    public String salvar() {
        try {
            if (edicao) {
                tipoPessoaDAO.update(tipoPessoa);
            } else {
                tipoPessoaDAO.save(tipoPessoa);
            }
            JsfUtil.addSuccessMessage("Tipo de Pessoa salvo com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao salvar Tipo de Pessoa");
            return null;
        }
    }

    public String excluir() {
        try {
            tipoPessoaDAO.delete(tipoPessoaSelecionada);
            tipoPessoaSelecionada = null;
            JsfUtil.addSuccessMessage("Tipo de Pessoa removido com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao excluir Tipo de Pessoa");
            return null;
        }
    }

    public String prepararLista() {
        newInstances();
        if (tipoPessoaSelecionada != null) tipoPessoaSelecionada = null;
        return "lista";
    }

    public String prepararEdicao() {
        newInstances();
        tipoPessoa = tipoPessoaSelecionada;
        header = "Editar Tipo de Pessoa";
        edicao = true;
        visualiza = false;
        return "edita";
    }

    public String prepararNovo() {
        newInstances();
        header = "Cadastro de Tipo de Pessoa";
        edicao = false;
        visualiza = false;
        return "edita";
    }

    public String prepararVer() {
        newInstances();
        tipoPessoa = tipoPessoaSelecionada;
        visualiza = true;
        edicao = false;
        header = "Visualizar Tipo de Pessoa";
        return "edita";
    }

    private void newInstances() {
        this.tipoPessoa = new TipoPessoa();
        this.tipoPessoaDAO = new TipoPessoaDAOImpl();
    }

    public List<TipoPessoa> getTipoPessoas() {
        return tipoPessoaDAO.listAll();
    }

    public TipoPessoaDAOImpl getTipoPessoaDAO() {
        return tipoPessoaDAO;
    }

    public void setTipoPessoaDAO(TipoPessoaDAOImpl tipoPessoaDAO) {
        this.tipoPessoaDAO = tipoPessoaDAO;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setVisualiza(boolean visualiza) {
        this.visualiza = visualiza;
    }
    
    public void setEdicao(boolean edicao) {
        this.edicao = edicao;
    }

    public boolean isVisualiza() {
        return visualiza;
    }

    public boolean isEdicao() {
        return edicao;
    }

    public TipoPessoa getTipoPessoaSelecionada() {
        return tipoPessoaSelecionada;
    }

    public void setTipoPessoaSelecionada(TipoPessoa tipoPessoaSelecionada) {
        this.tipoPessoaSelecionada = tipoPessoaSelecionada;
    }
    
    

}
