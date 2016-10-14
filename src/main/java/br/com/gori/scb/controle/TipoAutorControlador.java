package br.com.gori.scb.controle;

import br.com.gori.scb.controle.util.JsfUtil;
import br.com.gori.scb.entidade.TipoAutor;
import br.com.gori.scb.dao.impl.TipoAutorDAOImpl;
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
public class TipoAutorControlador implements Serializable {

    private TipoAutorDAOImpl tipoAutorDAO;
    private TipoAutor tipoAutor;
    private TipoAutor tipoAutorSelecionado;
    private boolean visualizar;
    private String header;
    private boolean edicao;

    public TipoAutorControlador() {
        newInstances();
    }

    private void newInstances() {
        this.tipoAutor = new TipoAutor();
        this.tipoAutorDAO = new TipoAutorDAOImpl();
    }

    public String salvar() {
        try {
            if (edicao) {
                tipoAutorDAO.update(tipoAutor);
            } else {
                tipoAutorDAO.save(tipoAutor);
            }
            JsfUtil.addSuccessMessage("Tipo de Autor salvo com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao salvar tipo de autor");
            return null;
        }
    }

    public String excluir() {
        try {
            tipoAutorDAO.delete(tipoAutorSelecionado);
            tipoAutorSelecionado = null;
            JsfUtil.addSuccessMessage("Tipo de Autor removido com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao excluir tipo de autor");
            return null;
        }
    }

    public String prepararLista() {
        newInstances();
        if (tipoAutorSelecionado != null) tipoAutorSelecionado = null;
        return "lista";
    }

    public String prepararEdicao() {
        newInstances();
        tipoAutor = tipoAutorSelecionado;
        header = "Editar Tipo de Autor";
        visualizar = false;
        edicao = true;
        return "edita";
    }

    public String prepararNovo() {
        newInstances();
        header = "Cadastrar Tipo de Autor";
        visualizar = false;
        edicao = false;
        return "edita";
    }

    public String prepararVer() {
        newInstances();
        tipoAutor = tipoAutorSelecionado;
        visualizar = true;
        edicao = false;
        header = "Visualizar Tipo de Autor";
        return "edita";
    }

    public List<TipoAutor> getTipoAutores() {
        return tipoAutorDAO.listAll();
    }

    public TipoAutor getTipoAutor() {
        return tipoAutor;
    }

    public void setTipoAutor(TipoAutor tipoAutor) {
        this.tipoAutor = tipoAutor;
    }

    public TipoAutorDAOImpl getTipoAutorDAO() {
        return tipoAutorDAO;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public boolean isVisualizar() {
        return visualizar;
    }

    public void setVisualizar(boolean visualizar) {
        this.visualizar = visualizar;
    }

    public boolean isEdicao() {
        return edicao;
    }

    public void setEdicao(boolean edicao) {
        this.edicao = edicao;
    }

    public void setTipoAutorDAO(TipoAutorDAOImpl tipoAutorDAO) {
        this.tipoAutorDAO = tipoAutorDAO;
    }

    public TipoAutor getTipoAutorSelecionado() {
        return tipoAutorSelecionado;
    }

    public void setTipoAutorSelecionado(TipoAutor tipoAutorSelecionado) {
        this.tipoAutorSelecionado = tipoAutorSelecionado;
    }
    
    

}
