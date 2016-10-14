package br.com.gori.scb.controle;

import br.com.gori.scb.controle.util.JsfUtil;
import br.com.gori.scb.dao.impl.CidadeDAOImpl;
import br.com.gori.scb.dao.impl.PessoaDAOImpl;
import br.com.gori.scb.dao.impl.TipoPessoaDAOImpl;
import br.com.gori.scb.dao.impl.TurmaDAOImpl;
import br.com.gori.scb.entidade.Cidade;
import br.com.gori.scb.entidade.Endereco;
import br.com.gori.scb.entidade.Pessoa;
import br.com.gori.scb.entidade.Telefone;
import br.com.gori.scb.entidade.TipoPessoa;
import br.com.gori.scb.entidade.Turma;
import br.com.gori.scb.entidade.util.Sexo;
import br.com.gori.scb.entidade.util.TipoEndereco;
import br.com.gori.scb.entidade.util.TipoLogradouro;
import br.com.gori.scb.entidade.util.TipoTelefone;
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
public class PessoaControlador implements Serializable {

    private boolean visualiza;
    private boolean ativo;
    private boolean edicao;
    private boolean isAluno;
    private String header;
    private PessoaDAOImpl pessoaDAO;
    private Pessoa pessoa;
    private Pessoa pessoaSelecionada;
    private TurmaDAOImpl turmaDAO;
    private TipoPessoaDAOImpl tipoPessoaDAO;
    private Endereco endereco;
    private Telefone telefone;
    private ConverterAutoComplete converterTurma;
    private ConverterAutoComplete converterTipoPessoa;
    private ConverterAutoComplete converterCidade;
    private CidadeDAOImpl cidadeDAO;

    public PessoaControlador() {
        newInstances();
    }

    private void newInstances() {
        this.pessoa = new Pessoa();
        this.turmaDAO = new TurmaDAOImpl();
        this.pessoaDAO = new PessoaDAOImpl();
        this.tipoPessoaDAO = new TipoPessoaDAOImpl();
        this.cidadeDAO = new CidadeDAOImpl();
        this.endereco = new Endereco();
        this.telefone = new Telefone();
    }

    public String salvar() {
        try {
            if (validaEndereco()) {
                if (edicao) {
                    pessoaDAO.update(pessoa);
                } else {
                    pessoaDAO.save(pessoa);
                }
                JsfUtil.addSuccessMessage("Pessoa salva com sucesso!");
                return prepararLista();
            } else {
                return null;
            }
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Erro ao salvar pessoa: " + ex.getMessage());
            return null;
        }
    }

    public String excluir() {
        try {
            pessoaDAO.delete(pessoaSelecionada);
            pessoaSelecionada = null;
            JsfUtil.addSuccessMessage("Pessoa removida com sucesso!");
            return prepararLista();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao excluir pessoa");
            return null;
        }
    }

    public String prepararLista() {
        newInstances();
        if (pessoaSelecionada != null) pessoaSelecionada = null;
        return "lista";
    }

    public String prepararEdicao() {
        newInstances();
        pessoa = pessoaSelecionada;
        ativo = true;
        visualiza = false;
        edicao = true;
        isAluno = !(pessoa.getCgm() == null && pessoa.getTurma() == null);
        if (isAluno) {
            header = "Editar Aluno";
        } else {
            header = "Editar Pessoa";
        }

        return "edita";
    }

    public String prepararNovaPessoa() {
        newInstances();
        ativo = false;
        visualiza = false;
        edicao = false;
        isAluno = false;
        header = "Cadastrar Pessoa";
        return "edita";
    }

    public String prepararVer() {
        newInstances();
        pessoa = pessoaSelecionada;
        ativo = true;
        edicao = false;
        isAluno = false;
        header = "Visualizar Pessoa";
        visualiza = true;
        return "edita";
    }

    public String prepararNovoAluno() {
        newInstances();
        ativo = false;
        visualiza = false;
        edicao = false;
        isAluno = true;
        header = "Cadastrar Aluno";
        return "edita";
    }

    public List<SelectItem> getSexo() {
        List<SelectItem> toReturn = new ArrayList<SelectItem>();
        for (Sexo sx : Sexo.values()) {
            toReturn.add(new SelectItem(sx, sx.getDescricao()));
        }
        return toReturn;
    }

    public List<SelectItem> getListaTipoEndereco() {
        List<SelectItem> toReturn = new ArrayList<SelectItem>();
        for (TipoEndereco tp : TipoEndereco.values()) {
            toReturn.add(new SelectItem(tp, tp.getDescricao()));
        }
        return toReturn;
    }

    public List<SelectItem> getListaTipoLogradouro() {
        List<SelectItem> toReturn = new ArrayList<SelectItem>();
        for (TipoLogradouro tp : TipoLogradouro.values()) {
            toReturn.add(new SelectItem(tp, tp.getDescricao()));
        }
        return toReturn;
    }

    public List<SelectItem> getTipoTelefone() {
        List<SelectItem> toReturn = new ArrayList<SelectItem>();
        for (TipoTelefone tp : TipoTelefone.values()) {
            toReturn.add(new SelectItem(tp, tp.getDescricao()));
        }
        return toReturn;
    }

    public List<Pessoa> getPessoas() {
        return pessoaDAO.listAll();
    }

    public boolean validaEndereco() {
        try {
            if (pessoa.getEndereco().isEmpty()) {
                JsfUtil.addErrorMessage("É necessario informar um endereço para a pessoa!");
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Erro ao validar endereço");
            return false;
        }
    }

    public void adicionaEndereco() {
        endereco.setPessoa(pessoa);
        pessoa.getEndereco().add(endereco);
        endereco = new Endereco();
    }

    public void adicionaTelefone() {
        telefone.setPessoa(pessoa);
        pessoa.getTelefone().add(telefone);
        telefone = new Telefone();
    }

    public void excluirEndereco(Endereco endereco) {
        pessoa.getEndereco().remove(endereco);
    }

    public void excluirTelefone(Telefone telefone) {
        pessoa.getTelefone().remove(telefone);
    }

    public void setVisualiza(boolean visualiza) {
        this.visualiza = visualiza;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public PessoaDAOImpl getPessoaDAO() {
        return pessoaDAO;
    }

    public void setPessoaDAO(PessoaDAOImpl pessoaDAO) {
        this.pessoaDAO = pessoaDAO;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public TipoPessoaDAOImpl getTipoPessoaDAO() {
        return tipoPessoaDAO;
    }

    public void setTipoPessoaDAO(TipoPessoaDAOImpl tipoPessoaDAO) {
        this.tipoPessoaDAO = tipoPessoaDAO;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public ConverterAutoComplete getConverterTipoPessoa() {
        if (converterTipoPessoa == null) {
            converterTipoPessoa = new ConverterAutoComplete(TipoPessoa.class, tipoPessoaDAO);
        }
        return converterTipoPessoa;
    }

    public void setConverterTipoPessoa(ConverterAutoComplete converterTipoPessoa) {
        this.converterTipoPessoa = converterTipoPessoa;
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

    public List<TipoPessoa> completaTipoPessoa(String value) {
        return pessoaDAO.getTipoPessoas(value.toLowerCase());
    }

    public List<Cidade> completaCidade(String value) {
        return pessoaDAO.getCidades(value.toLowerCase());
    }

    public List<Turma> completaTurma(String value) {
        return pessoaDAO.getTurmas(value.toLowerCase());
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

    public CidadeDAOImpl getCidadeDAO() {
        return cidadeDAO;
    }

    public void setCidadeDAO(CidadeDAOImpl cidadeDAO) {
        this.cidadeDAO = cidadeDAO;
    }

    public void setEdicao(boolean edicao) {
        this.edicao = edicao;
    }

    public void setIsAluno(boolean isAluno) {
        this.isAluno = isAluno;
    }

    public String adicionar() {
        endereco = new Endereco();
        return null;
    }

    public boolean isVisualiza() {
        return visualiza;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public boolean isEdicao() {
        return edicao;
    }

    public boolean isIsAluno() {
        return isAluno;
    }

    public Pessoa getPessoaSelecionada() {
        return pessoaSelecionada;
    }

    public void setPessoaSelecionada(Pessoa pessoaSelecionada) {
        this.pessoaSelecionada = pessoaSelecionada;
    }

}
