/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gori.scb.entidade.util;

/**
 *
 * @author Leonardo
 */
public enum EstadoEmprestimo {

    EMPRESTADO("Emprestado"),
    FINALIZADO("Finalizado"),
    ATRASADO("Atrasado");

    private EstadoEmprestimo(String descricao) {
        this.descricao = descricao;
    }

    private final String descricao;

    public String getDescricao() {
        return descricao;
    }

}
