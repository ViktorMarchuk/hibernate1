package com.vm.hibarnate.dao;

import com.vm.hibarnate.entity.Company;
import com.vm.hibarnate.entity.User;
import com.vm.hibarnate.util.HibernateUtil;
import com.vm.hibarnate.util.TestDataImporter;
import jdk.dynalink.linker.LinkerServices;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.SplittableRandom;
import java.util.stream.Collectors;

import static org.junit.Assert.assertThat;

@Slf4j
public class UserDaoTest {
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private final UserDao userDao = UserDao.getInstance();

    @BeforeAll
    public static void start() {
        TestDataImporter.importData(sessionFactory);

    }

    @AfterAll
    public static void finish() {
        sessionFactory.close();
    }

    @Test
    public void showAllUsersTest() {
//        @Cleanup var sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        var user = session.createQuery("""
                        select u from User u 
                        """)
                .getResultList();
        log.info("List of users{}", user);
        session.getTransaction().commit();

    }

    @Test
    public void getAllUsersByCriteriaTest() {
//        @Cleanup var sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
//        var cb = session.getCriteriaBuilder();
//        var criteria = cb.createQuery(User.class);
//        var user = criteria.from(User.class);
//        criteria.select(user);
//
//        List<User> users = session.createQuery(criteria).list();
//        log.info("List of user from test {]", users);
        List<User> fromDB = userDao.findAll(session);
        List<String> expected = Arrays.asList("Kot", "Don", "Lena");
        Assert.assertEquals(fromDB.size(), 3);
        List<String> name = fromDB.stream()
                .map(user -> user.getPersonalInfo().getFirstName())
                .collect(Collectors.toList());
        log.info("Data from DB {}:", name);
        Assert.assertEquals(name, expected);
    }

    @Test
    public void getUserByFirstNameTest() {
        @Cleanup var session = sessionFactory.openSession();
        String name = "Kot";
        List<User> resultByFirstName = userDao.findByFirstName(session, name);
        Optional<String> firstName = resultByFirstName.stream()
                .map(user -> user.getPersonalInfo().getFirstName())
                .filter(u -> u.equals(name))
                .findFirst();
        Assertions.assertEquals(firstName.get(), name);
        System.out.println(firstName);
        log.info("Data user get by first name: {}", resultByFirstName);
        log.warn("Show word {}", firstName);
    }

    @Test
    public void findAllByCompanyNameTest() {
        @Cleanup var session = sessionFactory.openSession();
        String expectedResult = "Audi";
        List<User> users = userDao.findAllByCompanyName(session, expectedResult);
        Optional<String> actualResult = users.stream()
                .map(user -> user.getCompany().getName())
                .filter(t -> t.equals(expectedResult)).findFirst();
        Assertions.assertEquals(actualResult.get(), expectedResult);
        log.info("result: {}", users);
        log.info("Company name: {}", actualResult);
    }

    @Test
    public void findUserByBirthdayTest() {
        @Cleanup var session = sessionFactory.openSession();
        Date date = Date.valueOf("1982-01-01");
        List<User> users = userDao.findUserByBirthday(session, date);
        log.info("List of users: {}", users);

    }
}
