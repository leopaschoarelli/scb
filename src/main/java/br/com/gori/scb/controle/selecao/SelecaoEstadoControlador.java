package br.com.gori.scb.controle.selecao;

import br.com.gori.scb.entidade.Estado;
import br.com.gori.scb.dao.impl.EstadoDAOImpl;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Leonardo
 */
@ManagedBean
@SessionScoped
public class SelecaoEstadoControlador implements Serializable {

    private EstadoDAOImpl estadoDAO;
    private String nome;

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("dynamic", true);
        opcoes.put("responsive", true);

        RequestContext.getCurrentInstance().openDialog("pesqEstado", opcoes, null);
    }

    public void selecionar(Estado estado) {
        RequestContext.getCurrentInstance().closeDialog(estado);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public EstadoDAOImpl getEstadoDAO() {
        return estadoDAO;
    }

    public void setEstadoDAO(EstadoDAOImpl estadoDAO) {
        this.estadoDAO = estadoDAO;
    }

    public List<Estado> getEstado() {
        this.estadoDAO = new EstadoDAOImpl();
        return estadoDAO.listAll();
    }

  
}
