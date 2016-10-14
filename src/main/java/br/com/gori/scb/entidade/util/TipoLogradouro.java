package br.com.gori.scb.entidade.util;

/**
 *
 * @author Leonardo
 */
public enum TipoLogradouro {

    RUA("Rua"),
    AVENIDA("Avenida"),
    PRACA("Praça"),
    ESTRADA("Estrada");

    private TipoLogradouro(String descricao) {
        this.descricao = descricao;
    }

    private final String descricao;

    public String getDescricao() {
        return descricao;
    }

}
