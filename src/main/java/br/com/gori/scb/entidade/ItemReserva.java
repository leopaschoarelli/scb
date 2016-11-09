package br.com.gori.scb.entidade;

import java.util.Date;
import javax.persistence.Entity;
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
@SequenceGenerator(name = "ITEMRESERVA_SEQUENCE", sequenceName = "ITEMRESERVA_SEQUENCE", allocationSize = 1, initialValue = 0)
@NamedQueries({
    @NamedQuery(name = "ItemReserva.findByTitulo", query = "select i from ItemReserva i, Publicacao p where i.publicacao = p.id and p.titulo = :titulo")
})
public class ItemReserva extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEMRESERVA_SEQUENCE")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reserva reserva;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date previsao;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date devolucao;
    private boolean efetivado;
    @ManyToOne
    @JoinColumn(name = "publicacao_id")
    private Publicacao publicacao;

    public ItemReserva() {
    }

    public ItemReserva(Reserva reserva, Date previsao, Date devolucao, boolean efetivado, Publicacao publicacao) {
        this.reserva = reserva;
        this.previsao = previsao;
        this.devolucao = devolucao;
        this.efetivado = efetivado;
        this.publicacao = publicacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Date getPrevisao() {
        return previsao;
    }

    public void setPrevisao(Date previsao) {
        this.previsao = previsao;
    }

    public Boolean getEfetivado() {
        return efetivado;
    }

    public void setEfetivado(boolean efetivado) {
        this.efetivado = efetivado;
    }

    public Publicacao getPublicacao() {
        return publicacao;
    }

    public void setPublicacao(Publicacao publicacao) {
        this.publicacao = publicacao;
    }

    public Date getDevolucao() {
        return devolucao;
    }

    public void setDevolucao(Date devolucao) {
        this.devolucao = devolucao;
    }

    public boolean isEfetivado() {
        return efetivado;
    }

    public String getEfetivo() {
        if (this.efetivado == true) {
            return "Sim";
        } else {
            return "Não";
        }
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
        if (!(object instanceof ItemReserva)) {
            return false;
        }
        ItemReserva other = (ItemReserva) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.gori.scb.entidade.ItemReserva[ id=" + id + " ]";
    }

}
