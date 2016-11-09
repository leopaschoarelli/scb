package br.com.gori.scb.entidade.util;

/**
 *
 * @author Leonardo
 */
public enum Modalidade {
    
    ENS_FUN("Ensino Fundamental"),
    ENS_MED("Ensino Médio"),
    EJA_FUN("EJA Fundamental"),
    EJA_MED("EJA Médio"),
    ENS_ESP("Espanhol - Aprimoramento"),
    ENS_ESPB("Espanhol - Basico"),
    TEC_QUI("Técnico em Química"),
    TEC_ALI("Técnico em Alimentos"),
    TEC_PRO("Técnico Profissionalizante");
    
    private final String descricao;

    private Modalidade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
   
}
