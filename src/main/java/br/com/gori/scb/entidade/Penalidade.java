package br.com.gori.scb.entidade;

import br.com.gori.scb.entidade.util.TipoPenalidade;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@SequenceGenerator(name = "PENALIDADE_SEQUENCE", sequenceName = "PENALIDADE_SEQUENCE", allocationSize = 1, initialValue = 0)
@NamedQueries({
    @NamedQuery(name = "Penalidade.findByDescricao", query = "select p from Penalidade p where p.descricao = :descricao")
})
public class Penalidade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PENALIDADE_SEQUENCE")
    private Long id;
    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false)
    private Double diaria;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoPenalidade tipoPenalidade;
    @ManyToMany(mappedBy = "penalidades")
    private List<ConfiguracaoEmprestimo> configuracoes;

    public Penalidade() {

    }

    public Penalidade(String descricao, Double diaria, TipoPenalidade tipoPenalidade) {
        this.descricao = descricao;
        this.diaria = diaria;
        this.tipoPenalidade = tipoPenalidade;
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

    public Double getDiaria() {
        return diaria;
    }

    public void setDiaria(Double diaria) {
        this.diaria = diaria;
    }

    public TipoPenalidade getTipoPenalidade() {
        return tipoPenalidade;
    }

    public void setTipoPenalidade(TipoPenalidade tipoPenalidade) {
        this.tipoPenalidade = tipoPenalidade;
    }

    public List<ConfiguracaoEmprestimo> getConfiguracoes() {
        return configuracoes;
    }

    public void setConfiguracoes(List<ConfiguracaoEmprestimo> configuracoes) {
        this.configuracoes = configuracoes;
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
        if (!(object instanceof Penalidade)) {
            return false;
        }
        Penalidade other = (Penalidade) object;
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
