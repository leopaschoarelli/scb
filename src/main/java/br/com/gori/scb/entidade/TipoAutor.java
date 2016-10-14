package br.com.gori.scb.entidade;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Leonardo
 */
@Entity
@SequenceGenerator(name = "TIPOAUTOR_SEQUENCE", sequenceName = "TIPOAUTOR_SEQUENCE", allocationSize = 1, initialValue = 0)
@NamedQueries({
    @NamedQuery(name = "TipoAutor.findByDescricao", query = "select t from TipoAutor t where t.descricao = :descricao")
})
public class TipoAutor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPOAUTOR_SEQUENCE")
    private Long id;
    @Column(nullable = false)
    private String descricao;

    public TipoAutor() {

    }

    public TipoAutor(String descricao) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoAutor)) {
            return false;
        }
        TipoAutor other = (TipoAutor) object;
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
