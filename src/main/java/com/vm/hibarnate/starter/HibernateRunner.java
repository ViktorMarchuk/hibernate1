package com.vm.hibarnate.starter;

import com.vm.hibarnate.converter.BirthdayConverter;
import com.vm.hibarnate.entity.*;
import com.vm.hibarnate.util.HibernateUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

@Slf4j
public class HibernateRunner {

    public static void main(String[] args) {
//        var company = Company.builder()
//                .name("Toyota")
//                .build();
//
//        var user = (User.builder()
//                .userName("Test2")
//                .personalInfo(PersonalInfo.builder()
//                        .firstName("Tort")
//                        .lastName("Dog")
//                        .birthDay(new Birthday(LocalDate.of(2000, 12, 23)))
//                        .build())
//                .role(Role.ADMIN)
//                .company(company)
//                .build());
//
//        try (SessionFactory sessionFactory = HibernateUtil.getSessionFactory()) {
//            Session session = sessionFactory.openSession();
//            session.beginTransaction();
////            session.persist(company);
//            session.persist(user);
//            session.getTransaction().commit();
//        }
    }
}
