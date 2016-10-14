package br.com.gori.scb.controle;

import br.com.gori.scb.controle.util.JsfUtil;
import br.com.gori.scb.entidade.Cidade;
import br.com.gori.scb.entidade.Estado;
import br.com.gori.scb.dao.impl.CidadeDAOImpl;
import br.com.gori.scb.dao.impl.EstadoDAOImpl;
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
public class CidadeControlador implements Serializable {

    private Cidade cidade;
    private Cidade cidadeSelecionada;
    private CidadeDAOImpl cidadeDAO;
    private boolean visualiza;
    private boolean edicao;
    private EstadoDAOImpl estadoDAO;
    private Estado estado;
    private String header;
    private ConverterAutoComplete converterEstado;

    public CidadeControlador() {
        newInstances();
    }

    private void newInstances() {
        this.cidade = new Cidade();
        this.cidadeDAO = new CidadeDAOImpl();
        this.estadoDAO = new EstadoDAOImpl();
    }

    public String salvar() {
        try {
            if (edicao) {
                cidadeDAO.update(cidade);
            } else {
                cidadeDAO.save(cidade);
            }
            JsfUtil.addSuccessMessage("Cidade salva com sucesso!");
            return prepararLista();
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Erro ao salvar cidade: " + ex.getMessage());
            return null;
        }
    }

    public String excluir() {
        try {
            cidadeDAO.delete(cidadeSelecionada);
            cidadeSelecionada = null;
            JsfUtil.addSuccessMessage("Cidade removida com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao excluir cidade");
            return null;
        }
    }

    public String prepararLista() {
        newInstances();
        if (cidadeSelecionada != null) cidadeSelecionada = null; 
        return "lista";
    }

    public String prepararEdicao() {
        newInstances();
        cidade = cidadeSelecionada;
        visualiza = false;
        edicao = true;
        header = "Editar Cidade";
        return "edita";
    }

    public String prepararNovo() {
        newInstances();
        visualiza = false;
        edicao = false;
        header = "Cadastrar Cidade";
        return "edita";
    }

    public String prepararVer() {
        newInstances();
        cidade = cidadeSelecionada;
        edicao = false;
        header = "Visualizar Cidade";
        visualiza = true;
        return "edita";
    }

    public List<Cidade> getCidades() {
        return cidadeDAO.listAll();
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public CidadeDAOImpl getCidadeDAO() {
        return cidadeDAO;
    }

    public void setCidadeDAO(CidadeDAOImpl cidadeDAO) {
        this.cidadeDAO = cidadeDAO;
    }

    public boolean isVisualiza() {
        return visualiza;
    }

    public void setVisualiza(boolean visualiza) {
        this.visualiza = visualiza;
    }

    public EstadoDAOImpl getEstadoDAO() {
        return estadoDAO;
    }

    public void setEstadoDAO(EstadoDAOImpl estadoDAO) {
        this.estadoDAO = estadoDAO;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getNomeEstado() {
        return cidade.getEstado() == null ? null : cidade.getEstado().getNome();
    }

    public void setNomeEstado(String nome) {
    }

    public void estadoSelecionado(SelectEvent event) {
        Estado estado = (Estado) event.getObject();
        cidade.setEstado(estado);
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public ConverterAutoComplete getConverterEstado() {
        if (converterEstado == null) {
            converterEstado = new ConverterAutoComplete(Estado.class, estadoDAO);
        }
        return converterEstado;
    }

    public List<Estado> completaEstado(String value) {
        return cidadeDAO.getEstados(value.toLowerCase());
    }

    public void setConverterEstado(ConverterAutoComplete converterEstado) {
        this.converterEstado = converterEstado;
    }

    public boolean isEdicao() {
        return edicao;
    }

    public void setEdicao(boolean edicao) {
        this.edicao = edicao;
    }

    public Cidade getCidadeSelecionada() {
        return cidadeSelecionada;
    }

    public void setCidadeSelecionada(Cidade cidadeSelecionada) {
        this.cidadeSelecionada = cidadeSelecionada;
    }

}
