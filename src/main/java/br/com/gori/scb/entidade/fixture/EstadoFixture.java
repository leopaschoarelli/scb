package br.com.gori.scb.entidade.fixture;

import br.com.gori.scb.entidade.Estado;
import br.com.gori.scb.entidade.Pais;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Leonardo
 */
public class EstadoFixture {

    private static final String NOME = "Paraná";
    private static final String UF = "PR";
    private static Pais PAIS;

    public static Estado estadoPadrao() {
        return novoEstado(NOME, UF, PAIS);
    }

    public static Estado estadoByNome(String nome) {
        return novoEstado(nome, UF, PAIS);
    }

    public static Estado estadoByUF(String uf) {
        return novoEstado(NOME, uf, PAIS);
    }

    public static Estado estadoByPais(Pais pais) {
        return novoEstado(NOME, UF, pais);
    }

    public static List<Estado> estados(int size) {
        List<Estado> estados = new ArrayList<Estado>();
        if (size <= 0) {
            return estados;
        }
        for (int x = 1; x < size; x++) {
            String nome = NOME + x;
            String uf = UF + x;
            Pais pais = PAIS;
            estados.add(novoEstado(nome, uf, pais));
        }
        estados.add(estadoPadrao());
        return estados;
    }

    public static Estado novoEstado(String nome, String uf, Pais pais) {
        pais = new Pais("Brasil", "BRA");
        return new Estado(nome, uf, pais);
    }

}
