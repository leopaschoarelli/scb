package br.com.gori.scb.controle;

import br.com.gori.scb.controle.util.JsfUtil;
import br.com.gori.scb.entidade.Turma;
import br.com.gori.scb.entidade.Turno;
import br.com.gori.scb.entidade.util.Modalidade;
import br.com.gori.scb.dao.impl.TurmaDAOImpl;
import br.com.gori.scb.dao.impl.TurnoDAOImpl;
import br.com.gori.scb.util.ConverterAutoComplete;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Leonardo
 */
@ManagedBean
@SessionScoped
public class TurmaControlador implements Serializable {

    private Turma turma;
    private Turma turmaSelecionada;
    private TurmaDAOImpl turmaDAO;
    private TurnoDAOImpl turnoDAO;
    private Turno turno;
    private ConverterAutoComplete converterTurno;
    private boolean edicao;
    private boolean visualiza;

    public TurmaControlador() {
        newInstances();
    }

    private void newInstances() {
        this.turma = new Turma();
        this.turmaDAO = new TurmaDAOImpl();
        this.turnoDAO = new TurnoDAOImpl();
    }

    public String salvar() {
        try {
            if (edicao) {
                turmaDAO.update(turma);
            } else {
                turmaDAO.save(turma);
            }
            JsfUtil.addSuccessMessage("Turma salva com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao salvar turma");
            return null;
        }
    }

    public String excluir() {
        try {
            turmaDAO.delete(turmaSelecionada);
            turmaSelecionada = null;
            JsfUtil.addSuccessMessage("Turma removida com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao excluir turma");
            return null;
        }
    }

    public String prepararLista() {
        newInstances();
        if (turmaSelecionada != null) turmaSelecionada = null;
        return "lista";
    }

    public String prepararEdicao() {
        newInstances();
        turma = turmaSelecionada;
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
        turma = turmaSelecionada;
        edicao = false;
        visualiza = true;
        return "edita";
    }

    public List<Turma> getTurmas() {
        return turmaDAO.listAll();
    }

    public List<SelectItem> getTipoModalidade() {
        List<SelectItem> toReturn = new ArrayList<SelectItem>();
        for (Modalidade tp : Modalidade.values()) {
            toReturn.add(new SelectItem(tp, tp.getDescricao()));
        }
        return toReturn;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurmaDAO(TurmaDAOImpl turmaDAO) {
        this.turmaDAO = turmaDAO;
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

    public TurnoDAOImpl getTurnoDAO() {
        return turnoDAO;
    }

    public void setTurnoDAO(TurnoDAOImpl turnoDAO) {
        this.turnoDAO = turnoDAO;
    }

    public String getNomeTurno() {
        return turma.getTurno() == null ? null : turma.getTurno().getDescricao();
    }

    public void setNomeTurno(String nome) {
    }

    public void turnoSelecionado(SelectEvent event) {
        Turno turno = (Turno) event.getObject();
        turma.setTurno(turno);
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public ConverterAutoComplete getConverterTurno() {
        if (converterTurno == null) {
            converterTurno = new ConverterAutoComplete(Turno.class, turnoDAO);
        }
        return converterTurno;
    }

    public List<Turno> completaTurno(String value) {
        return turmaDAO.getTurnos(value.toLowerCase());
    }

    public void setConverterTurno(ConverterAutoComplete converterTurno) {
        this.converterTurno = converterTurno;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public TurmaDAOImpl getTurmaDAO() {
        return turmaDAO;
    }

    public Turma getTurmaSelecionada() {
        return turmaSelecionada;
    }

    public void setTurmaSelecionada(Turma turmaSelecionada) {
        this.turmaSelecionada = turmaSelecionada;
    }

}
