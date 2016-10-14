package br.com.gori.scb.entidade.util;

/**
 *
 * @author Leonardo
 */
public enum FiltroConsulta {
    
    igual("Com todas as palavras"),
    like("Com a palavra"),
    not_like("Sem a palavra");

    private final String descricao;

    private FiltroConsulta(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
