package br.com.gori.scb.dao.inter;

import br.com.gori.scb.entidade.Instituicao;
import java.util.List;

/**
 *
 * @author Leonardo
 */
public interface InstituicaoDAO {

    public List<Instituicao> listarInstituicaoPorNomeFantasia(String nomeFantasia);
    public Instituicao buscarInstituicaoPorNomeFantasia(String nomeFantasia);
}
