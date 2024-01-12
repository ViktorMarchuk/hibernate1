package com.vm.hibarnate.dao;

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
    public void findAllUsersTest() {
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
    public void findByLastNameTest() {
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
}
