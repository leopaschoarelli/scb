package br.com.gori.scb.entidade.util;

/**
 *
 * @author Leonardo
 */
public enum TipoEndereco {
    
    RESIDENCIAL("Residencial"),
    COMERCIAL("Comercial"),
    VERANEIO("Veraneio"),
    CORRESPONDENCIA("Correspondência");

    private TipoEndereco(String descricao) {
        this.descricao = descricao;
    }
    private final String descricao;

    public String getDescricao() {
        return descricao;
    }

}
