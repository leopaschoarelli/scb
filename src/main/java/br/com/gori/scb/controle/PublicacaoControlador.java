package br.com.gori.scb.controle;

import br.com.gori.scb.controle.util.JsfUtil;
import br.com.gori.scb.entidade.Exemplar;
import br.com.gori.scb.entidade.Publicacao;
import br.com.gori.scb.entidade.util.EstadoExemplar;
import br.com.gori.scb.entidade.util.TipoAquisicao;
import br.com.gori.scb.dao.impl.AutorDAOImpl;
import br.com.gori.scb.dao.impl.AutoriaDAOImpl;
import br.com.gori.scb.dao.impl.CategoriaDAOImpl;
import br.com.gori.scb.dao.impl.CidadeDAOImpl;
import br.com.gori.scb.dao.impl.EditoraDAOImpl;
import br.com.gori.scb.dao.impl.ExemplarDAOImpl;
import br.com.gori.scb.dao.impl.PublicacaoDAOImpl;
import br.com.gori.scb.dao.impl.TipoAutorDAOImpl;
import br.com.gori.scb.entidade.Autor;
import br.com.gori.scb.entidade.Autoria;
import br.com.gori.scb.entidade.Categoria;
import br.com.gori.scb.entidade.Cidade;
import br.com.gori.scb.entidade.Editora;
import br.com.gori.scb.entidade.TipoAutor;
import br.com.gori.scb.entidade.util.TipoPublicacao;
import br.com.gori.scb.util.ConverterAutoComplete;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Leonardo
 */
@ManagedBean
@SessionScoped
public class PublicacaoControlador implements Serializable {

    private Publicacao publicacao;
    private Publicacao publicacaoSelecionada;
    private PublicacaoDAOImpl publicacaoDAO;
    private Exemplar exemplar;
    private ExemplarDAOImpl exemplarDAO;
    private List<Exemplar> exemplares;
    private List<Exemplar> exemplarEtiqueta;
    private EditoraDAOImpl editoraDAO;
    private CategoriaDAOImpl categoriaDAO;
    private AutoriaDAOImpl autoriaDAO;
    private AutorDAOImpl autorDAO;
    private Autoria autoria;
    private Autor autor;
    private TipoAutorDAOImpl tipoAutorDAO;
    private TipoAutor tipoAutor;
    private CidadeDAOImpl cidadeDAO;
    private boolean visualizar;
    private boolean edicao;
    private boolean novo;
    private String header;
    private ConverterAutoComplete converterEditora;
    private ConverterAutoComplete converterCidade;
    private ConverterAutoComplete converterCategoria;
    private ConverterAutoComplete converterAutor;
    private ConverterAutoComplete converterTipoAutor;
    private Integer qtdGerarExem;

    private Exemplar exemplarAux;
    private Exemplar exemplarAuxiliar;

    public PublicacaoControlador() {
        newInstances();
    }

    private void newInstances() {
        this.publicacao = new Publicacao(true);
        this.publicacaoDAO = new PublicacaoDAOImpl();
        this.exemplar = new Exemplar(true);
        this.exemplarAux = new Exemplar();
        this.exemplarDAO = new ExemplarDAOImpl();
        this.categoriaDAO = new CategoriaDAOImpl();
        this.editoraDAO = new EditoraDAOImpl();
        this.autorDAO = new AutorDAOImpl();
        this.tipoAutorDAO = new TipoAutorDAOImpl();
        this.cidadeDAO = new CidadeDAOImpl();
        this.autoriaDAO = new AutoriaDAOImpl();
        this.autoria = new Autoria();
        this.autor = new Autor();
        this.tipoAutor = new TipoAutor();
        this.exemplarAuxiliar = new Exemplar();
        this.exemplarEtiqueta = new ArrayList<Exemplar>();
    }

    public boolean validaExemplar() {
        try {
            if (publicacao.getExemplar().isEmpty()) {
                JsfUtil.addErrorMessage("É necessario informar um exemplar!");
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao validar exemplar");
            return false;
        }
    }

    public boolean validaAutoria() {
        try {
            if (publicacao.getAutorias().isEmpty()) {
                JsfUtil.addErrorMessage("É necessario informar uma autoria!");
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao validar autoria");
            return false;
        }
    }

    public List<Publicacao> getPublicacoes() {
        return publicacaoDAO.listAll();
    }

    public void salvarAutoria() {
        try {
            if (edicao) {
                autoriaDAO.update(autoria);
            } else {
                autoriaDAO.save(autoria);
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao salvar autoria");
        }
    }

    public String salvar() {
        try {
            for (Exemplar ex : publicacao.getExemplar()) {
                String tombo;
                String tombo_aux;
                String concat;
                boolean contem;
                tombo_aux = ex.getTombo();
                concat = "-" + ex.getNumExe() + "-" + ex.getPublicacao().getId();
                tombo = tombo_aux + concat;
                System.out.println(ex.getTombo());

                if (!ex.getTombo().contains(concat)) {
                    ex.setTombo(tombo);
                }
            }
            if (edicao) {
                publicacaoDAO.update(publicacao);
            } else {
                publicacaoDAO.update(publicacao);
            }
            JsfUtil.addSuccessMessage("Publicacao salvo com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao salvar publicacao");
            return null;
        }
    }

    public String excluir() {
        try {
            publicacaoDAO.delete(publicacaoSelecionada);
            publicacaoSelecionada = null;
            JsfUtil.addSuccessMessage("Publicação removida com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao excluir publicação");
            return null;
        }
    }

    public String prepararLista() {
        newInstances();
        if (publicacaoSelecionada != null) {
            publicacaoSelecionada = null;
        }
        return "lista";
    }

    public String prepararEdicao() {
        newInstances();
        publicacao = publicacaoSelecionada;
        visualizar = false;
        edicao = true;
        novo = false;
        header = "Editar Publicação";
        return "edita";
    }

    public String prepararNovo() {
        newInstances();
        visualizar = false;
        edicao = false;
        novo = true;
        header = "Cadastrar Publicação";
        return "edita";
    }

    public String prepararVer() {
        newInstances();
        publicacao = publicacaoSelecionada;
        visualizar = true;
        edicao = false;
        header = "Visualizar Publicação";
        return "edita";
    }

    public List<SelectItem> getListaTipoPublicacao() {
        List<SelectItem> toReturn = new ArrayList<SelectItem>();
        for (TipoPublicacao tp : TipoPublicacao.values()) {
            toReturn.add(new SelectItem(tp, tp.getDescricao()));
        }
        return toReturn;
    }

    public void editaExemplar(Exemplar exemplar) {
        this.exemplarAux = new Exemplar();
        System.out.println("Exemplar Numero: " + exemplar.getNumExe());
        this.exemplarAux = exemplar;
    }

    public void adicionaExemplar() {
        exemplar.setPublicacao(publicacao);
        publicacao.getExemplar().add(exemplar);
        exemplar = new Exemplar();
    }

    public void onTomboEdit(CellEditEvent event) {
        Object novoValor = event.getNewValue();
        exemplar.setTombo((String) novoValor);

    }

    public void onObservacaoEdit(CellEditEvent event) {
        Object novoValor = event.getNewValue();
        exemplar.setObservacao((String) novoValor);
    }

    public void onDataBaixaEdit(CellEditEvent event) {
        Object novoValor = event.getNewValue();
        exemplar.setDataBaixa((Date) novoValor);
    }

    public void gerarExemplar() {
        Integer numexe;

        if (!novo) {
            exemplarAuxiliar = publicacaoDAO.getMaxNumExe(publicacao.getId());
            numexe = exemplarAuxiliar.getNumExe();
        } else {
            numexe = 0;
        }
        if (this.qtdGerarExem == null) {
            this.qtdGerarExem = 1;
        }
        for (int i = 0; i < this.qtdGerarExem; i++) {
            numexe = numexe + 1;
            exemplar.setNumExe(numexe);
            exemplar.setEstadoExemplar(exemplarAux.getEstadoExemplar());
            exemplar.setDataAquisicao(exemplarAux.getDataAquisicao());
            exemplar.setTipoAquisicao(exemplarAux.getTipoAquisicao());
            exemplar.setPreco(exemplarAux.getPreco());
            exemplar.setUsoInterno(exemplarAux.isUsoInterno());
            exemplar.setDataBaixa(exemplarAux.getDataBaixa());
            exemplar.setObservacao(exemplarAux.getObservacao());

            exemplar.setPublicacao(publicacao);
            publicacao.getExemplar().add(exemplar);
            exemplar = new Exemplar(true);
        }
        this.qtdGerarExem = null;

    }

    public List<SelectItem> getTipoAquisicao() {
        List<SelectItem> toReturn = new ArrayList<SelectItem>();
        for (TipoAquisicao tp : TipoAquisicao.values()) {
            toReturn.add(new SelectItem(tp, tp.getDescricao()));
        }
        return toReturn;
    }

    public List<SelectItem> getEstadoExemplar() {
        List<SelectItem> toReturn = new ArrayList<SelectItem>();
        for (EstadoExemplar tp : EstadoExemplar.values()) {
            toReturn.add(new SelectItem(tp, tp.getDescricao()));
        }
        return toReturn;
    }

    public void excluirExemplar(Exemplar exemplar) {
        publicacao.getExemplar().remove(exemplar);
    }

    public void excluirAutoria(Autoria autoria) {
        publicacao.getAutorias().remove(autoria);
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

    public boolean isVisualizar() {
        return visualizar;
    }

    public void setVisualizar(boolean visualizar) {
        this.visualizar = visualizar;
    }

    public Exemplar getExemplar() {
        return exemplar;
    }

    public void setExemplar(Exemplar exemplar) {
        this.exemplar = exemplar;
    }

    public EditoraDAOImpl getEditoraDAO() {
        return editoraDAO;
    }

    public void setEditoraDAO(EditoraDAOImpl editoraDAO) {
        this.editoraDAO = editoraDAO;
    }

    public CategoriaDAOImpl getCategoriaDAO() {
        return categoriaDAO;
    }

    public void setCategoriaDAO(CategoriaDAOImpl categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }

    public AutorDAOImpl getAutorDAO() {
        return autorDAO;
    }

    public void setAutorDAO(AutorDAOImpl autorDAO) {
        this.autorDAO = autorDAO;
    }

    public List<Exemplar> getExemplares() {
        return exemplares;
    }

    public void setExemplares(List<Exemplar> exemplares) {
        this.exemplares = exemplares;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public ConverterAutoComplete getConverterEditora() {
        if (converterEditora == null) {
            converterEditora = new ConverterAutoComplete(Editora.class, editoraDAO);
        }
        return converterEditora;
    }

    public void setConverterEditora(ConverterAutoComplete converterEditora) {
        this.converterEditora = converterEditora;
    }

    public ConverterAutoComplete getConverterCidade() {
        if (converterCidade == null) {
            converterCidade = new ConverterAutoComplete(Cidade.class, cidadeDAO);
        }
        return converterCidade;
    }

    public void setConverterCidade(ConverterAutoComplete converterCidade) {
        this.converterCidade = converterCidade;
    }

    public ConverterAutoComplete getConverterCategoria() {
        if (converterCategoria == null) {
            converterCategoria = new ConverterAutoComplete(Categoria.class, categoriaDAO);
        }
        return converterCategoria;
    }

    public void setConverterCategoria(ConverterAutoComplete converterCategoria) {
        this.converterCategoria = converterCategoria;
    }

    public ConverterAutoComplete getConverterAutor() {
        if (converterAutor == null) {
            converterAutor = new ConverterAutoComplete(Autor.class, autorDAO);
        }
        return converterAutor;
    }

    public void setConverterAutor(ConverterAutoComplete converterAutor) {
        this.converterAutor = converterAutor;
    }

    public ConverterAutoComplete getConverterTipoAutor() {
        if (converterTipoAutor == null) {
            converterTipoAutor = new ConverterAutoComplete(TipoAutor.class, tipoAutorDAO);
        }
        return converterTipoAutor;
    }

    public void setConverterTipoAutor(ConverterAutoComplete converterTipoAutor) {
        this.converterTipoAutor = converterTipoAutor;
    }

    public List<Editora> completaEditora(String value) {
        return publicacaoDAO.getEditoras(value.toLowerCase());
    }

    public List<Cidade> completaCidade(String value) {
        return publicacaoDAO.getCidades(value.toLowerCase());
    }

    public List<Categoria> completaCategoria(String value) {
        return publicacaoDAO.getCategorias(value.toLowerCase());
    }

    public List<Autor> completaAutor(String value) {
        return publicacaoDAO.getAutores(value.toLowerCase());
    }

    public List<TipoAutor> completaTipoAutor(String value) {
        return publicacaoDAO.getTipoAutores(value.toLowerCase());
    }

    public TipoAutorDAOImpl getTipoAutorDAO() {
        return tipoAutorDAO;
    }

    public void setTipoAutorDAO(TipoAutorDAOImpl tipoAutorDAO) {
        this.tipoAutorDAO = tipoAutorDAO;
    }

    public CidadeDAOImpl getCidadeDAO() {
        return cidadeDAO;
    }

    public void setCidadeDAO(CidadeDAOImpl cidadeDAO) {
        this.cidadeDAO = cidadeDAO;
    }

    public AutoriaDAOImpl getAutoriaDAO() {
        return autoriaDAO;
    }

    public void setAutoriaDAO(AutoriaDAOImpl autoriaDAO) {
        this.autoriaDAO = autoriaDAO;
    }

    public void adicionaAutoria() {
        publicacao.getAutorias().add(autoria);
        autoria = new Autoria();
    }

    public Autoria getAutoria() {
        return autoria;
    }

    public void setAutoria(Autoria autoria) {
        this.autoria = autoria;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public TipoAutor getTipoAutor() {
        return tipoAutor;
    }

    public void setTipoAutor(TipoAutor tipoAutor) {
        this.tipoAutor = tipoAutor;
    }

    public boolean isEdicao() {
        return edicao;
    }

    public void setEdicao(boolean edicao) {
        this.edicao = edicao;
    }

    public Integer getQtdGerarExem() {
        return qtdGerarExem;
    }

    public void setQtdGerarExem(Integer qtdGerarExem) {
        this.qtdGerarExem = qtdGerarExem;
    }

    public ExemplarDAOImpl getExemplarDAO() {
        return exemplarDAO;
    }

    public void setExemplarDAO(ExemplarDAOImpl exemplarDAO) {
        this.exemplarDAO = exemplarDAO;
    }

    public Publicacao getPublicacaoSelecionada() {
        return publicacaoSelecionada;
    }

    public void setPublicacaoSelecionada(Publicacao publicacaoSelecionada) {
        this.publicacaoSelecionada = publicacaoSelecionada;
    }

    public Exemplar getExemplarAux() {
        return exemplarAux;
    }

    public void setExemplarAux(Exemplar exemplarAux) {
        this.exemplarAux = exemplarAux;
    }

    public Exemplar getExemplarAuxiliar() {
        return exemplarAuxiliar;
    }

    public void setExemplarAuxiliar(Exemplar exemplarAuxiliar) {
        this.exemplarAuxiliar = exemplarAuxiliar;
    }

    public boolean isNovo() {
        return novo;
    }

    public void setNovo(boolean novo) {
        this.novo = novo;
    }

    public List<Exemplar> getExemplarEtiqueta() {
        return exemplarEtiqueta;
    }

    public void setExemplarEtiqueta(List<Exemplar> exemplarEtiqueta) {
        this.exemplarEtiqueta = exemplarEtiqueta;
    }

}
