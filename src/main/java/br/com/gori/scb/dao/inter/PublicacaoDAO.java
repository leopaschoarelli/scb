package br.com.gori.scb.dao.inter;

import br.com.gori.scb.entidade.Autor;
import br.com.gori.scb.entidade.Categoria;
import br.com.gori.scb.entidade.Cidade;
import br.com.gori.scb.entidade.Editora;
import br.com.gori.scb.entidade.Exemplar;
import br.com.gori.scb.entidade.Publicacao;
import br.com.gori.scb.entidade.TipoAutor;
import java.util.List;

/**
 *
 * @author Leonardo
 */
public interface PublicacaoDAO {

    public Exemplar getMaxNumExe(Long id);
    public List<Editora> getEditoras(String nome);
    public List<Cidade> getCidades(String nome);
    public List<Categoria> getCategorias(String descricao);
    public List<Autor> getAutores(String nome);
    public List<TipoAutor> getTipoAutores(String descricao);
    public List<Publicacao> listarPublicacaoPorTitulo(String titulo);
    public List<Publicacao> listarPublicacaoPorSubtitulo(String subtitulo);
    public Publicacao buscarPublicacaoPorTitulo(String titulo);
}
