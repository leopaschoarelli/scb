package br.com.gori.scb.security;

import br.com.gori.scb.dao.impl.PessoaDAOImpl;
import br.com.gori.scb.dao.impl.UserDAOImpl;
import br.com.gori.scb.entidade.Pessoa;
import br.com.gori.scb.entidade.User;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class UsuarioSistema {

    private boolean visualiza;
    
    public UsuarioSistema(){
        getUsuarioLogado();
        this.visualiza = true;
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

}
