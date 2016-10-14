package br.com.gori.scb.controle;

import br.com.gori.scb.controle.util.JsfUtil;
import br.com.gori.scb.entidade.Instituicao;
import br.com.gori.scb.dao.impl.InstituicaoDAOImpl;
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
public class InstituicaoControlador implements Serializable {

    private Instituicao instituicao;
    private Instituicao instituicaoSelecionada;
    private InstituicaoDAOImpl instituicaoDAO;
    private String header;
    private boolean edicao;
    private boolean visualizar;

    public InstituicaoControlador() {
        newInstances();
    }

    private void newInstances() {
        this.instituicao = new Instituicao();
        this.instituicaoDAO = new InstituicaoDAOImpl();
    }

    public String salvar() {
        try {
            if (edicao) {
                instituicaoDAO.update(instituicao);
            } else {
                instituicaoDAO.save(instituicao);
            }
            JsfUtil.addSuccessMessage("Instituição salva com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao salvar instituição");
            return null;
        }
    }

    public String excluir() {
        try {
            instituicaoDAO.delete(instituicaoSelecionada);
            instituicaoSelecionada = null;
            JsfUtil.addSuccessMessage("Instituição removido com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao excluir instituição");
            return null;
        }
    }

    public String prepararLista() {
        newInstances();
        if (instituicaoSelecionada != null) {
            instituicaoSelecionada = null;
        }
        return "lista";
    }

    public String prepararEdicao() {
        newInstances();
        instituicao = instituicaoSelecionada;
        edicao = true;
        visualizar = false;
        this.header = "Editar Instituição";
        return "edita";
    }

    public String prepararNovo() {
        newInstances();
        edicao = false;
        visualizar = false;
        this.header = "Cadastrar Instituição";
        return "edita";
    }

    public String prepararVer() {
        newInstances();
        instituicao = instituicaoSelecionada;
        edicao = false;
        visualizar = true;
        this.header = "Visualizar Instituição";
        return "edita";
    }

    public List<Instituicao> getInstituicoes() {
        return instituicaoDAO.listAll();
    }

    public Instituicao getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }

    public InstituicaoDAOImpl getInstituicaoDAO() {
        return instituicaoDAO;
    }

    public void setInstituicaoDAO(InstituicaoDAOImpl instituicaoDAO) {
        this.instituicaoDAO = instituicaoDAO;
    }

    public boolean isEdicao() {
        return edicao;
    }

    public void setEdicao(boolean edicao) {
        this.edicao = edicao;
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

    public Instituicao getInstituicaoSelecionada() {
        return instituicaoSelecionada;
    }

    public void setInstituicaoSelecionada(Instituicao instituicaoSelecionada) {
        this.instituicaoSelecionada = instituicaoSelecionada;
    }

}
