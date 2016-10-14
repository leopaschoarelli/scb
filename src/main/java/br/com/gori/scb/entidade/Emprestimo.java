package br.com.gori.scb.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;

/**
 *
 * @author Leonardo
 */
@Entity
@SequenceGenerator(name = "EMPRESTIMO_SEQUENCE", sequenceName = "EMPRESTIMO_SEQUENCE", allocationSize = 1, initialValue = 0)
@NamedQueries({
    @NamedQuery(name = "Emprestimo.findByPessoa", query = "select e from Emprestimo e, Pessoa p where e.pessoa = p.id and p.nome = :nome")
})
public class Emprestimo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMPRESTIMO_SEQUENCE")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date criacao;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "emprestimo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemEmprestimo> itemEmprestimo;

    public Emprestimo() {
        criacao = new Date();
        this.itemEmprestimo = new ArrayList<>();
    }

    public Emprestimo(Pessoa pessoa, Date criacao) {
        this.pessoa = pessoa;
        this.criacao = criacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Date getCriacao() {
        return criacao;
    }

    public void setCriacao(Date criacao) {
        this.criacao = criacao;
    }

    public List<ItemEmprestimo> getItemEmprestimo() {
        return itemEmprestimo;
    }

    public void setItemEmprestimo(List<ItemEmprestimo> itemEmprestimo) {
        this.itemEmprestimo = itemEmprestimo;
    }


    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Emprestimo)) {
            return false;
        }
        Emprestimo other = (Emprestimo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.gori.scb.entidade.Emprestimo[ id=" + id + " ]";
    }

}
