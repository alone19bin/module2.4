package org.maxim.RestApi.repository.hiber;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.maxim.RestApi.model.User;
import org.maxim.RestApi.repository.UserRepository;
import org.maxim.RestApi.utils.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class HibernateUserRepositoryImpl implements UserRepository {

    @Override
    public User create(User object) {
        User savedUser;
        try (Session session = HibernateUtil.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(object);
            savedUser = session.get(User.class, object.getId());
            transaction.commit();
        }
        return savedUser;
    }

    @Override
    public User get(Integer id) {
        Optional<User> user;
        try (Session session = HibernateUtil.openSession()) {
            Query<User> query = session.createQuery("FROM User u LEFT JOIN FETCH " +
                    "u.events e WHERE u.id = :userId", User.class);
            query.setParameter("userId", id);
            user = query.uniqueResultOptional();
        }
        return user.orElse(null);
    }


    @Override
    public User update(User object) {
        User merge;
        try (Session session = HibernateUtil.openSession()) {
            Transaction transaction = session.beginTransaction();
            merge = (User) session.merge(object);
            transaction.commit();
        }
        return merge;
    }

    @Override
    public void delete(Integer id) {
        User user = new User();
        user.setId(id);
        try (Session session = HibernateUtil.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
        }
    }


    @Override
    public List<User> getAll() {
        List<User> users;
        try (Session session = HibernateUtil.openSession()) {
            Query<User> from_user = session.createQuery("From User", User.class);
            users = from_user.list();
        }
        return users;
    }
}
