package br.com.gori.scb.reportcontrole;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 *
 * @author Leonardo
 */
public class Relatorio {

    public Connection recuperaConexaoJDBC() {
        Connection conexao = null;
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(LeitorPersistenceXML.getInstance().getNomeDataSource());
            conexao = ds.getConnection();
            return conexao;
        } catch (SQLException ex) {
            Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
            return conexao;
        } catch (NamingException ex) {
            Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
            return conexao;
        }
    }

    public String getCaminho() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String caminho = ((ServletContext) facesContext.getExternalContext().getContext()).getRealPath("/WEB-INF/report/");
        caminho += "/";
        return caminho;
    }

    public void gerarRelatorio(String arquivoJasper, HashMap parametros) throws JRException, IOException {
        final Connection conn = recuperaConexaoJDBC();
        try {
            parametros.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
            gerarRelatorio(arquivoJasper, parametros, conn);
        } catch (Exception e) {
            Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void gerarRelatorio(String arquivoJasper, HashMap parametros, Connection con)
            throws JRException, IOException {
        parametros.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.responseComplete();
        ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
        JasperPrint jasperPrint = JasperFillManager.fillReport(scontext.getRealPath("/WEB-INF/report/" + arquivoJasper), parametros, con);
        ByteArrayOutputStream dadosByte = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, dadosByte);
        exporter.exportReport();
        byte[] bytes = dadosByte.toByteArray();
        if (bytes != null && bytes.length > 0) {
            int recorte = arquivoJasper.indexOf(".");
            String nomePDF = arquivoJasper.substring(0, recorte);
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "inline; filename=\"" + nomePDF + ".pdf\"");
            response.setContentLength(bytes.length);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes, 0, bytes.length);
            outputStream.flush();
            outputStream.close();
        }
    }

    protected void addErrorMessage(String errorMessage) {
        addErrorMessage(null, errorMessage);
    }

    protected void addErrorMessage(String componentId, String errorMessage) {
        addMessage(componentId, errorMessage, FacesMessage.SEVERITY_ERROR);
    }

    private void addMessage(String componentId, String errorMessage, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(errorMessage);
        message.setSeverity(severity);
        FacesContext.getCurrentInstance().addMessage(componentId, message);
    }
}
