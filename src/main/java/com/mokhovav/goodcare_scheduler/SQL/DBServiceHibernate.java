package com.mokhovav.goodcare_scheduler.SQL;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBServiceHibernate implements DBService{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long save(Object object) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Long id = (Long) session.save(object);
        transaction.commit();
        session.close();
        return id;
    }

    @Override
    public Object update(Object object) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Object obj = session.merge(object);
        transaction.commit();
        session.close();
        return obj;
    }

    @Override
    public boolean delete(Object object) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(object);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Object getById(Long id, Class c) {
        Session session = sessionFactory.openSession();
        Object result = session.get(c, id);
        session.close();
        return result;
    }

    @Override
    public Object findObject(String text) {
        Session session = sessionFactory.openSession();
        List<?> list = session.createQuery(text).list();
        session.close();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<?> getAll(Class c) {
        Session session = sessionFactory.openSession();
        List<?> list = session.createQuery("SELECT a from " + c.getSimpleName() + " a").getResultList();
        session.close();
        return list.isEmpty() ? null : list;
    }
}
