package br.com.gori.scb.entidade;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Leonardo
 */
@Entity
@SequenceGenerator(name = "TIPOPESSOA_SEQUENCE", sequenceName = "TIPOPESSOA_SEQUENCE", allocationSize = 1, initialValue = 0)
@NamedQueries({
    @NamedQuery(name = "TipoPessoa.findByDescricao", query = "select t from TipoPessoa t where t.descricao = :descricao")
})
public class TipoPessoa extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPOPESSOA_SEQUENCE")
    private Long id;
    @Column(nullable = false)
    private String descricao;
    @ManyToMany(mappedBy = "tipoPessoas")
    private List<GrupoEmprestimo> grupos;

    public TipoPessoa() {

    }

    public TipoPessoa(String descricao) {
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<GrupoEmprestimo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<GrupoEmprestimo> grupos) {
        this.grupos = grupos;
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
        if (!(object instanceof TipoPessoa)) {
            return false;
        }
        TipoPessoa other = (TipoPessoa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descricao;
    }

}
