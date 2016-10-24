package br.com.gori.scb.reportcontrole;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.hibernate.jdbc.Work;

/**
 *
 * @author Leonardo
 */
public class ExecutorRelatorio implements Work {

    private String caminhoRelatorio;
    private HttpServletResponse response;
    private Map<String, Object> parametros;
    private String nomeArquivoSaida;
    private FacesContext facesContext = FacesContext.getCurrentInstance();

    private boolean relatorioGerado;

    public ExecutorRelatorio(String caminhoRelatorio,
            HttpServletResponse response, Map<String, Object> parametros,
            String nomeArquivoSaida) {
        this.caminhoRelatorio = caminhoRelatorio;
        this.response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        this.parametros = parametros;
        this.nomeArquivoSaida = nomeArquivoSaida;

        this.parametros.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
    }

    @Override
    public void execute(Connection connection) throws SQLException {
        try {
            InputStream relatorioStream = this.getClass().getResourceAsStream(this.caminhoRelatorio);

            JasperPrint print = JasperFillManager.fillReport(relatorioStream, this.parametros, connection);
            this.relatorioGerado = print.getPages().size() > 0;
            
            if (this.relatorioGerado) {
                JRExporter exportador = new JRPdfExporter();
                exportador.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
                exportador.setParameter(JRExporterParameter.JASPER_PRINT, print);

                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline; filename=\""
                        + this.nomeArquivoSaida + "\"");

                exportador.exportReport();
                response.getOutputStream().flush();
                response.getOutputStream().close();
                response.getOutputStream().close();
            }
        } catch (Exception e) {
            throw new SQLException("Erro ao executar relatório " + this.caminhoRelatorio, e);
        }
    }

    public boolean isRelatorioGerado() {
        return relatorioGerado;
    }

}
