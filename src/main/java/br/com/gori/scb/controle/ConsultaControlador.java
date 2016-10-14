package br.com.gori.scb.controle;

import br.com.gori.scb.dao.impl.AutorDAOImpl;
import br.com.gori.scb.dao.impl.CategoriaDAOImpl;
import br.com.gori.scb.dao.impl.ConsultaDAOImpl;
import br.com.gori.scb.dao.impl.PublicacaoDAOImpl;
import br.com.gori.scb.entidade.Autor;
import br.com.gori.scb.entidade.Categoria;
import br.com.gori.scb.entidade.Publicacao;
import br.com.gori.scb.entidade.util.FiltroConsulta;
import br.com.gori.scb.util.ConverterAutoComplete;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Leonardo
 */
@ManagedBean
@SessionScoped
public class ConsultaControlador implements Serializable {

    private Publicacao publicacao;
    private PublicacaoDAOImpl publicacaoDAO;
    private List<Publicacao> publicacaoFiltrado;
    private ConsultaDAOImpl consultaDAO;
    private String titulo;
    private String autor;
    private String categoria;
    private Publicacao publicacaoSelecionada;

    public ConsultaControlador() {
        newInstances();
    }

    private void newInstances() {
        this.publicacao = new Publicacao();
        this.publicacaoDAO = new PublicacaoDAOImpl();
        this.publicacaoFiltrado = new ArrayList<Publicacao>();
        this.consultaDAO = new ConsultaDAOImpl();
        this.titulo = "";
        this.autor = "";
        this.categoria = "";
        this.publicacaoSelecionada = new Publicacao();
    }

    public List<Publicacao> getPublicacoes() {
        return publicacaoFiltrado;
    }

    public List<SelectItem> getListaFiltro() {
        List<SelectItem> toReturn = new ArrayList<SelectItem>();
        for (FiltroConsulta fc : FiltroConsulta.values()) {
            toReturn.add(new SelectItem(fc, fc.getDescricao()));
        }
        return toReturn;
    }

    public List<Publicacao> getPublicacaoFiltrado() {
        return publicacaoFiltrado;
    }

    public void setPublicacaoFiltrado(List<Publicacao> publicacaoFiltrado) {
        this.publicacaoFiltrado = publicacaoFiltrado;
    }

    public ConsultaDAOImpl getConsultaDAO() {
        return consultaDAO;
    }

    public void setConsultaDAO(ConsultaDAOImpl consultaDAO) {
        this.consultaDAO = consultaDAO;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Publicacao getPublicacaoSelecionada() {
        return publicacaoSelecionada;
    }

    public void setPublicacaoSelecionada(Publicacao publicacaoSelecionada) {
        this.publicacaoSelecionada = publicacaoSelecionada;
    }
    
    public void pesquisar() {
        this.publicacaoFiltrado = publicacaoDAO.listarFiltros(titulo, autor, categoria);
    }

}
