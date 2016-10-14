package br.com.gori.scb.controle;

import br.com.gori.scb.controle.util.JsfUtil;
import br.com.gori.scb.entidade.Estado;
import br.com.gori.scb.entidade.Pais;
import br.com.gori.scb.dao.impl.EstadoDAOImpl;
import br.com.gori.scb.dao.impl.PaisDAOImpl;
import br.com.gori.scb.util.ConverterAutoComplete;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Leonardo
 */
@ManagedBean
@SessionScoped
public class EstadoControlador implements Serializable {

    private Estado estado;
    private Estado estadoSelecionado;
    private EstadoDAOImpl estadoDAO;
    private boolean visualiza;
    private boolean edicao;
    private String header;
    private PaisDAOImpl paisDAO;
    private ConverterAutoComplete convertPais;

    public EstadoControlador() {
        newInstances();
    }

    private void newInstances() {
        this.estado = new Estado();
        this.estadoDAO = new EstadoDAOImpl();
        this.paisDAO = new PaisDAOImpl();
    }

    public String salvar() {
        try {
            if (edicao) {
                estadoDAO.update(estado);
            } else {
                estadoDAO.save(estado);
            }
            JsfUtil.addSuccessMessage("Estado salvo com sucesso!");
            return prepararLista();
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Erro ao salvar estado: " + ex.getMessage());
            return null;
        }
    }

    public String excluir() {
        try {
            estadoDAO.delete(estadoSelecionado);
            estadoSelecionado = null;
            JsfUtil.addSuccessMessage("Estado removido com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao excluir estado");
            return null;
        }
    }

    public String prepararLista() {
        newInstances();
        if (estadoSelecionado != null) estadoSelecionado = null;
        return "lista";
    }

    public String prepararEdicao() {
        newInstances();
        estado = estadoSelecionado;
        visualiza = false;
        edicao = true;
        header = "Editar Estado";
        return "edita";
    }

    public String prepararNovo() {
        newInstances();
        visualiza = false;
        edicao = false;
        header = "Cadastrar Estado";
        return "edita";
    }

    public String prepararVer() {
        newInstances();
        estado = estadoSelecionado;
        edicao = false;
        header = "Visualizar Estado";
        visualiza = true;
        return "edita";
    }

    public List<Estado> getEstados() {
        return estadoDAO.listAll();
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public EstadoDAOImpl getEstadoDAO() {
        return estadoDAO;
    }

    public boolean isVisualiza() {
        return visualiza;
    }

    public void setVisualiza(boolean visualiza) {
        this.visualiza = visualiza;
    }

    public String getHeader() {
        return header;
    }

    public List<Pais> completaPais(String value) {
        return estadoDAO.getPaises(value.toLowerCase());
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public PaisDAOImpl getPaisDAO() {
        return paisDAO;
    }

    public void setPaisDAO(PaisDAOImpl paisDAO) {
        this.paisDAO = paisDAO;
    }

    public String getNomePais() {
        return estado.getPais() == null ? null : estado.getPais().getNome();
    }

    public void setNomePais(String nome) {
    }

    public ConverterAutoComplete getConvertPais() {
        if (convertPais == null) {
            convertPais = new ConverterAutoComplete(Pais.class, paisDAO);
        }
        return convertPais;
    }

    public void setConvertPais(ConverterAutoComplete convertPais) {
        this.convertPais = convertPais;
    }

    public void paisSelecionado(SelectEvent event) {
        Pais pais = (Pais) event.getObject();
        estado.setPais(pais);
    }

    public boolean isEdicao() {
        return edicao;
    }

    public void setEdicao(boolean edicao) {
        this.edicao = edicao;
    }

    public Estado getEstadoSelecionado() {
        return estadoSelecionado;
    }

    public void setEstadoSelecionado(Estado estadoSelecionado) {
        this.estadoSelecionado = estadoSelecionado;
    }

}
