package com.vm.hibarnate.starter;

import com.vm.hibarnate.entity.User;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (var sessionFactory = configuration.buildSessionFactory()) {
            var session = sessionFactory.openSession();
            session.beginTransaction();
            session.persist(User.builder()
                    .userName("Tom")
                    .firstName("Tomas")
                    .lastName("Bond")
                    .birthDay(LocalDate.of(2000, 12, 19))
                    .age(23)
                    .build());
            session.getTransaction().commit();
        }
    }
}
