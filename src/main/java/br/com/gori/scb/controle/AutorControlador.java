package br.com.gori.scb.controle;

import br.com.gori.scb.controle.util.JsfUtil;
import br.com.gori.scb.entidade.Autor;
import br.com.gori.scb.dao.impl.AutorDAOImpl;
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
public class AutorControlador implements Serializable {

    private AutorDAOImpl autorDAO;
    private Autor autor;
    private Autor autorSelecionado;
    private boolean visualizar;
    private boolean edicao;
    private String header;

    public AutorControlador() {
        newInstances();
    }

    private void newInstances() {
        this.autor = new Autor();
        this.autorDAO = new AutorDAOImpl();
    }

    public String salvar() {
        try {
            if (edicao) {
                autorDAO.update(autor);
            } else {
                autorDAO.save(autor);
            }
            JsfUtil.addSuccessMessage("Autor salvo com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao salvar autor");
            return null;
        }
    }

    public String excluir() {
        try {
            autorDAO.delete(autorSelecionado);
            autorSelecionado = null;
            JsfUtil.addSuccessMessage("Autor removido com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao excluir autor");
            return null;
        }
    }

    public String prepararLista() {
        newInstances();
        if (autorSelecionado != null) autorSelecionado = null;
        return "lista";
    }

    public String prepararEdicao() {
        newInstances();
        autor = autorSelecionado;
        header = "Editar Autor";
        edicao = true;
        visualizar = false;
        return "edita";
    }

    public String prepararNovo() {
        newInstances();
        header = "Cadastrar Autor";
        visualizar = false;
        edicao = false;
        return "edita";
    }

    public String prepararVer() {
        newInstances();
        autor = autorSelecionado;
        visualizar = true;
        edicao = false;
        header = "Visualizar Autor";
        return "edita";
    }

    public List<Autor> getAutores() {
        return autorDAO.listAll();
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public AutorDAOImpl getAutorDAO() {
        return autorDAO;
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

    public void setAutorDAO(AutorDAOImpl autorDAO) {
        this.autorDAO = autorDAO;
    }

    public Autor getAutorSelecionado() {
        return autorSelecionado;
    }

    public void setAutorSelecionado(Autor autorSelecionado) {
        this.autorSelecionado = autorSelecionado;
    }
    
}
