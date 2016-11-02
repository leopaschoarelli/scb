package br.com.gori.scb.controle;

import br.com.gori.scb.controle.util.JsfUtil;
import br.com.gori.scb.dao.impl.ComprovanteDAOImpl;
import br.com.gori.scb.dao.impl.ConfiguracaoEmprestimoDAOImpl;
import br.com.gori.scb.dao.impl.EmprestimoDAOImpl;
import br.com.gori.scb.dao.impl.ExemplarDAOImpl;
import br.com.gori.scb.dao.impl.ItemEmprestimoDAOImpl;
import br.com.gori.scb.dao.impl.ItemReservaDAOImpl;
import br.com.gori.scb.dao.impl.PessoaDAOImpl;
import br.com.gori.scb.dao.impl.PublicacaoDAOImpl;
import br.com.gori.scb.entidade.ComprovanteEmprestimo;
import br.com.gori.scb.entidade.ConfiguracaoEmprestimo;
import br.com.gori.scb.entidade.Emprestimo;
import br.com.gori.scb.entidade.Exemplar;
import br.com.gori.scb.entidade.ItemEmprestimo;
import br.com.gori.scb.entidade.ItemReserva;
import br.com.gori.scb.entidade.Pessoa;
import br.com.gori.scb.entidade.Publicacao;
import br.com.gori.scb.entidade.util.EstadoExemplar;
import br.com.gori.scb.util.ConverterAutoComplete;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.Temporal;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Leonardo
 */
@ManagedBean
@SessionScoped
public class EmprestimoControlador implements Serializable {

    private Emprestimo emprestimo;
    private List<ItemEmprestimo> emprestimoFiltrado;
    private List<ItemEmprestimo> itensSelecionados;
    private List<ItemEmprestimo> itensEmprestado;
    private List<ItemEmprestimo> itensDevolFiltro;
    private List<ItemEmprestimo> itensDevolucao;
    private List<ItemReserva> reservasAbertas;
    private ItemReserva itemReserva;
    private ItemReservaDAOImpl itemReservaDAO;

    private List<Exemplar> listaDisponibilidade;
    private Integer qtdDisponivel;
    private Integer qtdDispReserva;

    private EmprestimoDAOImpl emprestimoDAO;
    private ItemEmprestimo itemEmprestimo;
    private ItemEmprestimoDAOImpl itemEmprestimoDAO;
    private ConfiguracaoEmprestimo configEmprestimo;
    private ConfiguracaoEmprestimoDAOImpl configuracaoDAO;
    private boolean visualizar;
    private boolean edicao;
    private boolean novo;
    private boolean emprestar;
    private Integer qtdEmprestada;
    private Integer qtdLimiteEmp;
    private String header;
    private String nome;
    private String obra;
    private String nomeExemplar;

    private Publicacao publicacaoFiltro;

    private List<Exemplar> exemplaresFiltrados;
    private List<Exemplar> exemplarDrop;
    private Date prazo;
    private Exemplar exemplarFiltro;

    private ComprovanteEmprestimo comprovante;
    private ComprovanteDAOImpl comprovanteDAO;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataEmprestimo;
    private ConverterAutoComplete converterPessoa;
    private PessoaDAOImpl pessoaDAO;
    private Exemplar exemplar;
    private ExemplarDAOImpl exemplarDAO;
    private ConverterAutoComplete converterPublicacao;
    private ConverterAutoComplete converterObra;
    private PublicacaoDAOImpl publicacaoDAO;

    private Date dataInicial;
    private Date dataFinal;
    private Date dtDevolIni;
    private Date dtDevolFinal;

    private String publicacaoPesquisa;
    private String pessoaPesquisa;
    private String tomboPesquisa;

    private String tomboEmprestimo;
    private String btn;
    private boolean salvo;

    public EmprestimoControlador() {
        newInstances();
    }

    private void newInstances() {
        this.emprestimo = new Emprestimo();
        this.emprestimoFiltrado = new ArrayList<ItemEmprestimo>();
        this.itensEmprestado = new ArrayList<ItemEmprestimo>();
        this.emprestimoDAO = new EmprestimoDAOImpl();
        this.itemEmprestimo = new ItemEmprestimo();
        this.itemEmprestimoDAO = new ItemEmprestimoDAOImpl();
        this.pessoaDAO = new PessoaDAOImpl();
        this.publicacaoDAO = new PublicacaoDAOImpl();
        this.exemplarDAO = new ExemplarDAOImpl();
        this.exemplar = new Exemplar();
        this.nomeExemplar = null;
        this.nome = "";
        this.publicacaoFiltro = new Publicacao();
        this.exemplarFiltro = new Exemplar();
        this.exemplaresFiltrados = new ArrayList<Exemplar>();
        this.exemplarDrop = new ArrayList<Exemplar>();
        this.reservasAbertas = new ArrayList<>();
        this.obra = "";
        this.emprestar = true;
        this.prazo = new Date();
        this.qtdLimiteEmp = 0;
        this.configEmprestimo = new ConfiguracaoEmprestimo();
        this.configuracaoDAO = new ConfiguracaoEmprestimoDAOImpl();
        this.comprovante = new ComprovanteEmprestimo();
        this.comprovanteDAO = new ComprovanteDAOImpl();
        this.itemReserva = new ItemReserva();
        this.itemReservaDAO = new ItemReservaDAOImpl();
        this.itensDevolFiltro = new ArrayList<>();
        this.itensEmprestado = new ArrayList<>();
        this.publicacaoPesquisa = "";
        this.pessoaPesquisa = "";
        dtDevolIni = null;
        dtDevolFinal = null;
        this.qtdDispReserva = 0;
        this.listaDisponibilidade = new ArrayList<>();
        tomboEmprestimo = "";
        btn = "complet";
        salvo = false;
    }

    public void inicializar() {
        newInstances();
    }

    public void salvarSemSair() {
        try {
            System.out.println("Salvando");
            comprovante.setEmprestimo(emprestimo);
            if (edicao) {
                emprestimoDAO.update(emprestimo);
                comprovanteDAO.update(comprovante);
            } else {
                emprestimoDAO.save(emprestimo);
                comprovanteDAO.save(comprovante);
            }
            JsfUtil.addSuccessMessage("Emprestimo salva com sucesso!");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao salvar emprestimo");
        }
    }

    public String salvar(String imprimir) {
        try {
            if (validarCampos()) {
                comprovante.setEmprestimo(emprestimo);
                for (ItemEmprestimo it : emprestimo.getItemEmprestimo()) {
                    it.getExemplar().setEstadoExemplar(EstadoExemplar.EMPRESTADO);
                }
                if (edicao) {
                    emprestimoDAO.update(emprestimo);
                    comprovanteDAO.update(comprovante);
                } else {
                    emprestimoDAO.save(emprestimo);
                    comprovanteDAO.save(comprovante);
                }
//            if ("Sim".equals(imprimir)) {
//                imprimeComprovante.emitir();
//            }
                salvo = true;
                newInstances();
                JsfUtil.addSuccessMessage("Emprestimo realizado com sucesso!");
                return "edita";
            } else {
                return null;
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao salvar emprestimo");
            return null;
        }
    }

    private boolean validarCampos() {
        try {
            if (emprestimo.getPessoa() == null) {
                JsfUtil.addErrorMessage("É necessário informar uma pessoa para realizar o empréstimo!");
                salvo = false;
                return false;
            }
            if (itemEmprestimo.getPrazo() == null) {
                JsfUtil.addErrorMessage("Verifique a data prazo de devolução!");
                salvo = false;
                return false;
            }
            if (emprestimo.getItemEmprestimo().isEmpty()) {
                JsfUtil.addErrorMessage("É necessário incluir ao menos um exemplar na lista de empréstimo!");
                salvo = false;
                return false;
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao validar campos obrigatórios, atualize a página e tente novamente");
            return false;
        }
        return true;
    }

    public void fecharConfirmacao(CloseEvent event) {
        if (salvo == true) {
            JsfUtil.addSuccessMessage("Emprestimo realizado com sucesso!");
        }
        RequestContext context = RequestContext.getCurrentInstance();
        RequestContext.getCurrentInstance().update("form-user:panelEmprestimo");
        RequestContext.getCurrentInstance().reset("form-user:panelEmprestimo");
    }

    public String prepararLista() {
        newInstances();
        return "lista";
    }

    public String prepararNovo() {
        newInstances();
        visualizar = false;
        edicao = false;
        novo = true;
        header = "Cadastrar Emprestimo";
        return "edita";
    }

    public String devolucao() {
        if (itensSelecionados.isEmpty()) {
            JsfUtil.addErrorMessage("Selecione um emprestimo para realizar a devolução");
            return null;
        } else {
            newInstances();
            visualizar = false;
            edicao = true;
            header = "Realizar Devolução dos Livros";
            return "devolucao";
        }
    }

    public List<ItemEmprestimo> getEmprestimos() {
        return emprestimoFiltrado;
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    public List<ItemEmprestimo> getEmprestimoFiltrado() {
        return emprestimoFiltrado;
    }

    public void setEmprestimoFiltrado(List<ItemEmprestimo> emprestimoFiltrado) {
        this.emprestimoFiltrado = emprestimoFiltrado;
    }

    public EmprestimoDAOImpl getEmprestimoDAO() {
        return emprestimoDAO;
    }

    public void pesquisaEmprestimo() {
        validarDisponibilidade();
        if (publicacaoFiltro != null && itemEmprestimo.getExemplar() != null) {
            JsfUtil.addErrorMessage("Selecione ou livro ou exemplar para pesquisa");
        } else if (qtdDisponivel > 0 && qtdDispReserva > 0) {
            if (exemplaresFiltrados == null || exemplaresFiltrados.isEmpty()) {
                exemplaresFiltrados = new ArrayList<Exemplar>();
            }
            if (publicacaoFiltro != null) {
                exemplaresFiltrados = emprestimoDAO.getExemplares(publicacaoFiltro.getId());
                if (exemplaresFiltrados.isEmpty()) {
                    JsfUtil.addErrorMessage("Não foram encontrados exemplares disponiveis na data atual, verifique empréstimos/devoluções");
                }
            } else if (itemEmprestimo.getExemplar() != null) {
                onExemplarAdiciona();
            } else {
                JsfUtil.addErrorMessage("Informe algum argumento de pesquisa para os livros");
            }
        } else {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String data = format.format(new Date());
            String data2 = format.format(itemEmprestimo.getPrazo());
            JsfUtil.addErrorMessage("Esta publicação não tem disponilidade de emprestimo para data: " + data + " e prazo devolução: " + data2 + ". Verifique Emprestimos/Reservas");
        }
    }

    public void setEmprestimoDAO(EmprestimoDAOImpl emprestimoDAO) {
        this.emprestimoDAO = emprestimoDAO;
    }

    public boolean isVisualizar() {
        return visualizar;
    }

    public void setVisualizar(boolean visualizar) {
        this.visualizar = visualizar;
    }

    public boolean isEdicao() {
        return edicao;
    }

    public void setEdicao(boolean edicao) {
        this.edicao = edicao;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getObra() {
        return obra;
    }

    public void setObra(String obra) {
        this.obra = obra;
    }

    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public void pesquisar(Long idPessoa) {
        if (validarDatas()) {
            if (idPessoa == 0) {
                idPessoa = null;
            }
            emprestimoFiltrado.clear();
            emprestimoFiltrado = new ArrayList<>();
            emprestimoFiltrado = emprestimoDAO.listarFiltros(nome, obra, dataInicial, dataFinal, dtDevolIni, dtDevolFinal, idPessoa);
            if (emprestimoFiltrado.isEmpty()) {
                JsfUtil.addSuccessMessage("Não foram encontradas informações referente a emprestimo/devolução com os parâmetros informados!");
            }

            nome = "";
            obra = "";
            dataInicial = null;
            dataFinal = null;
            dtDevolIni = null;
            dtDevolFinal = null;
            idPessoa = null;
        }
    }

    public ItemEmprestimoDAOImpl getItemEmprestimoDAO() {
        return itemEmprestimoDAO;
    }

    public void setItemEmprestimoDAO(ItemEmprestimoDAOImpl itemEmprestimoDAO) {
        this.itemEmprestimoDAO = itemEmprestimoDAO;
    }

    public void setConverterPessoa(ConverterAutoComplete converterPessoa) {
        this.converterPessoa = converterPessoa;
    }

    public PessoaDAOImpl getPessoaDAO() {
        return pessoaDAO;
    }

    public void setPessoaDAO(PessoaDAOImpl pessoaDAO) {
        this.pessoaDAO = pessoaDAO;
    }

    public void setConverterPublicacao(ConverterAutoComplete converterPublicacao) {
        this.converterPublicacao = converterPublicacao;
    }

    public void validaDiasDevolucao(String novo) {
        if ("Sim".equals(novo)) {
            emprestar = true;
            qtdEmprestada = 0;
            itensEmprestado = new ArrayList<>();
            reservasAbertas = new ArrayList<>();
            exemplarDrop.clear();
            exemplaresFiltrados.clear();
        }
        Date dataDevol;
        dataDevol = null;
        if (emprestimo != null) {
            Pessoa pessoa = emprestimo.getPessoa();
            if (pessoa != null) {
                String nomePessoa = pessoa.getNome();
                configEmprestimo = configuracaoDAO.buscarDiasEmprestimoPessoa(nomePessoa);
                Integer dias = configEmprestimo.getDias();
                qtdLimiteEmp = configEmprestimo.getVolumes();
                if (itensEmprestado.isEmpty()) {
                    itensEmprestado = emprestimoDAO.recuperarEmprestimosPessoa(nomePessoa);
                }
                qtdEmprestada = emprestimoDAO.buscarQtdEmprestadaPessoa(nomePessoa);
                reservasAbertas = itemReservaDAO.reservasAbertasPessoa(pessoa);
                validaQtdEmprestimo();
                dataDevol = new Date();
                dataDevol = addDays(dataDevol, dias);
            }
        }
        itemEmprestimo.setPrazo(dataDevol);
    }

    public void onEfetivaReserva(ItemReserva reserva) {
        this.itemReserva = new ItemReserva();
        this.itemReserva = reserva;
        this.itemReserva.setEfetivado(true);
        this.reservasAbertas.remove(reserva);
        this.exemplaresFiltrados = emprestimoDAO.getExemplares(this.itemReserva.getPublicacao().getId());
    }

    public void onExemplarAdiciona() {
        exemplarDrop.add(itemEmprestimo.getExemplar());
//        itemEmprestimo.setPrazo(prazo);
        itemEmprestimo.setEmprestimo(emprestimo);
        emprestimo.getItemEmprestimo().add(itemEmprestimo);
        itensEmprestado.add(itemEmprestimo);
        itemEmprestimo = new ItemEmprestimo();
        nomeExemplar = null;
        validaDiasDevolucao("Nao");
    }

    public void onDataPrazoSelect(SelectEvent event) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            itemEmprestimo.setPrazo((Date) event.getObject());
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao atualizar data");
        }

    }

    public void onExemplarDrop(DragDropEvent ddEvent) {
//        itemEmprestimo.setEmprestimo(emprestimo);
//        emprestimo.getItemEmprestimo().add(itemEmprestimo);
//        itemEmprestimo.getExemplar().setEstadoExemplar(EstadoExemplar.EMPRESTADO);
//        itensEmprestado.add(itemEmprestimo);
//        itemEmprestimo = new ItemEmprestimo();
//        nomeExemplar = null;
//        validaDiasDevolucao();

        Exemplar exem = new Exemplar();
        exem = (Exemplar) ddEvent.getData();

        exemplarDrop.add(exem);
        exemplaresFiltrados.remove(exem);

        itemEmprestimo.setExemplar(exem);
        itemEmprestimo.setEmprestimo(emprestimo);
        emprestimo.getItemEmprestimo().add(itemEmprestimo);

        itemEmprestimo = new ItemEmprestimo();

        itensEmprestado.add(itemEmprestimo);
        validaDiasDevolucao("Nao");
        nomeExemplar = null;

    }

    public PublicacaoDAOImpl getPublicacaoDAO() {
        return publicacaoDAO;
    }

    public void setPublicacaoDAO(PublicacaoDAOImpl publicacaoDAO) {
        this.publicacaoDAO = publicacaoDAO;
    }

    public ItemEmprestimo getItemEmprestimo() {
        return itemEmprestimo;
    }

    public void setItemEmprestimo(ItemEmprestimo itemEmprestimo) {
        this.itemEmprestimo = itemEmprestimo;
    }

    public ExemplarDAOImpl getExemplarDAO() {
        return exemplarDAO;
    }

    public void setExemplarDAO(ExemplarDAOImpl exemplarDAO) {
        this.exemplarDAO = exemplarDAO;
    }

    public ConfiguracaoEmprestimoDAOImpl getConfiguracaoDAO() {
        return configuracaoDAO;
    }

    public void setConfiguracaoDAO(ConfiguracaoEmprestimoDAOImpl configuracaoDAO) {
        this.configuracaoDAO = configuracaoDAO;
    }

    public List<Pessoa> completaPessoa(String value) {
        return emprestimoDAO.getPessoas(value.toLowerCase());
    }

    public List<Publicacao> completaObra(String value) {
        return emprestimoDAO.getPublic(value.toLowerCase());
    }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public List<Exemplar> completaPublicacao(String value) {
        return emprestimoDAO.getPublicacoes(value.toLowerCase());
    }

    public List<Publicacao> completaAllPublicacao(String value) {
        return emprestimoDAO.getPublic(value.toLowerCase());
    }

    public List<Exemplar> completaExem(String value) {
        return emprestimoDAO.getAllExemplares(value.toLowerCase());
    }

    public ConverterAutoComplete getConverterPessoa() {
        if (converterPessoa == null) {
            converterPessoa = new ConverterAutoComplete(Pessoa.class, pessoaDAO);
        }
        return converterPessoa;
    }

    public String getNomeExemplar() {
        return nomeExemplar;
    }

    public Exemplar getExemplar() {
        return exemplar;
    }

    public void setExemplar(Exemplar exemplar) {
        this.exemplar = exemplar;
    }

    public void completarExemp() {
        nomeExemplar = null;

        if (tomboPesquisa != null) {
            System.out.println("TOMBO: " + tomboPesquisa);
        }
    }

    public void completarExemplar() {
        nomeExemplar = null;
        if (itemEmprestimo != null) {
            Exemplar exem = new Exemplar();
            exem = itemEmprestimo.getExemplar();
            System.out.println("Caiu aqui - Passo 1");
            if (exem != null) {
                Publicacao obraLit = exem.getPublicacao();
                System.out.println("Caiu aqui - Passo 2");
                if (obraLit != null) {
                    System.out.println("Vai escrever o nome!! ");
                    nomeExemplar = "Nº Exe: " + itemEmprestimo.getExemplar().getNumExe()
                            + ". " + itemEmprestimo.getExemplar().getPublicacao().getTitulo()
                            + ", " + itemEmprestimo.getExemplar().getPublicacao().getSubtitulo();
                }
            }
        }
    }

    public void validaQtdEmprestimo() {
        try {
            if (itensEmprestado.size() >= qtdLimiteEmp) {
                JsfUtil.addErrorMessage("Esta pessoa atingiu o limite de empréstimo");
                emprestar = false;
            } else {
                emprestar = true;
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao validar quantidade emprestada");
        }
    }

    public ConverterAutoComplete getConverterPublicacao() {
        if (converterPublicacao == null) {
            converterPublicacao = new ConverterAutoComplete(Exemplar.class, exemplarDAO);
        }
        novo = false;
        return converterPublicacao;
    }

    public boolean isNovo() {
        return novo;
    }

    public void setNovo(boolean novo) {
        this.novo = novo;
    }

    public void setNomeExemplar(String nomeExemplar) {
        this.nomeExemplar = nomeExemplar;
    }

    public boolean isEmprestar() {
        return emprestar;
    }

    public void setEmprestar(boolean emprestar) {
        this.emprestar = emprestar;
    }

    public Integer getQtdEmprestada() {
        return qtdEmprestada;
    }

    public void setQtdEmprestada(Integer qtdEmprestada) {
        this.qtdEmprestada = qtdEmprestada;
    }

    public Integer getQtdLimiteEmp() {
        return qtdLimiteEmp;
    }

    public void setQtdLimiteEmp(Integer qtdLimiteEmp) {
        this.qtdLimiteEmp = qtdLimiteEmp;
    }

    public List<ItemEmprestimo> getItensEmprestado() {
        return itensEmprestado;
    }

    public void setItensEmprestado(List<ItemEmprestimo> itensEmprestado) {
        this.itensEmprestado = itensEmprestado;
    }

    public ConfiguracaoEmprestimo getConfigEmprestimo() {
        return configEmprestimo;
    }

    public void setConfigEmprestimo(ConfiguracaoEmprestimo configEmprestimo) {
        this.configEmprestimo = configEmprestimo;
    }

    public List<ItemEmprestimo> getItensSelecionados() {
        return itensSelecionados;
    }

    public void setItensSelecionados(List<ItemEmprestimo> itensSelecionados) {
        this.itensSelecionados = itensSelecionados;
    }

    public void preencheDevolucao() {

        for (ItemEmprestimo it : itensSelecionados) {
            it.setDevolucao(itemEmprestimo.getDevolucao());
        }

    }

    public Publicacao getPublicacaoFiltro() {
        return publicacaoFiltro;
    }

    public void setPublicacaoFiltro(Publicacao publicacaoFiltro) {
        this.publicacaoFiltro = publicacaoFiltro;
    }

    public ConverterAutoComplete getConverterObra() {
        if (converterObra == null) {
            converterObra = new ConverterAutoComplete(Publicacao.class, publicacaoDAO);
        }
        return converterObra;
    }

    public void setConverterObra(ConverterAutoComplete converterObra) {
        this.converterObra = converterObra;
    }

    public Exemplar getExemplarFiltro() {
        return exemplarFiltro;
    }

    public void setExemplarFiltro(Exemplar exemplarFiltro) {
        this.exemplarFiltro = exemplarFiltro;
    }

    public List<Exemplar> getExemplaresFiltrados() {
        return exemplaresFiltrados;
    }

    public void setExemplaresFiltrados(List<Exemplar> exemplaresFiltrados) {
        this.exemplaresFiltrados = exemplaresFiltrados;
    }

    public List<Exemplar> getExemplarDrop() {
        return exemplarDrop;
    }

    public void setExemplarDrop(List<Exemplar> exemplarDrop) {
        this.exemplarDrop = exemplarDrop;
    }

    public Date getPrazo() {
        return prazo;
    }

    public void setPrazo(Date prazo) {
        this.prazo = prazo;
    }

    public ComprovanteEmprestimo getComprovante() {
        return comprovante;
    }

    public void setComprovante(ComprovanteEmprestimo comprovante) {
        this.comprovante = comprovante;
    }

    public ComprovanteDAOImpl getComprovanteDAO() {
        return comprovanteDAO;
    }

    public void setComprovanteDAO(ComprovanteDAOImpl comprovanteDAO) {
        this.comprovanteDAO = comprovanteDAO;
    }

    public List<ItemReserva> getReservasAbertas() {
        return reservasAbertas;
    }

    public void setReservasAbertas(List<ItemReserva> reservasAbertas) {
        this.reservasAbertas = reservasAbertas;
    }

    public ItemReserva getItemReserva() {
        return itemReserva;
    }

    public void setItemReserva(ItemReserva itemReserva) {
        this.itemReserva = itemReserva;
    }

    public ItemReservaDAOImpl getItemReservaDAO() {
        return itemReservaDAO;
    }

    public void setItemReservaDAO(ItemReservaDAOImpl itemReservaDAO) {
        this.itemReservaDAO = itemReservaDAO;
    }

    public List<ItemEmprestimo> getItensDevolFiltro() {
        return itensDevolFiltro;
    }

    public void setItensDevolFiltro(List<ItemEmprestimo> itensDevolFiltro) {
        this.itensDevolFiltro = itensDevolFiltro;
    }

    public List<ItemEmprestimo> getItensDevolucao() {
        return itensDevolucao;
    }

    public void setItensDevolucao(List<ItemEmprestimo> itensDevolucao) {
        this.itensDevolucao = itensDevolucao;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    private boolean validarDatas() {
        if (dataInicial != null) {
            if (dataFinal == null) {
                JsfUtil.addErrorMessage("Informe uma data final para continuar com a pesquisa");
                return false;
            } else if (dataFinal.before(dataInicial)) {
                JsfUtil.addErrorMessage("Data final menor que a data inicial, verifique!");
                return false;
            } else {
                return true;
            }
        } else if (dataFinal != null) {
            JsfUtil.addErrorMessage("Informe uma data inicial para continuar com a pesquisa");
            return false;
        } else {
            return true;
        }
    }

    public void pesquisarDevol() {
        if (validarDatas()) {
            if (!"".equals(tomboPesquisa)) {
                publicacaoPesquisa = "";
                pessoaPesquisa = "";
                dataInicial = null;
                dataFinal = null;
            }
            itensDevolFiltro = itemEmprestimoDAO.filtrarDevolPendente(publicacaoPesquisa, pessoaPesquisa, tomboPesquisa, dataInicial, dataFinal);
            if (itensDevolFiltro.isEmpty()) {
                JsfUtil.addSuccessMessage("Não foram encontradas devoluções pendentes com os parâmetros informados!");
            }
            publicacaoPesquisa = "";
            pessoaPesquisa = "";
            dataInicial = null;
            dataFinal = null;
            tomboPesquisa = "";
        }
    }

    public String realizaDevolucao() {
        if (itensDevolFiltro.isEmpty()) {
            JsfUtil.addErrorMessage("Recupere e selecione pelo menos um emprestimo a ser devolvido");
            return null;
        }
        if (itensDevolucao.isEmpty()) {
            JsfUtil.addErrorMessage("Selecione pelo menos um emprestimo para realizar a devolução");
            return null;
        }
        for (ItemEmprestimo it : itensDevolucao) {
            it.getExemplar().setEstadoExemplar(EstadoExemplar.DISPONIVEL);
            it.setDevolucao(new Date());
            itensDevolFiltro.remove(it);
            itensDevolucao.remove(it);
        }
        try {
            emprestimoDAO.update(emprestimo);
            JsfUtil.addSuccessMessage("Devolução realizada com sucesso!");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao salvar devolução");
        }
        return null;
    }

    public void testando(String numero) {
        System.out.println("Caiu aqui: " + numero);
    }

    public String getPublicacaoPesquisa() {
        return publicacaoPesquisa;
    }

    public void setPublicacaoPesquisa(String publicacaoPesquisa) {
        this.publicacaoPesquisa = publicacaoPesquisa;
    }

    public String getPessoaPesquisa() {
        return pessoaPesquisa;
    }

    public void setPessoaPesquisa(String pessoaPesquisa) {
        this.pessoaPesquisa = pessoaPesquisa;
    }

    public String getTomboPesquisa() {
        return tomboPesquisa;
    }

    public void setTomboPesquisa(String tomboPesquisa) {
        this.tomboPesquisa = tomboPesquisa;
    }

    public Date getDtDevolIni() {
        return dtDevolIni;
    }

    public void setDtDevolIni(Date dtDevolIni) {
        this.dtDevolIni = dtDevolIni;
    }

    public Date getDtDevolFinal() {
        return dtDevolFinal;
    }

    public void setDtDevolFinal(Date dtDevolFinal) {
        this.dtDevolFinal = dtDevolFinal;
    }

    public void validarDisponibilidade() {
        if (publicacaoFiltro != null || itemEmprestimo.getExemplar() != null) {
            listaDisponibilidade = new ArrayList<>();
            Publicacao pub = new Publicacao();
            if (publicacaoFiltro != null) {
                pub = publicacaoFiltro;
            } else if (itemEmprestimo.getExemplar() != null) {
                pub = itemEmprestimo.getExemplar().getPublicacao();
            }
            if (pub != null) {
                listaDisponibilidade = exemplarDAO.verificarDisponibilidade(pub, null, itemEmprestimo);
                qtdDisponivel = listaDisponibilidade.size();
                listaDisponibilidade = new ArrayList<>();
                listaDisponibilidade = exemplarDAO.verificarDispReserva(pub, null, itemEmprestimo);
                qtdDispReserva = listaDisponibilidade.size();
            }
        }
    }

    public List<Exemplar> getListaDisponibilidade() {
        return listaDisponibilidade;
    }

    public void setListaDisponibilidade(List<Exemplar> listaDisponibilidade) {
        this.listaDisponibilidade = listaDisponibilidade;
    }

    public Integer getQtdDisponivel() {
        return qtdDisponivel;
    }

    public void setQtdDisponivel(Integer qtdDisponivel) {
        this.qtdDisponivel = qtdDisponivel;
    }

    public Integer getQtdDispReserva() {
        return qtdDispReserva;
    }

    public void setQtdDispReserva(Integer qtdDispReserva) {
        this.qtdDispReserva = qtdDispReserva;
    }

    public void completarExemplarTeste() {
        nomeExemplar = null;
        if (tomboEmprestimo != null) {
            List<Exemplar> exem = new ArrayList<>();
            if (!exem.isEmpty()) {
                Publicacao obraLit = exem.get(0).getPublicacao();
                if (obraLit != null) {
                    nomeExemplar = "Nº Exe: " + itemEmprestimo.getExemplar().getNumExe()
                            + ". " + itemEmprestimo.getExemplar().getPublicacao().getTitulo()
                            + ", " + itemEmprestimo.getExemplar().getPublicacao().getSubtitulo();
                }
            }
        }
    }

    public String getTomboEmprestimo() {
        return tomboEmprestimo;
    }

    public void setTomboEmprestimo(String tomboEmprestimo) {
        this.tomboEmprestimo = tomboEmprestimo;
    }

    public void completaExemplarEmp(AjaxBehaviorEvent e) {
        nomeExemplar = null;

        if (itemEmprestimo != null) {
            Exemplar exem = itemEmprestimo.getExemplar();
            if (exem != null) {
                Publicacao obraLit = exem.getPublicacao();
                if (obraLit != null) {
                    nomeExemplar = "Nº Exe: " + itemEmprestimo.getExemplar().getNumExe()
                            + ". " + itemEmprestimo.getExemplar().getPublicacao().getTitulo()
                            + ", " + itemEmprestimo.getExemplar().getPublicacao().getSubtitulo();
                }
            }
        }
    }

    public void btnDevolSubmit() {
        if (!"".equals(tomboEmprestimo)) {
            Exemplar exem = exemplarDAO.recuperaExemplarEmpPorTombo(tomboEmprestimo);
            if (exem != null) {
                completarExemplar();
                List<ItemEmprestimo> itemEmp = new ArrayList<>();
                itemEmp = itemEmprestimoDAO.recuperarEmprestimosAbertosTombo(tomboEmprestimo);
                itensDevolFiltro.addAll(itemEmp);
                itensDevolucao.addAll(itemEmp);
                tomboEmprestimo = "";
                nomeExemplar = "";
            } else {
                JsfUtil.addErrorMessage("Exemplar não encontrado na lista de empréstimo, verifique emprestimos/devolução ou cadastro do código de exemplar!");
            }
        } else {
            nomeExemplar = "";
        }
    }

    public void btnSubmit() {
        if (!"".equals(tomboEmprestimo)) {
            Exemplar exem = exemplarDAO.recuperaExemplarPorTombo(tomboEmprestimo);
            if (exem != null) {
                itemEmprestimo.setExemplar(exem);
                completarExemplar();
            } else {
                JsfUtil.addErrorMessage("Exemplar não está disponível na data atual, verifique emprestimos/devolução ou cadastro do código de exemplar!");
            }
        } else {
            nomeExemplar = "";
        }
    }

    public void emitirMensagem() {
        JsfUtil.addSuccessMessage("Emprestimo realizado com sucesso");
    }

    public void resetFields() {
        RequestContext.getCurrentInstance().reset("form-user:panelEmprestimo");
    }

    public String getBtn() {
        return btn;
    }

    public void setBtn(String btn) {
        this.btn = btn;
    }

    public boolean isSalvo() {
        return salvo;
    }

    public void setSalvo(boolean salvo) {
        this.salvo = salvo;
    }

}
