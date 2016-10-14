package br.com.gori.scb.entidade;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Leonardo
 */
@Entity
@SequenceGenerator(name = "AUTORIA_SEQUENCE", sequenceName = "AUTORIA_SEQUENCE", allocationSize = 1, initialValue = 0)
@NamedQueries({
    @NamedQuery(name = "Autoria.findByNome", query = "select a from Autoria a, Autor b where a.autor = b.id and b.nome = :nome")
})
public class Autoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTORIA_SEQUENCE")
    private Long id;
    @ManyToOne
    private Autor autor;
    @ManyToOne
    private TipoAutor tipoAutor;
//    @ManyToMany(mappedBy = "autorias")
//    private List<Publicacao> publicacoes;

    public Autoria() {
//        this.publicacoes = new ArrayList<>();
    }

    public Autoria(Autor autor, TipoAutor tipoAutor) {
        this.autor = autor;
        this.tipoAutor = tipoAutor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public TipoAutor getTipoAutor() {
        return tipoAutor;
    }

    public void setTipoAutor(TipoAutor tipoAutor) {
        this.tipoAutor = tipoAutor;
    }

//    public List<Publicacao> getPublicacoes() {
//        return publicacoes;
//    }
//
//    public void setPublicacoes(List<Publicacao> publicacoes) {
//        this.publicacoes = publicacoes;
//    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.autor);
        hash = 29 * hash + Objects.hashCode(this.tipoAutor);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Autoria other = (Autoria) obj;
        if (!Objects.equals(this.autor, other.autor)) {
            return false;
        }
        if (!Objects.equals(this.tipoAutor, other.tipoAutor)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "br.com.gori.scb.entidade.Autoria[ id=" + id + " ]";
    }

}
