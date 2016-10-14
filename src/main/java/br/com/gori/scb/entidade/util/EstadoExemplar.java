package br.com.gori.scb.entidade.util;

/**
 *
 * @author Leonardo
 */
public enum EstadoExemplar {
    
    DISPONIVEL("Disponível"),
    EMPRESTADO("Emprestado"),
    RESERVADO("Reservado"),
    BAIXADO("Baixado");

    private EstadoExemplar(String descricao) {
        this.descricao = descricao;
    }

    private final String descricao;

    public String getDescricao() {
        return descricao;
    }
    
}
