package br.com.gori.scb.controle.selecao;

import br.com.gori.scb.entidade.TipoAutor;
import br.com.gori.scb.dao.impl.TipoAutorDAOImpl;
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
public class SelecaoTpAutorControlador implements Serializable {

    private TipoAutorDAOImpl tipoAutorDAO;
    private String nome;

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("dynamic", true);
        opcoes.put("closeOnEscape", true);

        RequestContext.getCurrentInstance().openDialog("pesqTpAutor", opcoes, null);
    }

    public void selecionar(TipoAutor tipoAutor) {
        RequestContext.getCurrentInstance().closeDialog(tipoAutor);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoAutorDAOImpl getTipoAutorDAO() {
        return tipoAutorDAO;
    }

    public void setTipoAutorDAO(TipoAutorDAOImpl tipoAutorDAO) {
        this.tipoAutorDAO = tipoAutorDAO;
    }

    public List<TipoAutor> getTipoAutores() {
        this.tipoAutorDAO = new TipoAutorDAOImpl();
        return tipoAutorDAO.listAll();
    }

}
