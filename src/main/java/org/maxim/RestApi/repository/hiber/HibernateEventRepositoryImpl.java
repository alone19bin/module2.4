package org.maxim.RestApi.repository.hiber;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.maxim.RestApi.model.Event;
import org.maxim.RestApi.repository.EventRepository;
import org.maxim.RestApi.utils.HibernateUtil;

import java.util.List;

public class HibernateEventRepositoryImpl implements EventRepository {
    @Override
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


    @Override
    public Event get(Integer id) {
        Event event;
        try(Session session = HibernateUtil.openSession()) {
            event = session.get(Event.class, id);
        }
        return event;
    }

    @Override
    public Event update(Event object) {
        return null;
    }

    @Override
    public void delete(Integer id) {
        Event event = new Event();
        event.setId(id);
        try(Session session = HibernateUtil.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(event);
            transaction.commit();
        }
    }

    @Override
    public List<Event> getAll() {
        List<Event> eventList;
        try(Session session = HibernateUtil.openSession()) {
            Query<Event> events = session.createQuery("From Event", Event.class);
            eventList = events.list();
        }
        return eventList;
    }
}
