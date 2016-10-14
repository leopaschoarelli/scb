package br.com.gori.scb.reportcontrole;

import br.com.gori.scb.controle.util.JsfUtil;
import br.com.gori.scb.dao.impl.EmprestimoDAOImpl;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

@ManagedBean
@RequestScoped
public class RelatorioGeralControlador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private FacesContext facesContext;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private EntityManager manager;

    private EmprestimoDAOImpl emprestimoDAO;

    public RelatorioGeralControlador() {
        this.emprestimoDAO = new EmprestimoDAOImpl();
    }

    public void emitir() {
        Map<String, Object> parametros = new HashMap<>();

        ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/emprestimos.jasper",
                this.response, parametros, "emprestimos.pdf");

        Session session = emprestimoDAO.getEntityManager().unwrap(Session.class);
        session.doWork(executor);

        if (executor.isRelatorioGerado()) {
            facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();
        } else {
            JsfUtil.addErrorMessage("A execução do relatório não retornou dados.");
        }
    }

    public EmprestimoDAOImpl getEmprestimoDAO() {
        return emprestimoDAO;
    }

    public void setEmprestimoDAO(EmprestimoDAOImpl emprestimoDAO) {
        this.emprestimoDAO = emprestimoDAO;
    }
    
    

}
