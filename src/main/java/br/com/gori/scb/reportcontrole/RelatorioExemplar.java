package br.com.gori.scb.reportcontrole;

import br.com.gori.scb.connection.EntityManagerProducer;
import br.com.gori.scb.controle.util.JsfUtil;
import br.com.gori.scb.dao.impl.AutorDAOImpl;
import br.com.gori.scb.dao.impl.CategoriaDAOImpl;
import br.com.gori.scb.dao.impl.EditoraDAOImpl;
import br.com.gori.scb.dao.impl.ExemplarDAOImpl;
import br.com.gori.scb.dao.impl.PublicacaoDAOImpl;
import br.com.gori.scb.entidade.Autor;
import br.com.gori.scb.entidade.Categoria;
import br.com.gori.scb.entidade.Editora;
import br.com.gori.scb.entidade.Exemplar;
import br.com.gori.scb.entidade.Publicacao;
import br.com.gori.scb.util.ConverterAutoComplete;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Leonardo
 */
@ManagedBean
@SessionScoped
public class RelatorioExemplar implements Serializable {

    @Autowired
    private FacesContext facesContext;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private EntityManager manager;

    private Publicacao publicacao;
    private PublicacaoDAOImpl publicacaoDAO;
    private Exemplar exemplar;
    private ExemplarDAOImpl exemplarDAO;
    private Editora editora;
    private EditoraDAOImpl editoraDAO;
    private Categoria categoria;
    private CategoriaDAOImpl categoriaDAO;
    private Autor autor;
    private AutorDAOImpl autorDAO;
    private ConverterAutoComplete converterPublicacao;
    private ConverterAutoComplete converterExemplar;
    private ConverterAutoComplete converterEditora;
    private ConverterAutoComplete converterCategoria;
    private ConverterAutoComplete converterAutor;

    public RelatorioExemplar() {
        newInstances();
    }

    public void newInstances() {
        publicacao = new Publicacao();
        publicacaoDAO = new PublicacaoDAOImpl();
        exemplar = new Exemplar();
        exemplarDAO = new ExemplarDAOImpl();
        editora = new Editora();
        editoraDAO = new EditoraDAOImpl();
        categoria = new Categoria();
        categoriaDAO = new CategoriaDAOImpl();
        autorDAO = new AutorDAOImpl();
    }

    public void emitir() {

        Map<String, Object> parametros = new HashMap<>();

        parametros.put("IMAGEM", getCaminho());
        parametros.put("SQL", gerarSql());
        ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/exemplares.jasper",
                this.response, parametros, "Relatório Exemplares.pdf");

        Session session = EntityManagerProducer.getEntityManager().unwrap(Session.class
        );
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
        if (publicacao != null) {
            sb.append(" and a.id = ").append(publicacao.getId());
        }
        if (exemplar != null) {
            sb.append(" and b.id = ").append(exemplar.getId());
        }
        if (categoria != null) {
            sb.append(" and h.id = ").append(categoria.getId());
        }
        if (editora != null) {
            sb.append(" and c.id = ").append(editora.getId());
        }
        if (autor != null) {
            sb.append(" and g.id = ").append(autor.getId());
        }
        System.out.println("SQL: "+sb);
        return sb.toString();
    }

    private String getCaminho() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String caminho = ((ServletContext) facesContext.getExternalContext().getContext()).getRealPath("/WEB-INF/images/");
        caminho += "/";
        return caminho;
    }

    public Publicacao getPublicacao() {
        return publicacao;
    }

    public void setPublicacao(Publicacao publicacao) {
        this.publicacao = publicacao;
    }

    public PublicacaoDAOImpl getPublicacaoDAO() {
        return publicacaoDAO;
    }

    public void setPublicacaoDAO(PublicacaoDAOImpl publicacaoDAO) {
        this.publicacaoDAO = publicacaoDAO;
    }

    public Exemplar getExemplar() {
        return exemplar;
    }

    public void setExemplar(Exemplar exemplar) {
        this.exemplar = exemplar;
    }

    public ExemplarDAOImpl getExemplarDAO() {
        return exemplarDAO;
    }

    public void setExemplarDAO(ExemplarDAOImpl exemplarDAO) {
        this.exemplarDAO = exemplarDAO;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public EditoraDAOImpl getEditoraDAO() {
        return editoraDAO;
    }

    public void setEditoraDAO(EditoraDAOImpl editoraDAO) {
        this.editoraDAO = editoraDAO;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public CategoriaDAOImpl getCategoriaDAO() {
        return categoriaDAO;
    }

    public void setCategoriaDAO(CategoriaDAOImpl categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public AutorDAOImpl getAutorDAO() {
        return autorDAO;
    }

    public void setAutorDAO(AutorDAOImpl autorDAO) {
        this.autorDAO = autorDAO;
    }

    public ConverterAutoComplete
            getConverterPublicacao() {
        if (converterPublicacao == null) {
            converterPublicacao = new ConverterAutoComplete(Publicacao.class, publicacaoDAO);
        }
        return converterPublicacao;
    }

    public void setConverterPublicacao(ConverterAutoComplete converterPublicacao) {
        this.converterPublicacao = converterPublicacao;
    }

    public ConverterAutoComplete
            getConverterExemplar() {
        if (converterExemplar == null) {
            converterExemplar = new ConverterAutoComplete(Exemplar.class, exemplarDAO);
        }
        return converterExemplar;
    }

    public void setConverterExemplar(ConverterAutoComplete converterExemplar) {
        this.converterExemplar = converterExemplar;
    }

    public ConverterAutoComplete
            getConverterEditora() {
        if (converterEditora == null) {
            converterEditora = new ConverterAutoComplete(Editora.class, editoraDAO);
        }
        return converterEditora;
    }

    public void setConverterEditora(ConverterAutoComplete converterEditora) {
        this.converterEditora = converterEditora;
    }

    public ConverterAutoComplete
            getConverterCategoria() {
        if (converterCategoria == null) {
            converterCategoria = new ConverterAutoComplete(Categoria.class, categoriaDAO);
        }
        return converterCategoria;
    }

    public void setConverterCategoria(ConverterAutoComplete converterCategoria) {
        this.converterCategoria = converterCategoria;
    }

    public ConverterAutoComplete
            getConverterAutor() {
        if (converterAutor == null) {
            converterAutor = new ConverterAutoComplete(Autor.class, autorDAO);
        }
        return converterAutor;
    }

    public void setConverterAutor(ConverterAutoComplete converterAutor) {
        this.converterAutor = converterAutor;
    }

    public List<Publicacao> completaPublicacao(String value) {
        return publicacaoDAO.getPublicacoes(value.toLowerCase());
    }

    public List<Exemplar> completaExemplar(String value) {
        return exemplarDAO.getExemplares(value.toLowerCase());
    }

    public List<Categoria> completaCategoria(String value) {
        return categoriaDAO.getCategorias(value.toLowerCase());
    }

    public List<Editora> completaEditora(String value) {
        return editoraDAO.getEditoras(value.toLowerCase());
    }

    public List<Autor> completaAutor(String value) {
        return autorDAO.getAutores(value.toLowerCase());
    }

}
