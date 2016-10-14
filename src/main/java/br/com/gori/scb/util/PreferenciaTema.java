/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gori.scb.util;

import java.io.Serializable;
import java.util.Map;
import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Leonardo
 */
@ManagedBean
@SessionScoped
public class PreferenciaTema implements Serializable {

    private String tema = "bootstrap";

    public String getTema() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (params.containsKey("tema")) {
            tema = params.get("tema");
        }
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

}
