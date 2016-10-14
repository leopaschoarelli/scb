package br.com.gori.scb.controle;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Leonardo
 */
@ManagedBean
@SessionScoped
public class MenuControlador implements Serializable {

    public MenuControlador() {
    }

    public String listarPais() {
        return "/pages/private/admin/endereco/pais/lista.xhtml";
    }

    public String listarEstado() {
        return "/pages/private/admin/endereco/estado/lista.xhtml";
    }

    public String listarCidade() {
        return "/pages/private/admin/endereco/cidade/lista.xhtml";
    }

    public String listarPessoa() {
        return "/pages/private/admin/pessoa/lista.xhtml";
    }

    public String listarTipoPessoa() {
        return "/pages/private/admin/pessoa/lista.xhtml";
    }

    public String listarTurma() {
        return "/pages/private/admin/turma/lista.xhtml";
    }

    public String listarUsuario() {
        return "/pages/private/admin/usuario/lista.xhtml";
    }

    public String listarTurno() {
        return "/pages/private/admin/turno/lista.xhtml";
    }

    public String mostrarInicio() {
        return "/pages/private/indexscb.xhtml";
    }

    public String realizarLogin() {
        return "/pages/private/indexscb.xhtml";
    }
}
