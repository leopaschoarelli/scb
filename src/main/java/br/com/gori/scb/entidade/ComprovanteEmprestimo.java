package br.com.gori.scb.entidade;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;

/**
 *
 * @author Leonardo
 */
@Entity
@SequenceGenerator(name = "COMPROVANTE_SEQUENCE", sequenceName = "COMPROVANTE_SEQUENCE", allocationSize = 1, initialValue = 0)
public class ComprovanteEmprestimo extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMPROVANTE_SEQUENCE")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "emprestimo_id", nullable = false)
    private Emprestimo emprestimo;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date criacao;

    public ComprovanteEmprestimo() {
        criacao = new Date();
    }

    public ComprovanteEmprestimo(Emprestimo emprestimo, Date criacao) {
        this.emprestimo = emprestimo;
        this.criacao = criacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    public Date getCriacao() {
        return criacao;
    }

    public void setCriacao(Date criacao) {
        this.criacao = criacao;
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
        if (!(object instanceof ComprovanteEmprestimo)) {
            return false;
        }
        ComprovanteEmprestimo other = (ComprovanteEmprestimo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + id;
    }

}
