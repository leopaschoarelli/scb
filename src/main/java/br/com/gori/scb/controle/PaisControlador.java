package br.com.gori.scb.controle;

import br.com.gori.scb.controle.util.JsfUtil;
import br.com.gori.scb.entidade.Pais;
import br.com.gori.scb.dao.impl.PaisDAOImpl;
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
public class PaisControlador implements Serializable {

    private Pais pais;
    private Pais paisSelecionado;
    private PaisDAOImpl paisDAO;
    private boolean visualizar;
    private boolean edicao;
    private String header;

    public PaisControlador() {
        newInstances();
    }

    public String salvar() {
        try {
            if (edicao) {
                paisDAO.update(pais);
            } else {
                paisDAO.save(pais);
            }
            JsfUtil.addSuccessMessage("País salvo com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao salvar país");
            return null;
        }
    }

    public String excluir() {
        try {
            paisDAO.delete(paisSelecionado);
            paisSelecionado = null;
            JsfUtil.addSuccessMessage("País removido com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao excluir país");
            return null;
        }
    }

    public String prepararLista() {
        newInstances();
        if (paisSelecionado != null) paisSelecionado = null;
        return "lista";
    }


    public String prepararEdicao() {
        newInstances();
        pais = paisSelecionado;
        header = "Editar País";
        edicao = true;
        visualizar = false;
        return "edita";
    }

    public String prepararNovo() {
        newInstances();
        header = "Cadastro de País";
        edicao = false;
        visualizar = false;
        return "edita";
    }

    public String prepararVer() {
        newInstances();
        pais = paisSelecionado;
        visualizar = true;
        edicao = false;
        header = "Visualizar País";
        return "edita";
    }

    private void newInstances() {
        this.pais = new Pais();
        this.paisDAO = new PaisDAOImpl();
    }

    public List<Pais> getPaises() {
        return paisDAO.listAll();
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public PaisDAOImpl getPaisDAO() {
        return paisDAO;
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

    public boolean isEdicao() {
        return edicao;
    }

    public void setEdicao(boolean edicao) {
        this.edicao = edicao;
    }

    public void setPaisDAO(PaisDAOImpl paisDAO) {
        this.paisDAO = paisDAO;
    }

    public Pais getPaisSelecionado() {
        return paisSelecionado;
    }

    public void setPaisSelecionado(Pais paisSelecionado) {
        this.paisSelecionado = paisSelecionado;
    }

}
