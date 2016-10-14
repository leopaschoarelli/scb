package br.com.gori.scb.controle.selecao;

import java.io.Serializable;
import java.util.HashMap;
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
public class EtiquetaControlador implements Serializable {

    private String nome;

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("resizable", false);
        opcoes.put("dynamic", true);

        RequestContext.getCurrentInstance().openDialog("etiqueta", opcoes, null);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    

}
