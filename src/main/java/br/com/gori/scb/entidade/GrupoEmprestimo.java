package br.com.gori.scb.entidade;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Leonardo
 */
@Entity
@SequenceGenerator(name = "GRUPOEMPRESTIMO_SEQUENCE", sequenceName = "GRUPOEMPRESTIMO_SEQUENCE", allocationSize = 1, initialValue = 0)
@NamedQueries({
    @NamedQuery(name = "GrupoEmprestimo.findByNome", query = "select g from GrupoEmprestimo g where g.nome = :nome")
})
public class GrupoEmprestimo extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GRUPOEMPRESTIMO_SEQUENCE")
    private Long id;
    @ManyToMany
    @JoinTable(name = "grupo_pessoa",
            joinColumns = @JoinColumn(name = "tipopessoa_id"),
            inverseJoinColumns = @JoinColumn(name = "grupoemprestimo_id"))
    private List<TipoPessoa> tipoPessoas;
    @ManyToMany
    @JoinTable(name = "grupo_publicacao",
            joinColumns = @JoinColumn(name = "publicacao_id"),
            inverseJoinColumns = @JoinColumn(name = "grupoemprestimo_id"))
    private List<Publicacao> grupoPublicacoes;
    @Column(nullable = false)
    private String nome;

    public GrupoEmprestimo() {
        this.tipoPessoas = new ArrayList<TipoPessoa>();
        this.grupoPublicacoes = new ArrayList<Publicacao>();
    }

    public GrupoEmprestimo(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<TipoPessoa> getTipoPessoas() {
        return tipoPessoas;
    }

    public void setTipoPessoas(List<TipoPessoa> tipoPessoas) {
        this.tipoPessoas = tipoPessoas;
    }

    public List<Publicacao> getGrupoPublicacoes() {
        return grupoPublicacoes;
    }

    public void setGrupoPublicacoes(List<Publicacao> grupoPublicacoes) {
        this.grupoPublicacoes = grupoPublicacoes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
        if (!(object instanceof GrupoEmprestimo)) {
            return false;
        }
        GrupoEmprestimo other = (GrupoEmprestimo) object;
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
