package br.com.gori.scb.entidade.util;

/**
 *
 * @author Leonardo
 */
public enum RoleUser {

    ADMIN("Administrador"),
    ROOT("Diretor"),
    MOD("Professor"),
    USER("Normal");

    private final String descricao;

    private RoleUser(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
