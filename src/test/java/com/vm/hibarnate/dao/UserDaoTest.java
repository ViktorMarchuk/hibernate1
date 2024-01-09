package com.vm.hibarnate.dao;

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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
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
    public void getAllusersByCriteriaTest() {
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
        List<String> expected = Arrays.asList("Don", "Kot");
        Assert.assertEquals(fromDB.size(), 2);
        List<String> name = fromDB.stream()
                .map(user -> user.getPersonalInfo().getFirstName())
                .collect(Collectors.toList());
        log.info("Data from DB {}:", name);
        Assert.assertEquals(name, expected);
    }

    @Test
    public void getUserByFirstNameTest() {
//        @Cleanup var session = sessionFactory.openSession();
//        String name = "Kot";
//        List<User> resultByFirstName = userDao.findByFirstName(session, name);
//        String firstName = resultByFirstName.stream()
//                .map(user -> user.getPersonalInfo().getFirstName()).filter(n -> n.equals(name)).toString();
//        Assert.assertEquals(firstName, name);
//        assertThat(firstName)
//                .extracting(user -> user.getPersonalInfo().getFirstName())
//                .containsExactly(name);
//
//        log.info("Data user get by first name: {}", resultByFirstName);
   }
}
