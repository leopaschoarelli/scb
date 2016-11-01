package br.com.gori.scb.reportcontrole;

import br.com.gori.scb.controle.util.JsfUtil;
import br.com.gori.scb.dao.impl.EmprestimoDAOImpl;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Leonardo
 */
@ManagedBean
@SessionScoped
public class ComprovanteControlador implements Serializable {

    @Autowired
    private FacesContext facesContext;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private EntityManager manager;

    private EmprestimoDAOImpl emprestimoDAO;

    public ComprovanteControlador() {
        this.emprestimoDAO = new EmprestimoDAOImpl();
    }

    public void emitir() {

        Map<String, Object> parametros = new HashMap<>();

        parametros.put("IMAGEM", getCaminho());
//        parametros.put("SQL", gerarSql());
//        System.out.println(parametros.toString());
        ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/report2.jasper",
                this.response, parametros, "Comprovante.pdf");

        Session session = emprestimoDAO.getEntityManager().unwrap(Session.class);
        session.doWork(executor);

        if (executor.isRelatorioGerado()) {
            facesContext = FacesContext.getCurrentInstance();
            facesContext.responseComplete();
        } else {
            JsfUtil.addErrorMessage("A execução do relatório não retornou dados.");
        }
    }

    private String gerarSql() {
        StringBuilder sb = new StringBuilder();
        sb.append(" and f.emprestimo_id = (select MAX(g.emprestimo_id) from comprovanteemprestimo g)");
        System.out.println(sb.toString());
        return sb.toString();
    }

    public EmprestimoDAOImpl getEmprestimoDAO() {
        return emprestimoDAO;
    }

    public void setEmprestimoDAO(EmprestimoDAOImpl emprestimoDAO) {
        this.emprestimoDAO = emprestimoDAO;
    }

    private String getCaminho() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String caminho = ((ServletContext) facesContext.getExternalContext().getContext()).getRealPath("/WEB-INF/images/");
        caminho += "/";
        return caminho;
    }

}
