package br.com.gori.scb.controle;

import br.com.gori.scb.controle.util.JsfUtil;
import br.com.gori.scb.entidade.Penalidade;
import br.com.gori.scb.dao.impl.PenalidadeDAOImpl;
import br.com.gori.scb.entidade.util.TipoPenalidade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Leonardo
 */
@ManagedBean
@SessionScoped
public class PenalidadeControlador implements Serializable {

    private Penalidade penalidade;
    private Penalidade penalidadeSelecionada;
    private PenalidadeDAOImpl penalidadeDAO;
    private boolean visualizar;
    private boolean edicao;
    private String header;

    public PenalidadeControlador() {
        newInstances();
    }

    public String salvar() {
        try {
            if (edicao) {
                penalidadeDAO.update(penalidade);
            } else {
                penalidadeDAO.save(penalidade);
            }
            JsfUtil.addSuccessMessage("Penalidade salvo com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao salvar penalidade");
            return null;
        }
    }

    public String excluir() {
        try {
            penalidadeDAO.delete(penalidadeSelecionada);
            penalidadeSelecionada = null;
            JsfUtil.addSuccessMessage("Penalidade removido com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao excluir penalidade");
            return null;
        }
    }

    public String prepararLista() {
        newInstances();
        if (penalidadeSelecionada != null) penalidadeSelecionada = null;
        return "lista";
    }

    public String prepararEdicao() {
        newInstances();
        penalidade = penalidadeSelecionada;
        header = "Editar Penalidade";
        edicao = true;
        visualizar = false;
        return "edita";
    }

    public String prepararNovo() {
        newInstances();
        header = "Cadastro de Penalidade";
        edicao = false;
        visualizar = false;
        return "edita";
    }

    public String prepararVer() {
        newInstances();
        penalidade = penalidadeSelecionada;
        visualizar = true;
        edicao = false;
        header = "Visualizar Penalidade";
        return "edita";
    }

    private void newInstances() {
        this.penalidade = new Penalidade();
        this.penalidadeDAO = new PenalidadeDAOImpl();
    }

    public List<Penalidade> getPenalidades() {
        return penalidadeDAO.listAll();
    }

    public Penalidade getPenalidade() {
        return penalidade;
    }

    public void setPenalidade(Penalidade penalidade) {
        this.penalidade = penalidade;
    }

    public PenalidadeDAOImpl getPenalidadeDAO() {
        return penalidadeDAO;
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

    public void setPenalidadeDAO(PenalidadeDAOImpl penalidadeDAO) {
        this.penalidadeDAO = penalidadeDAO;
    }

    public List<SelectItem> getTipoPenalidade() {
        List<SelectItem> toReturn = new ArrayList<SelectItem>();
        for (TipoPenalidade tp : TipoPenalidade.values()) {
            toReturn.add(new SelectItem(tp, tp.getDescricao()));
        }
        return toReturn;
    }

    public boolean isEdicao() {
        return edicao;
    }

    public void setEdicao(boolean edicao) {
        this.edicao = edicao;
    }

    public Penalidade getPenalidadeSelecionada() {
        return penalidadeSelecionada;
    }

    public void setPenalidadeSelecionada(Penalidade penalidadeSelecionada) {
        this.penalidadeSelecionada = penalidadeSelecionada;
    }

}
