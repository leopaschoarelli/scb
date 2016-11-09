package br.com.gori.scb.reportcontrole;

import br.com.gori.scb.connection.EntityManagerProducer;
import br.com.gori.scb.controle.util.JsfUtil;
import br.com.gori.scb.dao.impl.EmprestimoDAOImpl;
import br.com.gori.scb.dao.impl.ExemplarDAOImpl;
import br.com.gori.scb.dao.impl.PessoaDAOImpl;
import br.com.gori.scb.dao.impl.PublicacaoDAOImpl;
import br.com.gori.scb.dao.impl.TurmaDAOImpl;
import br.com.gori.scb.dao.impl.TurnoDAOImpl;
import br.com.gori.scb.entidade.Exemplar;
import br.com.gori.scb.entidade.Pessoa;
import br.com.gori.scb.entidade.Publicacao;
import br.com.gori.scb.entidade.Turma;
import br.com.gori.scb.entidade.Turno;
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
public class RelatorioTurma implements Serializable {

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
    private TurnoDAOImpl turnoDAO;
    private TurmaDAOImpl turmaDAO;
    private Exemplar exemplar;
    private Publicacao publicacao;
    private Pessoa pessoa;
    private Pessoa pessoaTurma;
    private Turma turma;
    private Turno turno;
    private ConverterAutoComplete converterObra;
    private ConverterAutoComplete converterPublicacao;
    private ConverterAutoComplete converterPessoa;
    private ConverterAutoComplete converterTurma;
    private ConverterAutoComplete converterTurno;
    private Date dataInicial;
    private Date dataFinal;
    private Date dtPrazoIni;
    private Date dtPrazoFinal;
    private Date dtDevolIni;
    private Date dtDevolFinal;
    private String console;

    public RelatorioTurma() {
        newInstances();
    }

    public void newInstances() {
        this.emprestimoDAO = new EmprestimoDAOImpl();
        this.exemplarDAO = new ExemplarDAOImpl();
        this.publicacaoDAO = new PublicacaoDAOImpl();
        this.pessoaDAO = new PessoaDAOImpl();
        this.turmaDAO = new TurmaDAOImpl();
        this.turnoDAO = new TurnoDAOImpl();
        this.exemplar = new Exemplar();
        this.publicacao = new Publicacao();
        this.pessoa = new Pessoa();
        this.turno = new Turno();
        this.turma = new Turma();
        this.pessoaTurma = new Pessoa();
        this.dataInicial = null;
        this.dataFinal = null;
        this.dtDevolIni = null;
        this.dtDevolFinal = null;
        this.dtPrazoIni = null;
        this.dtPrazoFinal = null;
    }

    public void emitir() {

        Map<String, Object> parametros = new HashMap<>();

        parametros.put("IMAGEM", getCaminho());
        parametros.put("SQL", gerarSql());
        if (!"".equals(console)) {
            if ("is not null".equals(console)) {
                parametros.put("DESCRICAO", "Devoluções");
            } else {
                parametros.put("DESCRICAO", "Empréstimos");
            }
        } else {
            parametros.put("DESCRICAO", "Empréstimos/Devoluções Por Turma");
        }
//        System.out.println(parametros.toString());
        ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/relatturma.jasper",
                this.response, parametros, "Relatório Por Turma.pdf");

        Session session = EntityManagerProducer.getEntityManager().unwrap(Session.class);
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
        if (!"".equals(console)) {
            sb.append(" and b.devolucao ").append(console);
        }
        if (exemplar != null) {
            sb.append(" and e.id = ").append(exemplar.getId());
        }
        if (pessoa != null) {
            sb.append(" and c.id = ").append(pessoa.getId());
        }
        if (publicacao != null) {
            sb.append(" and f.id = ").append(publicacao.getId());
        }
        if (pessoaTurma.getTurma() != null) {
            sb.append(" and d.id = ").append(pessoaTurma.getTurma().getId());
        }
        if (turno != null) {
            sb.append(" and g.id = ").append(turno.getId());
        }
        if (dataInicial != null && dataFinal != null) {
            sb.append(" and a.criacao between '").append(dataInicial).append("' and '").append(dataFinal).append("' ");
        }
        if (dtPrazoIni != null && dtPrazoFinal != null) {
            sb.append(" and b.prazo between '").append(dtPrazoIni).append("' and '").append(dtPrazoFinal).append("' ");
        }
        if (dtDevolIni != null && dtDevolFinal != null) {
            sb.append(" and b.devolucao between '").append(dtDevolIni).append("' and '").append(dtDevolFinal).append("' ");
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
        return pessoaDAO.getAlunos(value.toLowerCase());
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

    public List<Turma> completaTurma(String value) {
        return turmaDAO.getTurmas(value.toLowerCase());
    }

    public TurmaDAOImpl getTurmaDAO() {
        return turmaDAO;
    }

    public void setTurmaDAO(TurmaDAOImpl turmaDAO) {
        this.turmaDAO = turmaDAO;
    }

    public ConverterAutoComplete getConverterTurma() {
        if (converterTurma == null) {
            converterTurma = new ConverterAutoComplete(Turma.class, turmaDAO);
        }
        return converterTurma;
    }

    public void setConverterTurma(ConverterAutoComplete converterTurma) {
        this.converterTurma = converterTurma;
    }

    public ConverterAutoComplete getConverterTurno() {
        if (converterTurno == null) {
            converterTurno = new ConverterAutoComplete(Turno.class, turnoDAO);
        }
        return converterTurno;
    }

    public List<Turno> completaTurno(String value) {
        return turmaDAO.getTurnos(value.toLowerCase());
    }

    public void setConverterTurno(ConverterAutoComplete converterTurno) {
        this.converterTurno = converterTurno;
    }

    public TurnoDAOImpl getTurnoDAO() {
        return turnoDAO;
    }

    public void setTurnoDAO(TurnoDAOImpl turnoDAO) {
        this.turnoDAO = turnoDAO;
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

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public Pessoa getPessoaTurma() {
        return pessoaTurma;
    }

    public void setPessoaTurma(Pessoa pessoaTurma) {
        this.pessoaTurma = pessoaTurma;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public Date getDtPrazoIni() {
        return dtPrazoIni;
    }

    public void setDtPrazoIni(Date dtPrazoIni) {
        this.dtPrazoIni = dtPrazoIni;
    }

    public Date getDtPrazoFinal() {
        return dtPrazoFinal;
    }

    public void setDtPrazoFinal(Date dtPrazoFinal) {
        this.dtPrazoFinal = dtPrazoFinal;
    }

}
