package br.com.gori.scb.entidade;

import br.com.gori.scb.entidade.util.Sexo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author Leonardo
 */
@Entity
@SequenceGenerator(name = "PESSOA_SEQUENCE", sequenceName = "PESSOA_SEQUENCE", allocationSize = 1, initialValue = 0)
@NamedQueries({
    @NamedQuery(name = "Pessoa.findByNome", query = "select p from Pessoa p where p.nome = :nome")
})
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PESSOA_SEQUENCE")
    Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date nascimento;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
    private String rg;
    private Boolean ativo;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> endereco;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Telefone> telefone;
    private String email;
    @ManyToOne
    @JoinColumn(name = "tipopessoa_id", nullable = false)
    private TipoPessoa tipoPessoa;
    private String cgm;
    @ManyToOne
    @JoinColumn(name = "turma_id")
    private Turma turma;
    @Transient
    private Set<Emprestimo> emprestimosAbertos;
    @Transient
    private Set<Reserva> reservasAbertas;

    public Pessoa() {
        this.endereco = new ArrayList<>();
        this.telefone = new ArrayList<>();
        this.emprestimosAbertos = new HashSet<>();
        this.reservasAbertas = new HashSet<>();
        this.ativo = true;
    }

    public Pessoa(String nome, Date nascimento, Sexo sexo, String rg, Boolean ativo, String email, TipoPessoa tipoPessoa, String cgm, Turma turma) {
        this.nome = nome;
        this.nascimento = nascimento;
        this.sexo = sexo;
        this.rg = rg;
        this.ativo = ativo;
        this.email = email;
        this.tipoPessoa = tipoPessoa;
        this.cgm = cgm;
        this.turma = turma;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public List<Endereco> getEndereco() {
        return endereco;
    }

    public void setEndereco(List<Endereco> endereco) {
        this.endereco = endereco;
    }

    public List<Telefone> getTelefone() {
        return telefone;
    }

    public void setTelefone(List<Telefone> telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public String getCgm() {
        return cgm;
    }

    public void setCgm(String cgm) {
        this.cgm = cgm;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Set<Emprestimo> getEmprestimosAbertos() {
        return emprestimosAbertos;
    }

    public void setEmprestimosAbertos(Set<Emprestimo> emprestimosAbertos) {
        this.emprestimosAbertos = emprestimosAbertos;
    }

    public Set<Reserva> getReservasAbertas() {
        return reservasAbertas;
    }

    public void setReservasAbertas(Set<Reserva> reservasAbertas) {
        this.reservasAbertas = reservasAbertas;
    }

    public Integer getQtdeItensEmprestimoAbertos() {
        int counter = 0;
        for (Emprestimo emp : this.getEmprestimosAbertos()) {
            counter = counter + emp.getItemEmprestimo().size();
        }
        return counter;
    }

    public Integer getQtdeItensReservasAbertas() {
        int counter = 0;
        for (Reserva res : this.getReservasAbertas()) {
            counter = counter + res.getItemReserva().size();
        }
        return counter;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pessoa)) {
            return false;
        }
        Pessoa other = (Pessoa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nome;
    }

}
