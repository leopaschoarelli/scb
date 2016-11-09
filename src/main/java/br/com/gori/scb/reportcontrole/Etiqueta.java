package br.com.gori.scb.reportcontrole;

import br.com.gori.scb.connection.EntityManagerProducer;
import br.com.gori.scb.controle.util.JsfUtil;
import br.com.gori.scb.dao.impl.ExemplarDAOImpl;
import br.com.gori.scb.entidade.Exemplar;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Leonardo
 */
@ManagedBean
@SessionScoped
public class Etiqueta implements Serializable {

    @Autowired
    private FacesContext facesContext;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private EntityManager manager;

    private List<Exemplar> exemplares;
    private ExemplarDAOImpl exemplarDAO;

    public Etiqueta() {
        this.exemplares = new ArrayList<Exemplar>();
        this.exemplarDAO = new ExemplarDAOImpl();
    }

    public void emitir() {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("SQL", gerarSQL());
        System.out.println(parametros.toString());
        ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/etiquetas.jasper",
                this.response, parametros, "Etiqueta.pdf");

        Session session = EntityManagerProducer.getEntityManager().unwrap(Session.class);
        session.doWork(executor);

        if (executor.isRelatorioGerado()) {
            facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();
        } else {
            JsfUtil.addErrorMessage("A execução do relatório não retornou dados.");
        }
    }

    private String gerarSQL() {
        StringBuilder sb = new StringBuilder();
        sb.append(" and b.id in (");
        String sb2 = null;
        if (!exemplares.isEmpty()) {
            for (Exemplar ex : exemplares) {
                if (sb2 == null) {
                    sb2 = "" + ex.getId();
                } else {
                    sb2 = sb2 + "," + ex.getId();
                }
            }
        }
//        String sb2 = "2359,2338";
        sb.append(sb2).append(")");
        return sb.toString();
    }

    public List<Exemplar> getExemplares() {
        return exemplares;
    }

    public void setExemplares(List<Exemplar> exemplares) {
        this.exemplares = exemplares;
    }

//    public EntityManager getEntityManager() {
//        return EntityManagerProducer.newInstance().getEntityManager();
//    }
    public ExemplarDAOImpl getExemplarDAO() {
        return exemplarDAO;
    }

    public void setExemplarDAO(ExemplarDAOImpl exemplarDAO) {
        this.exemplarDAO = exemplarDAO;
    }

}
