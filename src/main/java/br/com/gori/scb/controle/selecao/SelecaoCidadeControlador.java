package br.com.gori.scb.controle.selecao;

import br.com.gori.scb.entidade.Cidade;
import br.com.gori.scb.dao.impl.CidadeDAOImpl;
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
public class SelecaoCidadeControlador implements Serializable {

    private CidadeDAOImpl cidadeDAO;
    private String nome;
    
    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("dynamic", true);
        opcoes.put("closeOnEscape", true);

        RequestContext.getCurrentInstance().openDialog("pesqCidade", opcoes, null);
    }

    public void selecionar(Cidade cidade) {
        RequestContext.getCurrentInstance().closeDialog(cidade);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CidadeDAOImpl getCidadeDAO() {
        return cidadeDAO;
    }

    public void setCidadeDAO(CidadeDAOImpl cidadeDAO) {
        this.cidadeDAO = cidadeDAO;
    }
    
    public List<Cidade> getCidades() {
        this.cidadeDAO = new CidadeDAOImpl();
        return cidadeDAO.listAll();
    }


}
