package br.com.gori.scb.entidade;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Leonardo
 */
@Entity
@SequenceGenerator(name = "CATEGORIA_SEQUENCE", sequenceName = "CATEGORIA_SEQUENCE", allocationSize = 1, initialValue = 0)
@NamedQueries({
    @NamedQuery(name = "Categoria.findByDescricao", query = "select c from Categoria c where c.descricao = :descricao")
})
public class Categoria extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CATEGORIA_SEQUENCE")
    private Long id;
    @Column(nullable = false)
    private String descricao;
    @ManyToOne
    @JoinColumn(name = "categsup_id")
    private Categoria superior;
    @OneToMany(mappedBy = "superior", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Categoria> filhos;

    public Categoria() {

    }

    public Categoria(String descricao, Categoria superior) {
        this.descricao = descricao;
        this.superior = superior;
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

    public Categoria getSuperior() {
        return superior;
    }

    public void setSuperior(Categoria superior) {
        this.superior = superior;
    }

    public List<Categoria> getFilhos() {
        return filhos;
    }

    public void setFilhos(List<Categoria> filhos) {
        this.filhos = filhos;
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
        if (!(object instanceof Categoria)) {
            return false;
        }
        Categoria other = (Categoria) object;
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
