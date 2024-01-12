package com.vm.hibarnate.dao;

import com.querydsl.jpa.impl.JPAQuery;
import com.vm.hibarnate.entity.QUser;
import com.vm.hibarnate.entity.User;
import org.hibernate.Session;

import java.util.List;

import static com.vm.hibarnate.entity.QUser.user;

public class UserDaoQueryDSL {
    private final static UserDaoQueryDSL INSTANCE = new UserDaoQueryDSL();

    public static UserDaoQueryDSL getInstance() {
        return INSTANCE;
    }

    public List<User> findAllUsers(Session session) {
        return new JPAQuery<User>(session).select(user).from(user).fetch();
    }

    public List<User> findByLastName(Session session, String name) {
        return new JPAQuery<User>(session).select(user).from(user).where(user.personalInfo.lastName.eq(name)).fetch();
    }
}
