package br.com.gori.scb.controle;

import br.com.gori.scb.controle.util.JsfUtil;
import br.com.gori.scb.dao.impl.ConfiguracaoEmprestimoDAOImpl;
import br.com.gori.scb.dao.impl.ExemplarDAOImpl;
import br.com.gori.scb.dao.impl.ItemEmprestimoDAOImpl;
import br.com.gori.scb.dao.impl.ItemReservaDAOImpl;
import br.com.gori.scb.dao.impl.PessoaDAOImpl;
import br.com.gori.scb.dao.impl.PublicacaoDAOImpl;
import br.com.gori.scb.dao.impl.ReservaDAOImpl;
import br.com.gori.scb.entidade.ConfiguracaoEmprestimo;
import br.com.gori.scb.entidade.Exemplar;
import br.com.gori.scb.entidade.ItemEmprestimo;
import br.com.gori.scb.entidade.ItemReserva;
import br.com.gori.scb.entidade.Pessoa;
import br.com.gori.scb.entidade.Publicacao;
import br.com.gori.scb.entidade.Reserva;
import br.com.gori.scb.util.ConverterAutoComplete;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Temporal;

/**
 *
 * @author Leonardo
 */
@ManagedBean
@SessionScoped
public class ReservaControlador implements Serializable {

    private Reserva reserva;
    private Reserva reservaSelecionada;
    private ReservaDAOImpl reservaDAO;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataInicial;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataFinal;
    private ItemReserva itemReserva;
    private ItemReservaDAOImpl itemReservaDAO;
    private List<ItemReserva> itensReservas;
    private List<ItemReserva> itensFiltrados;
    private List<Exemplar> listaDisponibilidade;
    private Integer qtdDisponivel;
    private Integer qtdDispReserva;
    private Integer diasDevolucao;
    private Date dataDevol;

    private Pessoa pessoa;
    private PessoaDAOImpl pessoaDAO;
    private Publicacao publicacao;
    private PublicacaoDAOImpl publicacaoDAO;
    private ConverterAutoComplete converterPessoa;
    private ConverterAutoComplete converterPublicacao;
    private boolean edicao;
    private boolean visualizar;
    private boolean reservar;
    private String header;
    private Integer qtdLimite;
    private Integer qtdEmpRes;

    private ExemplarDAOImpl exemplarDAO;
    private ConfiguracaoEmprestimo configEmprestimo;
    private ConfiguracaoEmprestimoDAOImpl configuracaoDAO;
    private ItemEmprestimoDAOImpl itemEmprestimoDAO;
    private List<ItemEmprestimo> itensEmprestimo;

    private String publicacaoPesquisa;
    private String pessoaPesquisa;

    public ReservaControlador() {
        newInstances();
    }

    private void newInstances() {
        this.reserva = new Reserva();
        this.reservaDAO = new ReservaDAOImpl();
        this.pessoa = new Pessoa();
        this.pessoaDAO = new PessoaDAOImpl();
        this.publicacao = new Publicacao();
        this.publicacaoDAO = new PublicacaoDAOImpl();
        this.itemReserva = new ItemReserva();
        this.itemReservaDAO = new ItemReservaDAOImpl();
        this.itemEmprestimoDAO = new ItemEmprestimoDAOImpl();
        this.configEmprestimo = new ConfiguracaoEmprestimo();
        this.configuracaoDAO = new ConfiguracaoEmprestimoDAOImpl();
        this.reservar = true;
        this.itensEmprestimo = new ArrayList<>();
        this.itensReservas = new ArrayList<>();
        this.listaDisponibilidade = new ArrayList<>();
        this.exemplarDAO = new ExemplarDAOImpl();
        this.itensFiltrados = new ArrayList<>();
        this.publicacaoPesquisa = "";
        this.pessoaPesquisa = "";
        this.qtdDispReserva = 0;
    }

    public String salvar() {
        try {
            if (edicao) {
                reservaDAO.update(reserva);
            } else {
                reservaDAO.save(reserva);
            }
            JsfUtil.addSuccessMessage("Reserva salva com sucessa!");
            return prepararLista();
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Erro ao salvar reserva: " + ex.getMessage());
            return null;
        }
    }

    public String excluir() {
        try {
            reservaDAO.delete(reservaSelecionada);
            reservaSelecionada = null;
            JsfUtil.addSuccessMessage("Reserva removida com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao excluir reserva");
            return null;
        }
    }

    public void pesquisar() {
        if (validarDatas()) {
            itensFiltrados = itemReservaDAO.filtrarReservas(publicacaoPesquisa, pessoaPesquisa, dataInicial, dataFinal);
        }
    }

    public String prepararLista() {
        newInstances();
        if (reservaSelecionada != null) {
            reservaSelecionada = null;
        }
        return "lista";
    }

    public String prepararEdicao() {
        newInstances();
        reserva = reservaSelecionada;
        visualizar = false;
        edicao = true;
        header = "Editar Reserva";
        return "edita";
    }

    public String prepararNovo() {
        newInstances();
        visualizar = false;
        edicao = false;
        header = "Cadastrar Reserva";
        return "edita";
    }

    public String prepararVer() {
        newInstances();
        reserva = reservaSelecionada;
        edicao = false;
        header = "Visualizar Reserva";
        visualizar = true;
        return "edita";
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Reserva getReservaSelecionada() {
        return reservaSelecionada;
    }

    public void setReservaSelecionada(Reserva reservaSelecionada) {
        this.reservaSelecionada = reservaSelecionada;
    }

    public ReservaDAOImpl getReservaDAO() {
        return reservaDAO;
    }

    public void setReservaDAO(ReservaDAOImpl reservaDAO) {
        this.reservaDAO = reservaDAO;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public PessoaDAOImpl getPessoaDAO() {
        return pessoaDAO;
    }

    public void setPessoaDAO(PessoaDAOImpl pessoaDAO) {
        this.pessoaDAO = pessoaDAO;
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

    public void setConverterPessoa(ConverterAutoComplete converterPessoa) {
        this.converterPessoa = converterPessoa;
    }

    public void setConverterPublicacao(ConverterAutoComplete converterPublicacao) {
        this.converterPublicacao = converterPublicacao;
    }

    public boolean isEdicao() {
        return edicao;
    }

    public void setEdicao(boolean edicao) {
        this.edicao = edicao;
    }

    public boolean isVisualizar() {
        return visualizar;
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

    public List<Pessoa> completaPessoa(String value) {
        return reservaDAO.getPessoas(value.toLowerCase());
    }

    public List<Publicacao> completaPublicacao(String value) {
        return reservaDAO.getPublicacao(value.toLowerCase());
    }

    public ConverterAutoComplete getConverterPessoa() {
        if (converterPessoa == null) {
            converterPessoa = new ConverterAutoComplete(Pessoa.class, pessoaDAO);
        }
        return converterPessoa;
    }

    public ConverterAutoComplete getConverterPublicacao() {
        if (converterPublicacao == null) {
            converterPublicacao = new ConverterAutoComplete(Publicacao.class, publicacaoDAO);
        }
        return converterPublicacao;
    }

    public List<Reserva> getReservas() {
        return reservaDAO.listAll();
    }

    public List<ItemReserva> getItensReservas() {
        return itemReservaDAO.listAll();
    }

    public ItemReserva getItemReserva() {
        return itemReserva;
    }

    public void setItemReserva(ItemReserva itemReserva) {
        this.itemReserva = itemReserva;
    }

    public boolean isReservar() {
        return reservar;
    }

    public ExemplarDAOImpl getExemplarDAO() {
        return exemplarDAO;
    }

    public void setExemplarDAO(ExemplarDAOImpl exemplarDAO) {
        this.exemplarDAO = exemplarDAO;
    }

    public void setReservar(boolean reservar) {
        this.reservar = reservar;
    }

    public ItemReservaDAOImpl getItemReservaDAO() {
        return itemReservaDAO;
    }

    public void setItemReservaDAO(ItemReservaDAOImpl itemReservaDAO) {
        this.itemReservaDAO = itemReservaDAO;
    }

    public ConfiguracaoEmprestimo getConfigEmprestimo() {
        return configEmprestimo;
    }

    public void setConfigEmprestimo(ConfiguracaoEmprestimo configEmprestimo) {
        this.configEmprestimo = configEmprestimo;
    }

    public Integer getQtdEmpRes() {
        return qtdEmpRes;
    }

    public void setQtdEmpRes(Integer qtdEmpRes) {
        this.qtdEmpRes = qtdEmpRes;
    }

    public List<ItemEmprestimo> getItensEmprestimo() {
        return itensEmprestimo;
    }

    public void setItensEmprestimo(List<ItemEmprestimo> itensEmprestimo) {
        this.itensEmprestimo = itensEmprestimo;
    }

    public List<Exemplar> getListaDisponibilidade() {
        return listaDisponibilidade;
    }

    public void setListaDisponibilidade(List<Exemplar> listaDisponibilidade) {
        this.listaDisponibilidade = listaDisponibilidade;
    }

    public ConfiguracaoEmprestimoDAOImpl getConfiguracaoDAO() {
        return configuracaoDAO;
    }

    public void setConfiguracaoDAO(ConfiguracaoEmprestimoDAOImpl configuracaoDAO) {
        this.configuracaoDAO = configuracaoDAO;
    }

    public ItemEmprestimoDAOImpl getItemEmprestimoDAO() {
        return itemEmprestimoDAO;
    }

    public void setItemEmprestimoDAO(ItemEmprestimoDAOImpl itemEmprestimoDAO) {
        this.itemEmprestimoDAO = itemEmprestimoDAO;
    }

    public void setItensReservas(List<ItemReserva> itensReservas) {
        this.itensReservas = itensReservas;
    }

    public Integer getQtdLimite() {
        return qtdLimite;
    }

    public void setQtdLimite(Integer qtdLimite) {
        this.qtdLimite = qtdLimite;
    }

    public void adicionaReserva() {
        dataDevol = itemReserva.getPrevisao();
        dataDevol = addDays(dataDevol, diasDevolucao);
        itemReserva.setDevolucao(dataDevol);
        validarDisponibilidade();
        if (qtdDisponivel > 0 && qtdDispReserva > 0) {
            itemReserva.setReserva(reserva);
            reserva.getItemReserva().add(itemReserva);
            itensReservas.add(itemReserva);
            qtdEmpRes = itensReservas.size() + itensEmprestimo.size();
            itemReserva = new ItemReserva();
            recuperaEmprestimoReserva("Nao");
        } else {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String data = format.format(itemReserva.getPrevisao());
            JsfUtil.addErrorMessage("Esta publicação não tem disponilidade de reserva para data: " + data + ". Verifique Emprestimos/Reservas");
        }
    }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public void validarDisponibilidade() {
        listaDisponibilidade = new ArrayList<>();
        if (itemReserva != null) {
            Publicacao pub = itemReserva.getPublicacao();
            if (pub != null) {
                listaDisponibilidade = exemplarDAO.verificarDisponibilidade(pub, itemReserva);
                qtdDisponivel = listaDisponibilidade.size();
                System.out.println("Disponivel 1: " + qtdDisponivel);
                listaDisponibilidade = new ArrayList<>();
                listaDisponibilidade = exemplarDAO.verificarDispReserva(pub, itemReserva);
                qtdDispReserva = listaDisponibilidade.size();
                System.out.println("Disponibilidade 2: " + qtdDispReserva);
            }
        }
    }

    public void recuperaEmprestimoReserva(String novo) {
        if ("Sim".equals(novo)) {
            reservar = true;
            qtdEmpRes = 0;
            itensEmprestimo = new ArrayList<>();
            itensReservas = new ArrayList<>();
            reserva.getItemReserva().clear();
        }
        if (reserva != null) {
            Pessoa p = reserva.getPessoa();
            if (p != null) {
                String nome = p.getNome();
                p = pessoaDAO.recuperaReservasEEmprestimos(p);
                if (itensReservas.isEmpty()) {
                    itensReservas.addAll(itemReservaDAO.recuperarReservasAbertaPessoa(p));
                    itensEmprestimo.addAll(itemEmprestimoDAO.recuperaEmprestimosAbertosPessoa(p));
                    qtdEmpRes = itensReservas.size() + itensEmprestimo.size();
                }
                configEmprestimo = configuracaoDAO.buscarDiasEmprestimoPessoa(nome);
                qtdLimite = configEmprestimo.getVolumes();
                diasDevolucao = configEmprestimo.getDias();
//                validaQtdEmprestimoReserva();
            }
        }
    }

    public void validaQtdEmprestimoReserva() {
        try {
            if (qtdEmpRes >= qtdLimite) {
                JsfUtil.addErrorMessage("Esta pessoa atingiu o limite de reservas");
                reservar = false;
            } else {
                reservar = true;
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao validar quantidade reservas");
        }
    }

    public Integer getQtdDisponivel() {
        return qtdDisponivel;
    }

    public void setQtdDisponivel(Integer qtdDisponivel) {
        this.qtdDisponivel = qtdDisponivel;
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

    public List<ItemReserva> getItensFiltrados() {
        return itensFiltrados;
    }

    public void setItensFiltrados(List<ItemReserva> itensFiltrados) {
        this.itensFiltrados = itensFiltrados;
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

    public Integer getQtdDispReserva() {
        return qtdDispReserva;
    }

    public void setQtdDispReserva(Integer qtdDispReserva) {
        this.qtdDispReserva = qtdDispReserva;
    }

    public Integer getDiasDevolucao() {
        return diasDevolucao;
    }

    public void setDiasDevolucao(Integer diasDevolucao) {
        this.diasDevolucao = diasDevolucao;
    }

    public Date getDataDevol() {
        return dataDevol;
    }

    public void setDataDevol(Date dataDevol) {
        this.dataDevol = dataDevol;
    }

}
