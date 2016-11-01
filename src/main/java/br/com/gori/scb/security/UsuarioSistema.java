package br.com.gori.scb.security;

import br.com.gori.scb.controle.util.JsfUtil;
import br.com.gori.scb.dao.impl.UserDAOImpl;
import br.com.gori.scb.entidade.User;
import br.com.gori.scb.entidade.util.Sexo;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

@ManagedBean
@SessionScoped
public class UsuarioSistema {

    private boolean visualiza;
    private User usuario;
    private UserDAOImpl usuarioDAO;

    public UsuarioSistema() {
        getUsuarioLogado();
        this.visualiza = true;
        this.usuario = new User();
        this.usuarioDAO = new UserDAOImpl();
    }

    public String salvar() {
        try {
            usuario = usuarioLogado;
            usuarioDAO.update(usuario);
            JsfUtil.addSuccessMessage("Informações de usuário salvo com sucesso!");
            this.usuario = new User();
            return null;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao salvar informações do usuário ");
            return null;
        }
    }

    private br.com.gori.scb.entidade.User usuarioLogado = null;

//    public String getNomeUsuario(){
//        getUsuarioLogado();
//        return usuarioLogado.getName();
//    }
    public br.com.gori.scb.entidade.User getUsuarioLogado() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext external = context.getExternalContext();
        String login = external.getRemoteUser();

        if (this.usuarioLogado == null || !login.equals(this.usuarioLogado.getUsername())) {

            if (login != null) {
                UserDAOImpl userDAO = new UserDAOImpl();
                this.usuarioLogado = userDAO.getLogin(login);
            }
        }
        return usuarioLogado;
    }

    public boolean isVisualiza() {
        return visualiza;
    }

    public void setVisualiza(boolean visualiza) {
        this.visualiza = visualiza;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public UserDAOImpl getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UserDAOImpl usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public List<SelectItem> getSexo() {
        List<SelectItem> toReturn = new ArrayList<SelectItem>();
        for (Sexo sx : Sexo.values()) {
            toReturn.add(new SelectItem(sx, sx.getDescricao()));
        }
        return toReturn;
    }
}
