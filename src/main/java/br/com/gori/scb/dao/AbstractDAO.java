package br.com.gori.scb.dao;

import br.com.gori.scb.connection.EntityManagerProducer;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 *
 * @author Leonardo
 * @param <T>
 */
public abstract class AbstractDAO<T> implements Serializable {

    public static final int MAX_RESULTS_QUERY = 10;
    private final Class clazz;

    public AbstractDAO(Class clazz) {
        this.clazz = clazz;
    }

    public void save(T entity) {
        beginTransaction();
        getEntityManager().persist(entity);
        commitAndCloseTransaction();
//        commit();
    }
    
    public void rollback(){
        closeTransaction();
    }
    
    public void saveSemCommit(T entity) {
        beginTransaction();
        getEntityManager().flush();
        closeTransaction();
    }

    public void update(T entity) {
        beginTransaction();
        getEntityManager().merge(entity);
        commitAndCloseTransaction();
//        commit();
    }

    public T merge(T entity) {
        beginTransaction();
        entity = getEntityManager().merge(entity);
        commitAndCloseTransaction();
        return entity;
    }

    public void delete(T entity) {
        beginTransaction();
        getEntityManager().remove(getEntityManager().merge(entity));
        commitAndCloseTransaction();
    }

    public T recover(Class clazz, Object id) {
        return (T) getEntityManager().find(clazz, id);
    }

    public T findById(Object id) {
        return (T) getEntityManager().find(clazz, id);
    }

    @Transactional
    public Object find(Class entity, Object id) {
        return getEntityManager().find(entity, id);
    }

    public List<T> listAll() {
        String hql = "from " + clazz.getSimpleName() + " obj order by obj.id";
        Query q = getEntityManager().createQuery(hql);
        return q.getResultList();
    }

    public int count() {
        String hql = "select count(obj.id) as amount from " + clazz.getSimpleName() + " obj";
        Query q = getEntityManager().createQuery(hql);
        Long value = (Long) q.getSingleResult();
        return value.intValue();
    }

    protected void beginTransaction() {
        getEntityManager().getTransaction().begin();
    }

    protected void commitAndCloseTransaction() {
        commit();
        closeTransaction();
    }

    private void commit() {
        getEntityManager().getTransaction().commit();
    }

    private void closeTransaction() {
        getEntityManager().close();
    }

    public EntityManager getEntityManager() {
        return EntityManagerProducer.newInstance().getEntityManager();
    }

    public Class getClazz() {
        return clazz;
    }
}
