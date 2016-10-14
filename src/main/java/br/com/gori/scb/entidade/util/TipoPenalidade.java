package br.com.gori.scb.entidade.util;

/**
 *
 * @author Leonardo
 */
public enum TipoPenalidade {
    
    FINANCEIRO("Financeiro"),
    SUSPENSAO("Suspensão");

    private TipoPenalidade(String descricao) {
        this.descricao = descricao;
    }

    private final String descricao;

    public String getDescricao() {
        return descricao;
    }

}
