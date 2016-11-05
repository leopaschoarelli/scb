package br.com.gori.scb.entidade;

import br.com.gori.scb.controle.util.HibernateNextId;
import br.com.gori.scb.entidade.util.FiltroConsulta;
import br.com.gori.scb.entidade.util.TipoPublicacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

/**
 *
 * @author Leonardo
 */
@Entity
@SequenceGenerator(name = "PUBLICACAO_SEQUENCE", sequenceName = "PUBLICACAO_SEQUENCE", allocationSize = 1, initialValue = 0)
@NamedQueries({
    @NamedQuery(name = "Publicacao.findByTitulo", query = "select p from Publicacao p where p.titulo = :titulo")
})
public class Publicacao implements Serializable {

//    private static final long serialVersionUID = 1L;
    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PUBLICACAO_SEQUENCE")
    private Long id;
    private String titulo;
    private String subtitulo;
    private Integer anoPublicacao;
    @ManyToOne
    @JoinColumn(name = "CIDADE_ID")
    private Cidade cidade;
    private String cutter;
    private String cdd;
    private String edicao;
    @Enumerated(EnumType.STRING)
    private TipoPublicacao tipoPublicacao;
    @ManyToOne
    @JoinColumn(name = "editora_id")
    private Editora editora;
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "publicacao_autoria",
            joinColumns = @JoinColumn(name = "publicacao_id"),
            inverseJoinColumns = @JoinColumn(name = "autoria_id"))
    private List<Autoria> autorias;
    private String isbn;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "publicacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exemplar> exemplar;
    @ManyToMany(mappedBy = "grupoPublicacoes")
    private List<GrupoEmprestimo> grupos;
    @Transient
    private FiltroConsulta filtroConsulta;

    public Publicacao() {
//        this.id = HibernateNextId.newInstance("PUBLICACAO_SEQUENCE").getValue().longValue();
    }

    public Publicacao(boolean geraId) {
        if (geraId) {
            this.id = HibernateNextId.newInstance("PUBLICACAO_SEQUENCE").getValue().longValue();
        }
        this.exemplar = new ArrayList<Exemplar>();
        this.autorias = new ArrayList<Autoria>();
    }

    public Publicacao(String titulo, String subtitulo, Integer anoPublicacao, Cidade cidade, String cutter, String cdd, String edicao, TipoPublicacao tipoPublicacao, Editora editora, Categoria categoria, String isbn, List<Exemplar> exemplar) {
        this.id = HibernateNextId.newInstance("PUBLICACAO_SEQUENCE").getValue().longValue();
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.anoPublicacao = anoPublicacao;
        this.cidade = cidade;
        this.cutter = cutter;
        this.cdd = cdd;
        this.edicao = edicao;
        this.tipoPublicacao = tipoPublicacao;
        this.editora = editora;
        this.categoria = categoria;
        this.isbn = isbn;
        this.exemplar = exemplar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getCutter() {
        return cutter;
    }

    public void setCutter(String cutter) {
        this.cutter = cutter;
    }

    public String getCdd() {
        return cdd;
    }

    public void setCdd(String cdd) {
        this.cdd = cdd;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public TipoPublicacao getTipoPublicacao() {
        return tipoPublicacao;
    }

    public List<GrupoEmprestimo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<GrupoEmprestimo> grupos) {
        this.grupos = grupos;
    }

    public void setTipoPublicacao(TipoPublicacao tipoPublicacao) {
        this.tipoPublicacao = tipoPublicacao;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Autoria> getAutorias() {
        return autorias;
    }

    public void setAutorias(List<Autoria> autorias) {
        this.autorias = autorias;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public List<Exemplar> getExemplar() {
        return exemplar;
    }

    public void setExemplar(List<Exemplar> exemplar) {
        this.exemplar = exemplar;
    }

    public FiltroConsulta getFiltroConsulta() {
        return filtroConsulta;
    }

    public void setFiltroConsulta(FiltroConsulta filtroConsulta) {
        this.filtroConsulta = filtroConsulta;
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
        if (!(object instanceof Publicacao)) {
            return false;
        }
        Publicacao other = (Publicacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    public String pesquisa(){
        return titulo+" "+subtitulo+" - "+edicao+" - "+categoria.getDescricao()+" - "+isbn;
    }

    @Override
    public String toString() {
        return titulo;
    }

}
