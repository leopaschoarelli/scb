package br.com.gori.scb.entidade;

import br.com.gori.scb.entidade.util.Modalidade;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Leonardo
 */
@Entity
@SequenceGenerator(name = "TURMA_SEQUENCE", sequenceName = "TURMA_SEQUENCE", allocationSize = 1, initialValue = 0)
@NamedQueries({
    @NamedQuery(name = "Turma.findByDescricao", query = "select t from Turma t where t.descricao = :descricao")
})
public class Turma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TURMA_SEQUENCE")
    private Long id;
    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Modalidade modalidade;
    @Column(nullable = false)
    private String serie;
    @ManyToOne
    @JoinColumn(name = "turno_id", nullable = false)
    private Turno turno;

    public Turma() {

    }

    public Turma(String descricao, Modalidade modalidade, String serie, Turno turno) {
        this.descricao = descricao;
        this.modalidade = modalidade;
        this.serie = serie;
        this.turno = turno;
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

    public Modalidade getModalidade() {
        return modalidade;
    }

    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
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
        if (!(object instanceof Turma)) {
            return false;
        }
        Turma other = (Turma) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descricao + " " + serie + " - " + modalidade.getDescricao();
    }

}
