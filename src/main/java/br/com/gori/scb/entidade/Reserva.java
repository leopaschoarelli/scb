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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;

/**
 *
 * @author Leonardo
 */
@Entity
@SequenceGenerator(name = "RESERVA_SEQUENCE", sequenceName = "RESERVA_SEQUENCE", allocationSize = 1, initialValue = 0)
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESERVA_SEQUENCE")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date criacao;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemReserva> itemReserva;

    public Reserva() {
        this.criacao = new Date();
        this.itemReserva = new ArrayList<>();
    }

    public Reserva(Pessoa pessoa, Date criacao) {
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

    public List<ItemReserva> getItemReserva() {
        return itemReserva;
    }

    public void setItemReserva(List<ItemReserva> itemReserva) {
        this.itemReserva = itemReserva;
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
        if (!(object instanceof Reserva)) {
            return false;
        }
        Reserva other = (Reserva) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.gori.scb.entidade.Reserva[ id=" + id + " ]";
    }

}
