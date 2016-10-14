package br.com.gori.scb.controle.selecao;

import br.com.gori.scb.entidade.Pais;
import br.com.gori.scb.dao.impl.PaisDAOImpl;
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
public class SelecaoPaisControlador implements Serializable {

    private PaisDAOImpl paisDAO;
    private String nome;

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("resizable", false);
        opcoes.put("responsive", true);
        opcoes.put("contentWidth", 330);

        RequestContext.getCurrentInstance().openDialog("pesqPais", opcoes, null);
    }

    public void selecionar(Pais pais) {
        RequestContext.getCurrentInstance().closeDialog(pais);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public PaisDAOImpl getPaisDAO() {
        return paisDAO;
    }

    public void setPaisDAO(PaisDAOImpl paisDAO) {
        this.paisDAO = paisDAO;
    }

    public List<Pais> getPais() {
        this.paisDAO = new PaisDAOImpl();
        return paisDAO.listAll();
    }

}
