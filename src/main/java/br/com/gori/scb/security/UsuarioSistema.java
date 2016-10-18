package br.com.gori.scb.security;

import br.com.gori.scb.dao.impl.UserDAOImpl;
import br.com.gori.scb.entidade.User;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class UsuarioSistema {

    private br.com.gori.scb.entidade.User usuarioLogado = null;
    
    public String getNomeUsuario(){
        getUsuarioLogado();
        return usuarioLogado.getUsername();
    }

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
}
