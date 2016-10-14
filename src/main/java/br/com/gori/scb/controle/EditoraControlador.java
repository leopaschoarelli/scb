package br.com.gori.scb.controle;

import br.com.gori.scb.controle.util.JsfUtil;
import br.com.gori.scb.entidade.Editora;
import br.com.gori.scb.dao.impl.EditoraDAOImpl;
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
public class EditoraControlador implements Serializable {

    private Editora editora;
    private Editora editoraSelecionada;
    private EditoraDAOImpl editoraDAO;
    private boolean visualizar;
    private boolean edicao;
    private String header;

    public EditoraControlador() {
        newInstances();
    }

    private void newInstances() {
        this.editora = new Editora();
        this.editoraDAO = new EditoraDAOImpl();
    }

    public String salvar() {
        try {
            if (edicao) {
                editoraDAO.update(editora);
            } else {
                editoraDAO.save(editora);
            }
            JsfUtil.addSuccessMessage("Editora salva com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao salvar editora");
            return null;
        }
    }

    public String excluir() {
        try {
            editoraDAO.delete(editoraSelecionada);
            editoraSelecionada = null;
            JsfUtil.addSuccessMessage("Editora removida com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao excluir editora");
            return null;
        }
    }

    public String prepararLista() {
        newInstances();
        if (editoraSelecionada != null) editoraSelecionada = null;
        return "lista";
    }

    public String prepararEdicao() {
        newInstances();
        editora = editoraSelecionada;
        edicao = true;
        header = "Editar Editora";
        visualizar = false;
        return "edita";
    }

    public String prepararNovo() {
        newInstances();
        visualizar = false;
        edicao = false;
        header = "Cadastrar Editora";
        return "edita";
    }

    public String prepararVer() {
        newInstances();
        editora = editoraSelecionada;
        visualizar = true;
        edicao = false;
        header = "Visualizar Editora";
        return "edita";
    }
    
    public List<Editora> getEditoras(){
        return editoraDAO.listAll();
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public EditoraDAOImpl getEditoraDAO() {
        return editoraDAO;
    }

    public void setEditoraDAO(EditoraDAOImpl editoraDAO) {
        this.editoraDAO = editoraDAO;
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

    public Editora getEditoraSelecionada() {
        return editoraSelecionada;
    }

    public void setEditoraSelecionada(Editora editoraSelecionada) {
        this.editoraSelecionada = editoraSelecionada;
    }
    
    
}
