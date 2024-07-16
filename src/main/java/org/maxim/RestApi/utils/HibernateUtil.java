package org.maxim.RestApi.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.maxim.RestApi.model.Event;
import org.maxim.RestApi.model.UFile;
import org.maxim.RestApi.model.User;

import java.util.List;

public class HibernateUtil {
    private HibernateUtil() {
    }

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();

            String url = "";
            String user = "";
            String password = "";

            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
            configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:3306/postgres");
            configuration.setProperty("hibernate.connection.username", "postgres");
            configuration.setProperty("hibernate.connection.password", "root");
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.format_sql", "true");
            configuration.setProperty("hibernate.use_sql_comments", "true");


            configuration.addAnnotatedClass(Event.class);
            configuration.addAnnotatedClass(UFile.class);
            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }

    public static Session openSession() {
        return getSessionFactory().openSession();
    }


    public Event create(Event event) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            System.out.println("Saving Event: " + event);
            session.save(event);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
        return event;
    }


    public List<Event> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Event> events = session.createQuery("from Event", Event.class).list();
        session.close();
        return events;
    }


}