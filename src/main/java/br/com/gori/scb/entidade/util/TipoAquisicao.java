package br.com.gori.scb.entidade.util;

/**
 *
 * @author Leonardo
 */
public enum TipoAquisicao {

    DOACAO("Doação"),
    COMPRA("Compra");

    private TipoAquisicao(String descricao) {
        this.descricao = descricao;
    }

    private final String descricao;

    public String getDescricao() {
        return descricao;
    }

}
