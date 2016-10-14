package br.com.gori.scb.controle.util;

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
public class Suporte implements Serializable {

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("dynamic", true);
        opcoes.put("closeOnEscape", true);

        RequestContext.getCurrentInstance().openDialog("dialogos/Suporte", opcoes, null);
    }
}
