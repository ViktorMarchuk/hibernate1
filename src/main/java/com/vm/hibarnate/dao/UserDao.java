package com.vm.hibarnate.dao;

import com.vm.hibarnate.entity.User;
import org.hibernate.Session;

import java.sql.Date;
import java.util.List;

public class UserDao {
    private final static UserDao INSTANCE = new UserDao();

    public static UserDao getInstance() {
        return INSTANCE;
    }


    public List<User> findAll(Session session) {
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(User.class);
        var user = criteria.from(User.class);
        criteria.select(user);

        return session.createQuery(criteria).list();
    }

    public List<User> findByFirstName(Session session, String firstName) {
        var criteriaBuild = session.getCriteriaBuilder();
        var criteria = criteriaBuild.createQuery(User.class);
        var user = criteria.from(User.class);
        criteria.select(user)
                .where(criteriaBuild.equal(user.get("personalInfo").get("firstName"), firstName));
        return session.createQuery(criteria).list();
    }

    public List<User> findAllByCompanyName(Session session, String company) {
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(User.class);
        var user = criteria.from(User.class);
        criteria.select(user)
                .where(cb.equal(user.get("company").get("name"), company));

        return session.createQuery(criteria).list();
    }

    public List<User> findUserByBirthday(Session session, Date date) {
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(User.class);
        var user = criteria.from(User.class);
        criteria.select(user)
                .where(cb.greaterThan(user.get("personalInfo").get("birthDay"), date));

        return session.createQuery(criteria).list();
    }
}
