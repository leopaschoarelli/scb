package br.com.gori.scb.dao.inter;

import br.com.gori.scb.entidade.Reserva;
import java.util.List;

/**
 *
 * @author Leonardo
 */
public interface ReservaDAO {

    public List<Reserva> listarReservaPorPessoa(String nome);
    public Reserva buscarReservaPorPessoa(String nome);
}
