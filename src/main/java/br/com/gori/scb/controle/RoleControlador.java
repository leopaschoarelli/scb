package br.com.gori.scb.controle;

import br.com.gori.scb.controle.util.JsfUtil;
import br.com.gori.scb.dao.impl.RoleDAOImpl;
import br.com.gori.scb.entidade.Role;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Leonardo
 */
@ManagedBean
@SessionScoped
public class RoleControlador implements Serializable {

    private Role role;
    private Role roleSelecionada;
    private RoleDAOImpl roleDAO;
    private boolean edicao;
    private String header;
    private boolean visualiza;

    public RoleControlador() {
        newInstances();
    }

    private void newInstances() {
        this.role = new Role();
        this.roleDAO = new RoleDAOImpl();
    }

    public String salvar() {
        try {
            if (edicao) {
                roleDAO.update(role);
            } else {
                roleDAO.save(role);
            }
            JsfUtil.addSuccessMessage("Role salvo com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao salvar role");
            return null;
        }
    }

    public String excluir() {
        try {
            roleDAO.delete(roleSelecionada);
            roleSelecionada = null;
            JsfUtil.addSuccessMessage("Role removido com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao excluir role");
            return null;
        }
    }

    public String prepararLista() {
        newInstances();
        if (roleSelecionada != null) {
            roleSelecionada = null;
        }
        return "lista";
    }

    public String prepararEdicao() {
        newInstances();
        role = roleSelecionada;
        edicao = true;
        header = "Editar Role";
        visualiza = false;
        return "edita";
    }

    public String prepararNovo() {
        newInstances();
        edicao = false;
        visualiza = false;
        header = "Cadastrar Role";
        return "edita";
    }

    public String prepararVer() {
        newInstances();
        role = roleSelecionada;
        edicao = false;
        visualiza = true;
        header = "Visualizar Role";
        return "edita";
    }

    public List<Role> getRoles() {
        return roleDAO.listAll();
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRoleSelecionada() {
        return roleSelecionada;
    }

    public void setRoleSelecionada(Role roleSelecionada) {
        this.roleSelecionada = roleSelecionada;
    }

    public RoleDAOImpl getRoleDAO() {
        return roleDAO;
    }

    public void setRoleDAO(RoleDAOImpl roleDAO) {
        this.roleDAO = roleDAO;
    }

    public boolean isEdicao() {
        return edicao;
    }

    public void setEdicao(boolean edicao) {
        this.edicao = edicao;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public boolean isVisualiza() {
        return visualiza;
    }

    public void setVisualiza(boolean visualiza) {
        this.visualiza = visualiza;
    }

    
}
