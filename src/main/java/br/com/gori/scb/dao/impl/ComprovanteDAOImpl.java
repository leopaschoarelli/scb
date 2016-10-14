package br.com.gori.scb.dao.impl;

import br.com.gori.scb.dao.AbstractDAO;
import br.com.gori.scb.dao.inter.ComprovanteDAO;
import br.com.gori.scb.entidade.ComprovanteEmprestimo;

/**
 *
 * @author Leonardo
 */
public class ComprovanteDAOImpl extends AbstractDAO<ComprovanteEmprestimo> implements ComprovanteDAO {

    public ComprovanteDAOImpl() {
        super(ComprovanteEmprestimo.class);

    }
}
