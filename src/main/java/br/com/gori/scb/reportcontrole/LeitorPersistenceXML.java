package br.com.gori.scb.reportcontrole;

import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Leonardo
 */
public class LeitorPersistenceXML {

    private static LeitorPersistenceXML instancia = new LeitorPersistenceXML();
    private String nomeDataSource = "java:/comp/env/jdbc/postgres";

    private LeitorPersistenceXML() {
        arrumaNome();
    }

    public static LeitorPersistenceXML getInstance() {
        return instancia;
    }

    public String getNomeDataSource() {
        return nomeDataSource;
    }

    public void setNomeDataSource(String nomeDataSource) {
        this.nomeDataSource = nomeDataSource;
    }

    private void arrumaNome() {
        try {
            InputStream resourceAsStream = LeitorPersistenceXML.class.getResourceAsStream("/META-INF/persistence.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(resourceAsStream);
            NodeList jtas = doc.getElementsByTagName("jta-data-source");
            if (jtas.getLength() > 0) {
                Node item = jtas.item(0);
                nomeDataSource = item.getTextContent();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
