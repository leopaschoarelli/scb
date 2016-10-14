/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gori.scb.connection;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Leonardo
 */
@ApplicationScoped
public class EntityManagerProducer {

    private static EntityManagerProducer instance;
    private EntityManagerFactory factory;
    private EntityManager entityManager;

    public EntityManagerProducer() {
        factory = Persistence.createEntityManagerFactory("scbPU");
    }

    @Produces
    @RequestScoped
    public EntityManager createEntityManager() {
        return factory.createEntityManager();
    }

    public static EntityManagerProducer newInstance() {
        if (instance == null) {
            instance = new EntityManagerProducer();
        }
        return instance;
    }

    public EntityManager getEntityManager() {
        if (entityManager == null || !entityManager.isOpen()) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    public void closeEntityManager(@Disposes EntityManager manager) {
        manager.close();
    }

}
