package br.com.gori.scb.reportcontrole;

import br.com.gori.scb.controle.util.JsfUtil;
import br.com.gori.scb.dao.impl.EmprestimoDAOImpl;
import br.com.gori.scb.dao.impl.ExemplarDAOImpl;
import br.com.gori.scb.dao.impl.PessoaDAOImpl;
import br.com.gori.scb.dao.impl.PublicacaoDAOImpl;
import br.com.gori.scb.entidade.Exemplar;
import br.com.gori.scb.entidade.Pessoa;
import br.com.gori.scb.entidade.Publicacao;
import br.com.gori.scb.util.ConverterAutoComplete;
import java.io.Serializable;
import java.util.Date;
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
public class EmprestimoAtrasoControlador implements Serializable {

    @Autowired
    private FacesContext facesContext;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private EntityManager manager;

    private EmprestimoDAOImpl emprestimoDAO;
    private ExemplarDAOImpl exemplarDAO;
    private PublicacaoDAOImpl publicacaoDAO;
    private PessoaDAOImpl pessoaDAO;
    private Exemplar exemplar;
    private Publicacao publicacao;
    private Pessoa pessoa;
    private ConverterAutoComplete converterObra;
    private ConverterAutoComplete converterPublicacao;
    private ConverterAutoComplete converterPessoa;
    private Date dataInicial;
    private Date dataFinal;
    private Date dtDevolIni;
    private Date dtDevolFinal;

    public EmprestimoAtrasoControlador() {
        newInstances();
    }

    public void newInstances() {
        this.emprestimoDAO = new EmprestimoDAOImpl();
        this.exemplarDAO = new ExemplarDAOImpl();
        this.publicacaoDAO = new PublicacaoDAOImpl();
        this.pessoaDAO = new PessoaDAOImpl();
        this.exemplar = new Exemplar();
        this.publicacao = new Publicacao();
        this.pessoa = new Pessoa();
        this.dataInicial = null;
        this.dataFinal = null;
        this.dtDevolIni = null;
        this.dtDevolFinal = null;
    }

    public void emitir() {

        Map<String, Object> parametros = new HashMap<>();

        parametros.put("IMAGEM", getCaminho());
        parametros.put("SQL", gerarSql());
//        System.out.println(parametros.toString());
        ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/devolatraso.jasper",
                this.response, parametros, "Emprestimo em Atraso.pdf");

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
        if (exemplar != null) {
            sb.append(" and e.id = ").append(exemplar.getId());
        }
        if (pessoa != null) {
            sb.append(" and c.id = ").append(pessoa.getId());
        }
        if (publicacao != null) {
            sb.append(" and f.id = ").append(publicacao.getId());
        }
        if (dataInicial != null && dataFinal != null) {
            sb.append(" and a.cricao between '").append(dataInicial).append("' and '").append(dataFinal).append("' ");
        }
        if (dtDevolIni != null && dtDevolFinal != null) {
            sb.append(" and b.prazo between '").append(dtDevolIni).append("' and '").append(dtDevolFinal).append("' ");
        }
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

    public List<Exemplar> completaExem(String value) {
        return emprestimoDAO.getAllExemplares(value.toLowerCase());
    }

    public List<Pessoa> completaPessoa(String value) {
        return emprestimoDAO.getPessoas(value.toLowerCase());
    }

    public List<Publicacao> completaObra(String value) {
        return emprestimoDAO.getPublic(value.toLowerCase());
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

    public ConverterAutoComplete getConverterPublicacao() {
        if (converterPublicacao == null) {
            converterPublicacao = new ConverterAutoComplete(Exemplar.class, exemplarDAO);
        }
        return converterPublicacao;
    }

    public void setConverterPublicacao(ConverterAutoComplete converterPublicacao) {
        this.converterPublicacao = converterPublicacao;
    }

    public ConverterAutoComplete getConverterPessoa() {
        if (converterPessoa == null) {
            converterPessoa = new ConverterAutoComplete(Pessoa.class, pessoaDAO);
        }
        return converterPessoa;
    }

    public void setConverterPessoa(ConverterAutoComplete converterPessoa) {
        this.converterPessoa = converterPessoa;
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

    public ExemplarDAOImpl getExemplarDAO() {
        return exemplarDAO;
    }

    public void setExemplarDAO(ExemplarDAOImpl exemplarDAO) {
        this.exemplarDAO = exemplarDAO;
    }

    public PublicacaoDAOImpl getPublicacaoDAO() {
        return publicacaoDAO;
    }

    public void setPublicacaoDAO(PublicacaoDAOImpl publicacaoDAO) {
        this.publicacaoDAO = publicacaoDAO;
    }

    public PessoaDAOImpl getPessoaDAO() {
        return pessoaDAO;
    }

    public void setPessoaDAO(PessoaDAOImpl pessoaDAO) {
        this.pessoaDAO = pessoaDAO;
    }

    public Exemplar getExemplar() {
        return exemplar;
    }

    public void setExemplar(Exemplar exemplar) {
        this.exemplar = exemplar;
    }

    public Publicacao getPublicacao() {
        return publicacao;
    }

    public void setPublicacao(Publicacao publicacao) {
        this.publicacao = publicacao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

}
