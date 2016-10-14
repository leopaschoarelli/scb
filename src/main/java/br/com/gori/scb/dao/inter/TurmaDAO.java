/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gori.scb.dao.inter;

import br.com.gori.scb.entidade.Turma;
import br.com.gori.scb.entidade.Turno;
import java.util.List;

/**
 *
 * @author Leonardo
 */
public interface TurmaDAO {

    public List<Turno> getTurnos(String descricao);
    public List<Turma> listarTurmaPorDescricao(String descricao);
    public Turma buscarTurmaPorDescricao(String descricao);
}
