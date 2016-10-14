package br.com.gori.scb.controle;

import br.com.gori.scb.controle.util.JsfUtil;
import br.com.gori.scb.dao.impl.CategoriaDAOImpl;
import br.com.gori.scb.entidade.Categoria;
import br.com.gori.scb.util.ConverterAutoComplete;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Leonardo
 */
@ManagedBean
@SessionScoped
public class CategoriaControlador implements Serializable {

    private CategoriaDAOImpl categoriaDAO;
    private Categoria categoria;
    private TreeNode categoriaSelecionada;
    private boolean edicao;
    private boolean visualizar;
    private String header;
    private ConverterAutoComplete converterSuperior;
    private TreeNode raiz;

    public CategoriaControlador() {
        newInstances();
    }

    private void newInstances() {
        this.categoriaDAO = new CategoriaDAOImpl();
        this.categoria = new Categoria();
        this.raiz = new DefaultTreeNode();
        consultar();
    }

    public void consultar() {
        List<Categoria> categoriasRaizes = categoriaDAO.raizes();
        this.raiz = new DefaultTreeNode("Raiz", null);
        adicionarNos(categoriasRaizes, this.raiz);
    }

    private void adicionarNos(List<Categoria> categorias, TreeNode pai) {
        for (Categoria categoria : categorias) {
            TreeNode no = new DefaultTreeNode(categoria, pai);
            adicionarNos(categoria.getFilhos(), no);
            no.setExpanded(false);
        }
    }

    public String salvar() {
        try {
            if (edicao) {
                categoriaDAO.update(categoria);
            } else {
                categoriaDAO.merge(categoria);
            }
            JsfUtil.addSuccessMessage("Categoria salva com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao salvar categoria");
            return null;
        }
    }

    public String excluir() {
        try {
            Categoria categ = (Categoria) (categoriaSelecionada.getData());
            Categoria c = categoriaDAO.buscarCategoriaPorDescricao(categoriaSelecionada.getData().toString());
            if (c != null) {
                categoriaDAO.delete(c);
            }
            JsfUtil.addSuccessMessage("Categoria removida com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao excluir categoria");
            return null;
        }
    }

    public String prepararLista() {
        newInstances();
        return "lista";
    }

    public String prepararEdicao() {
        newInstances();
        this.categoria = (Categoria) (categoriaSelecionada.getData());
        header = "Editar Categoria";
        edicao = true;
        visualizar = false;
        return "edita";
    }

    public String prepararNovo() {
        newInstances();
        edicao = false;
        header = "Cadastrar Categoria";
        visualizar = false;
        return "edita";
    }

    public String prepararVer() {
        newInstances();
        this.categoria = (Categoria) (categoriaSelecionada.getData());
        visualizar = true;
        edicao = false;
        header = "Visualizar Categoria";
        return "edita";
    }

    public List<Categoria> getCategorias() {
        return categoriaDAO.listAll();
    }

    public CategoriaDAOImpl getCategoriaDAO() {
        return categoriaDAO;
    }

    public void setCategoriaDAO(CategoriaDAOImpl categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setEdicao(boolean edicao) {
        this.edicao = edicao;
    }

    public void setVisualizar(boolean visualizar) {
        this.visualizar = visualizar;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public ConverterAutoComplete getConverterSuperior() {
        if (converterSuperior == null) {
            converterSuperior = new ConverterAutoComplete(Categoria.class, categoriaDAO);
        }
        return converterSuperior;
    }

    public void setConverterSuperior(ConverterAutoComplete converterSuperior) {
        this.converterSuperior = converterSuperior;
    }

    public List<Categoria> completaCategoria(String value) {
        return categoriaDAO.getCategorias(value.toLowerCase());
    }

    public TreeNode getRaiz() {
        return raiz;
    }

    public void setRaiz(TreeNode raiz) {
        this.raiz = raiz;
    }

    public boolean isEdicao() {
        return edicao;
    }

    public boolean isVisualizar() {
        return visualizar;
    }

    public TreeNode getCategoriaSelecionada() {
        return categoriaSelecionada;
    }

    public void setCategoriaSelecionada(TreeNode categoriaSelecionada) {
        this.categoriaSelecionada = categoriaSelecionada;
    }

}
