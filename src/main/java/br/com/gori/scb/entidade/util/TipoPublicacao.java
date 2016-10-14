package br.com.gori.scb.entidade.util;

/**
 *
 * @author Leonardo
 */
public enum TipoPublicacao {

    OBRA("Obra"),
    REVISTA("Revista"),
    JORNAL("Jornal"),
    DIDATICO("Didatico");

    private TipoPublicacao(String descricao) {
        this.descricao = descricao;
    }

    private final String descricao;

    public String getDescricao() {
        return descricao;
    }

}
