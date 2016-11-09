package br.com.gori.scb.controle.util;

import br.com.gori.scb.connection.EntityManagerProducer;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Leonardo
 */
public final class HibernateNextId implements Serializable {

    private static HibernateNextId instance;
    private static final int MIN_SIZE = 1;
    private static final int MAX_SIZE = 100;
    private static List<BigInteger> ids;
    private final String sequenceName;
    private final EntityManager entityManager;

    private HibernateNextId(String sequenceName) {
        this.ids = new ArrayList<>();
        this.sequenceName = sequenceName;
        this.entityManager = EntityManagerProducer.getEntityManager();
    }

    public static HibernateNextId newInstance(String sequenceName) {
        if (instance == null) {
            instance = new HibernateNextId(sequenceName);
        }
        return instance;
    }

    public BigInteger getValue() {
        if (ids.size() <= MIN_SIZE) {
            reloadIds();
        }
        return ids.remove(0);
    }

    private void reloadIds() {
        System.out.println("RELOAD!");
        Query q = entityManager.createNativeQuery(getQuery());
        ids = q.getResultList();
    }

    private String getQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("select nextval('").append(sequenceName).append("') as ids ");
        sb.append(" from generate_series(1,").append(MAX_SIZE).append(")");
        return sb.toString();
    }
}
