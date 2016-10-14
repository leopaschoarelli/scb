package br.com.gori.scb.entidade;

import br.com.gori.scb.entidade.util.EstadoEmprestimo;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;

/**
 *
 * @author Leonardo
 */
@Entity
@SequenceGenerator(name = "ITEMEMPRESTIMO_SEQUENCE", sequenceName = "ITEMEMPRESTIMO_SEQUENCE", allocationSize = 1, initialValue = 0)
@NamedQueries({
    @NamedQuery(name = "ItemEmprestimo.findByPessoa", query = "select i from ItemEmprestimo i, Emprestimo e, Pessoa p where i.emprestimo = e.id and e.pessoa = p.id and p.nome = :nome"),
    @NamedQuery(name = "ItemEmprestimo.findByFiltro", query = ""
            + "select b from Emprestimo a, ItemEmprestimo b, Pessoa c, Publicacao d, Exemplar e \n"
            + "where a.id = b.emprestimo \n"
            + "and a.pessoa = c.id \n"
            + "and b.exemplar = e.id \n"
            + "and d.id = e.publicacao \n"
            + "and d.titulo like :obra \n"
            + "and c.nome like :nome")
})
public class ItemEmprestimo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEMEMPRESTIMO_SEQUENCE")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "emprestimo_id", nullable = false)
    private Emprestimo emprestimo;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date prazo;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date devolucao;
    @Enumerated(EnumType.STRING)
    private EstadoEmprestimo estadoEmprestimo;
    @ManyToOne
    @JoinColumn(name = "exemplar_id")
    private Exemplar exemplar;

    public ItemEmprestimo() {

    }

    public ItemEmprestimo(Emprestimo emprestimo, Date prazo, Date devolucao, EstadoEmprestimo estadoEmprestimo, Exemplar exemplar) {
        this.emprestimo = emprestimo;
        this.prazo = prazo;
        this.devolucao = devolucao;
        this.estadoEmprestimo = estadoEmprestimo;
        this.exemplar = exemplar;
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

    public Date getPrazo() {
        return prazo;
    }

    public void setPrazo(Date prazo) {
        this.prazo = prazo;
    }

    public Date getDevolucao() {
        return devolucao;
    }

    public void setDevolucao(Date devolucao) {
        this.devolucao = devolucao;
    }

    public EstadoEmprestimo getEstadoEmprestimo() {
        return estadoEmprestimo;
    }

    public void setEstadoEmprestimo(EstadoEmprestimo estadoEmprestimo) {
        this.estadoEmprestimo = estadoEmprestimo;
    }

    public Exemplar getExemplar() {
        return exemplar;
    }

    public void setExemplar(Exemplar exemplar) {
        this.exemplar = exemplar;
    }
    
    public boolean isAtrasado(){
        return this.prazo.after(new Date());
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
        if (!(object instanceof ItemEmprestimo)) {
            return false;
        }
        ItemEmprestimo other = (ItemEmprestimo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.gori.scb.entidade.ItemEmprestimo[ id=" + id + " ]";
    }

}
