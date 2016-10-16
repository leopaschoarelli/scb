package br.com.gori.scb.controle;

import br.com.gori.scb.controle.util.JsfUtil;
import br.com.gori.scb.entidade.Turno;
import br.com.gori.scb.dao.impl.TurnoDAOImpl;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Leonardo
 */
@ManagedBean
@SessionScoped
public class TurnoControlador implements Serializable {

    private Turno turno;
    private Turno turnoSelecionado;
    private TurnoDAOImpl turnoDAO;
    private boolean edicao;
    private boolean visualiza;

    public TurnoControlador() {
        newInstances();
    }

    private void newInstances() {
        this.turno = new Turno();
        this.turnoDAO = new TurnoDAOImpl();
    }

    public String salvar() {
        try {
            if (edicao) {
                System.out.println("Update");
                turnoDAO.update(turno);
            } else {
                System.out.println("Save");
                turnoDAO.save(turno);
            }
            JsfUtil.addSuccessMessage("Turno salvo com sucesso!");
            System.out.println("Retornar");
            visualiza = true;
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao salvar turno");
            return null;
        }
    }

    public String excluir() {
        try {
            turnoDAO.delete(turnoSelecionado);
            turnoSelecionado = null;
            JsfUtil.addSuccessMessage("Turno removido com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao excluir turno");
            return null;
        }
    }

    public String prepararLista() {
        newInstances();
        if (turnoSelecionado != null) {
            turnoSelecionado = null;
        }
        return "lista";
    }

    public String prepararEdicao() {
        newInstances();
        turno = turnoSelecionado;
        edicao = true;
        visualiza = false;
        return "edita";
    }

    public String prepararNovo() {
        newInstances();
        edicao = true;
        visualiza = false;
        return "edita";
    }

    public String prepararVer() {
        newInstances();
        turno = turnoSelecionado;
        edicao = false;
        visualiza = true;
        return "edita";
    }

    public void reset() {
        RequestContext.getCurrentInstance().reset("form-user:panelTurno");
    }

    public List<Turno> getTurnos() {
        return turnoDAO.listAll();
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public TurnoDAOImpl getTurnoDAO() {
        return turnoDAO;
    }

    public void setTurnoDAO(TurnoDAOImpl turnoDAO) {
        this.turnoDAO = turnoDAO;
    }

    public boolean isEdicao() {
        return edicao;
    }

    public void setEdicao(boolean edicao) {
        this.edicao = edicao;
    }

    public boolean isVisualiza() {
        return visualiza;
    }

    public void setVisualiza(boolean visualiza) {
        this.visualiza = visualiza;
    }

    public Turno getTurnoSelecionado() {
        return turnoSelecionado;
    }

    public void setTurnoSelecionado(Turno turnoSelecionado) {
        this.turnoSelecionado = turnoSelecionado;
    }

}
