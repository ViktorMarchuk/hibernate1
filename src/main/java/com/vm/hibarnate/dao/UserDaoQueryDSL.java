package com.vm.hibarnate.dao;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.vm.hibarnate.dto.PaymentFilter;
import com.vm.hibarnate.entity.Payment;
import com.vm.hibarnate.entity.User;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

import static com.vm.hibarnate.entity.QCompany.company;
import static com.vm.hibarnate.entity.QPayment.payment;
import static com.vm.hibarnate.entity.QUser.user;

public class UserDaoQueryDSL {
    private final static UserDaoQueryDSL INSTANCE = new UserDaoQueryDSL();

    public static UserDaoQueryDSL getInstance() {
        return INSTANCE;
    }

    public List<User> findAllUsers(Session session) {
        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .fetch();
    }

    public List<User> findByLastName(Session session, String name) {
        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .where(user.personalInfo.lastName.eq(name))
                .fetch();
    }

    public List<User> findLimitUserOrderByBirthday(Session session, int limit) {
        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .orderBy(new OrderSpecifier(Order.ASC, user.personalInfo.birthDay))
                .limit(limit)
                .fetch();
    }

    public List<User> findAllByCompanyName(Session session, String name) {
        return new JPAQuery<User>(session)
                .select(user)
                .from(company)
                .join(company.users, user)
                .where(company.name.eq(name))
                .fetch();
    }

    public List<Payment> findAllPaymentsByCompanyName(Session session, String name) {
        return new JPAQuery<Payment>(session)
                .select(payment)
                .from(company)
                .join(company.users, user)
                .join(user.payments, payment)
                .where(company.name.eq(name))
                .fetch();
    }

    public Double findAverageAmountByCompanyName(Session session, PaymentFilter filter) {
        List<Predicate> predicates = new ArrayList<>();
        if(filter.getCompany()!=null)
            predicates.add(user.company.name.eq(filter.getCompany()));

        return new JPAQuery<Double>(session)
                .select(payment.amount.avg())
                .from(payment)
                .join(payment.user)
                .join(user.company)
                .where(predicates.toArray(Predicate[]::new))
                .fetchOne();
    }
}
