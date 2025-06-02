package com.example.app.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.List;

public class GenericDAO<T, ID extends Serializable> {
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private Class<T> clazz;

    public GenericDAO(Class<T> clazz) {
        this.clazz = clazz;
    }

    public ID inserir(T entity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        ID id = (ID) session.save(entity);
        session.getTransaction().commit();
        session.close();
        return id;
    }

    public void update(T entity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
    }

    public void excluir(T entity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
    }

    public T pesquisaId(ID id) {
        Session session = sessionFactory.openSession();
        T obj = session.get(clazz, id);
        session.close();
        return obj;
    }

    public List<T> retornaTodos() {
        Session session = sessionFactory.openSession();
        List<T> list = session.createQuery("from " + clazz.getName(), clazz).list();
        session.close();
        return list;
    }
}