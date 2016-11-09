package br.com.gori.scb.entidade;

import br.com.gori.scb.controle.util.HibernateNextId;
import br.com.gori.scb.entidade.util.EstadoExemplar;
import br.com.gori.scb.entidade.util.TipoAquisicao;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
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
@SequenceGenerator(name = "EXEMPLAR_SEQUENCE", sequenceName = "EXEMPLAR_SEQUENCE", allocationSize = 1, initialValue = 0)
@NamedQueries({
    @NamedQuery(name = "Exemplar.findByTitulo", query = "select e from Exemplar e, Publicacao p where e.publicacao = p.id and p.titulo = :titulo")
})
public class Exemplar extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXEMPLAR_SEQUENCE")
    private Long id;
    private Integer numExe;
    @ManyToOne
    private Publicacao publicacao;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataAquisicao;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataBaixa;
    @Enumerated(EnumType.STRING)
    private TipoAquisicao tipoAquisicao;
    private boolean usoInterno;
    @Column(nullable = true)
    private float preco;
    @Enumerated(EnumType.STRING)
    private EstadoExemplar estadoExemplar;
    private String tombo;
    private String observacao;

    public Exemplar() {

    }

    public Exemplar(boolean geraId) {
        if (geraId) {
            this.id = HibernateNextId.newInstance("EXEMPLAR_SEQUENCE").getValue().longValue();
        }
    }

    public Exemplar(Integer numExe, Publicacao publicacao, Date dataAquisicao, Date dataBaixa, TipoAquisicao tipoAquisicao, boolean usoInterno, float preco, EstadoExemplar estadoExemplar, String tombo, String observacao) {
        this.numExe = numExe;
        this.publicacao = publicacao;
        this.dataAquisicao = dataAquisicao;
        this.dataBaixa = dataBaixa;
        this.tipoAquisicao = tipoAquisicao;
        this.usoInterno = usoInterno;
        this.preco = preco;
        this.estadoExemplar = estadoExemplar;
        this.tombo = tombo;
        this.observacao = observacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumExe() {
        return numExe;
    }

    public void setNumExe(Integer numExe) {
        this.numExe = numExe;
    }

    public Publicacao getPublicacao() {
        return publicacao;
    }

    public void setPublicacao(Publicacao publicacao) {
        this.publicacao = publicacao;
    }

    public Date getDataAquisicao() {
        return dataAquisicao;
    }

    public void setDataAquisicao(Date dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    public Date getDataBaixa() {
        return dataBaixa;
    }

    public void setDataBaixa(Date dataBaixa) {
        this.dataBaixa = dataBaixa;
    }

    public TipoAquisicao getTipoAquisicao() {
        return tipoAquisicao;
    }

    public void setTipoAquisicao(TipoAquisicao tipoAquisicao) {
        this.tipoAquisicao = tipoAquisicao;
    }

    public boolean isUsoInterno() {
        return usoInterno;
    }

    public void setUsoInterno(boolean usoInterno) {
        this.usoInterno = usoInterno;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public EstadoExemplar getEstadoExemplar() {
        return estadoExemplar;
    }

    public void setEstadoExemplar(EstadoExemplar estadoExemplar) {
        this.estadoExemplar = estadoExemplar;
    }

    public String getTombo() {
        return tombo;
    }

    public void setTombo(String tombo) {
        this.tombo = tombo;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
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
        if (!(object instanceof Exemplar)) {
            return false;
        }
        Exemplar other = (Exemplar) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tombo;
    }

}
