package br.com.gori.scb.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Leonardo
 */
@Entity
@SequenceGenerator(name = "CONFIGURACAOEMPRESTIMO_SEQUENCE", sequenceName = "CONFIGURACAOEMPRESTIMO_SEQUENCE", allocationSize = 1, initialValue = 0)
@Table(name = "configuracaoemprestimo")
@NamedQueries({
    @NamedQuery(name = "ConfiguracaoEmprestimo.findByDias", query = "select c from ConfiguracaoEmprestimo c where c.dias = :dias"),
    @NamedQuery(name = "ConfiguracaoEmprestimo.findByPessoa", query = "select c from ConfiguracaoEmprestimo c, TipoPessoa t where c.tipoPessoa = t.id and t.descricao = :descricao")
})
public class ConfiguracaoEmprestimo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONFIGURACAOEMPRESTIMO_SEQUENCE")
    private Long id;
    @ManyToMany
    @JoinTable(name = "configuracao_penalidade",
            joinColumns = @JoinColumn(name = "configuracaoemprestimo_id"),
            inverseJoinColumns = @JoinColumn(name = "penalidade_id"))
    private List<Penalidade> penalidades;
    @ManyToOne
    @JoinColumn(name = "tipopessoa_id", nullable = false)
    private TipoPessoa tipoPessoa;
    @Column(nullable = false)
    private Integer dias;
    @Column(nullable = false)
    private Integer volumes;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date vigencia;
    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date criacao;

    public ConfiguracaoEmprestimo() {
        this.criacao = new Date();
        this.penalidades = new ArrayList<Penalidade>();
    }

    public ConfiguracaoEmprestimo(TipoPessoa tipoPessoa, Integer dias, Integer volumes, Date vigencia, Date criacao) {
        this.tipoPessoa = tipoPessoa;
        this.dias = dias;
        this.volumes = volumes;
        this.vigencia = vigencia;
        this.criacao = criacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Penalidade> getPenalidades() {
        return penalidades;
    }

    public void setPenalidades(List<Penalidade> penalidades) {
        this.penalidades = penalidades;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public Integer getVolumes() {
        return volumes;
    }

    public void setVolumes(Integer volumes) {
        this.volumes = volumes;
    }

    public Date getVigencia() {
        return vigencia;
    }

    public void setVigencia(Date vigencia) {
        this.vigencia = vigencia;
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
        if (!(object instanceof ConfiguracaoEmprestimo)) {
            return false;
        }
        ConfiguracaoEmprestimo other = (ConfiguracaoEmprestimo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return dias.toString();
    }

}
