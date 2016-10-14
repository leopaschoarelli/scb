package br.com.gori.scb.dao.inter;

import br.com.gori.scb.entidade.ItemReserva;
import java.util.List;

/**
 *
 * @author Leonardo
 */
public interface ItemReservaDAO {

    public List<ItemReserva> listarItemReservaPorTitulo(String titulo);
    public ItemReserva buscarItemReservaPorTitulo(String titulo);
}
