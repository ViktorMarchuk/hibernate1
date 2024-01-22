package com.vm.hibarnate.dao;

import com.vm.hibarnate.dto.PaymentFilter;
import com.vm.hibarnate.entity.Payment;
import com.vm.hibarnate.entity.User;
import com.vm.hibarnate.util.HibernateUtil;
import com.vm.hibarnate.util.TestDataImporter;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class UserDaoQueryDSLTest {

    private final UserDaoQueryDSL userDaoQueryDSL = UserDaoQueryDSL.getInstance();
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @BeforeAll
    public static void start() {
        TestDataImporter.importData(sessionFactory);

    }

    @AfterAll
    public static void finish() {
        sessionFactory.close();
    }

    @Test
    void findAllUsersTest() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        List<User> users = userDaoQueryDSL.findAllUsers(session);
        List<String> expected = Arrays.asList("Kot", "Don", "Lena");
        List<String> actual = users.stream().map(user -> user.getPersonalInfo().getFirstName()).collect(Collectors.toList());
        log.info("list of first name:{}", actual);
        log.info("List of users: {}", users);
        Assertions.assertEquals(expected, actual, "Test do not pass");

        session.getTransaction().commit();
    }

    @Test
    void findByLastNameTest() {
        @Cleanup var session = sessionFactory.openSession();
        String excepted = "Grud";
        session.beginTransaction();
        List<User> users = userDaoQueryDSL.findByLastName(session, excepted);
        log.info("The found user: {}", users);
        Optional<String> actual = users.stream().map(user -> user.getPersonalInfo().getLastName()).findFirst();
        Assertions.assertEquals(excepted, actual.get());
        log.info("Result: {}", actual);
        session.getTransaction().commit();
    }

    @Test
    void findLimitUserOrderByBirthdayTest() {
        @Cleanup var session = sessionFactory.openSession();
        int limit = 3;
        List<String> expected = Arrays.asList("Minsk", "Ocean", "Stop");
        session.beginTransaction();
        List<User> users = userDaoQueryDSL.findLimitUserOrderByBirthday(session, limit);
        log.info("List of users:{}", users);
        List<String> actual = users.stream().map(user -> user.getUserName()).sorted().collect(Collectors.toList());
        log.info("List of users:{}", actual);
        Assertions.assertEquals(expected, actual, "Test not pass");

        session.getTransaction().commit();
    }

    @Test
    void findAllBuCompanyNameTest() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        String companyName = "Porshe";
        List<User> users = userDaoQueryDSL.findAllByCompanyName(session, companyName);
        log.info("List of users:{}", users);

        Optional<String> actual = users.stream().map(user -> user.getCompany().getName()).findFirst();
        log.info("Company name : {}", actual.get());

        Assertions.assertEquals(actual.get(), companyName, "Test not pass");

        session.getTransaction().commit();
    }

    @Test
    void findAllPaymentsByCompanyNameTest() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        String companyName = "Audi";
        List<Integer> expected = Arrays.asList(600, 550);
        List<Payment> payments = userDaoQueryDSL.findAllPaymentsByCompanyName(session, companyName);
        log.info("Payments received by company name:{}", payments);
        List<Integer> actual = payments.stream().map(payment -> payment.getAmount()).collect(Collectors.toList());
        log.info("List of amount of payment(s):{}", actual);
        Assertions.assertEquals(expected, actual, "Test not pass with expected amount " + expected);

        session.getTransaction().commit();
    }

    @Test
    void findAverageAmountByCompanyNameTest() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        double expected = 575.0;
        double actual = userDaoQueryDSL
                .findAverageAmountByCompanyName(session, PaymentFilter.builder()
                        .company("Audi")
                        .build());
        log.info("Average amount by company:{}", actual);
        Assertions.assertEquals(expected, actual);
        session.getTransaction().commit();
    }

    @Test
    void findLimitByBirthdayOrderTest() {
        @Cleanup var session = sessionFactory.openSession();
        int limit = 3;
        session.beginTransaction();
        List<User> users = userDaoQueryDSL.findLimitUserOrderByBirthday(session, limit);
        log.info("Find users by birthday:{}", users);
        Assertions.assertEquals(users.size(), limit);
        session.getTransaction().commit();
    }

    @Test
    void findAllByCompanyNameTest() {
        @Cleanup var session = sessionFactory.openSession();
        String companyName = "Audi";
        List<String> expected = Arrays.asList("Stop", "Minsk");
        List<User> users = userDaoQueryDSL.findAllByCompanyName(session, companyName);
        log.info("List of users find by company name: {}", users);
        List<String> actual = users.stream().map(user -> user.getUserName()).collect(Collectors.toList());
        log.info("List of user name:{}", actual);
        Assertions.assertEquals(actual, expected);
        session.beginTransaction();

    }
}
