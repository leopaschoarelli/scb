package br.com.gori.scb.dao;

import br.com.gori.scb.connection.EntityManagerProducer;
import br.com.gori.scb.entidade.AbstractEntity;
import br.com.gori.scb.util.SCBException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Query;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author Leonardo
 * @param <T>
 */
public abstract class AbstractDAO<T extends AbstractEntity> implements Serializable {

    public static final int MAX_RESULTS_QUERY = 10;
    private final Class clazz;

    public AbstractDAO(Class clazz) {
        this.clazz = clazz;
    }

    public void save(T entity) {
        try {
            EntityManagerProducer.beginTransaction();
            EntityManagerProducer.getEntityManager().persist(entity);
            EntityManagerProducer.commitAndCloseTransaction();
        } catch (Exception ex) {
            EntityManagerProducer.rollbackAndCloseTransaction();
            String message = "Error to save " + clazz.getSimpleName() + ":" + ex.getMessage();
            throw new SCBException(message, ex);
        }
    }

    public void update(T entity) {
        try {
            EntityManagerProducer.beginTransaction();
            EntityManagerProducer.getEntityManager().merge(entity);
            EntityManagerProducer.commitAndCloseTransaction();
        } catch (Exception ex) {
            EntityManagerProducer.rollbackAndCloseTransaction();
            String message = "Error to update " + clazz.getSimpleName() + ":" + ex.getMessage();
            throw new SCBException(message, ex);
        }
    }

    public void saveOrUpdate(T entity) {
        if (entity.getId() == null) {
            save(entity);
        } else {
            update(entity);
        }
    }

    public T merge(T entity) {
        try {
            EntityManagerProducer.beginTransaction();
            entity = EntityManagerProducer.getEntityManager().merge(entity);
            EntityManagerProducer.commitAndCloseTransaction();
            return entity;
        } catch (Exception ex) {
            EntityManagerProducer.rollbackAndCloseTransaction();
            String message = "Error to merge " + clazz.getSimpleName() + ":" + ex.getMessage();
            throw new SCBException(message, ex);
        }
    }

    public void delete(T entity) {
        try {
            EntityManagerProducer.beginTransaction();
            EntityManagerProducer.getEntityManager().remove(EntityManagerProducer.getEntityManager().merge(entity));
            EntityManagerProducer.commitAndCloseTransaction();
        } catch (Exception ex) {
            EntityManagerProducer.rollbackAndCloseTransaction();
            String message = "Error to delete " + clazz.getSimpleName() + ":" + ex.getMessage();
            throw new SCBException(message, ex);
        }
    }

    public void deleteById(Long id) {
        delete(findById(id));
    }

    public void deleteAll() {
        try {
            EntityManagerProducer.beginTransaction();
            for (T entity : listAll()) {
                EntityManagerProducer.getEntityManager().remove(entity);
            }
            EntityManagerProducer.commitAndCloseTransaction();
        } catch (Exception ex) {
            EntityManagerProducer.rollbackAndCloseTransaction();
            String message = "Error to delete all " + clazz.getSimpleName() + ":" + ex.getMessage();
            throw new SCBException(message, ex);
        }
    }

    public T recover(Class clazz, Object id) {
        return (T) EntityManagerProducer.getEntityManager().find(clazz, id);
    }

    public T findById(Object id) {
        return (T) EntityManagerProducer.getEntityManager().find(clazz, id);
    }

    public Object find(Class entity, Object id) {
        return EntityManagerProducer.getEntityManager().find(entity, id);
    }

    public List<T> listAll() {
        try {
            String hql = "from " + clazz.getSimpleName() + " obj order by obj.id";
            System.out.println("HQL: " + hql);
            Query q = EntityManagerProducer.getEntityManager().createQuery(hql);
            return q.getResultList();
        } catch (Exception ex) {
            String message = "Error to list " + clazz.getSimpleName() + ": " + ex.getMessage();
            throw new SCBException(message, ex);
        }
    }

    @Transactional
    public List<T> findAll() {
        return EntityManagerProducer.getEntityManager().createQuery("from " + clazz.getSimpleName() + " obj order by obj.id").getResultList();
    }

    public int count() {
        String hql = "select count(obj.id) as amount from " + clazz.getSimpleName() + " obj";
        Query q = EntityManagerProducer.getEntityManager().createQuery(hql);
        Long value = (Long) q.getSingleResult();
        return value.intValue();
    }
}
