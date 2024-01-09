package com.vm.hibarnate.runner;

import com.vm.hibarnate.entity.*;
import com.vm.hibarnate.util.HibernateUtil;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;

@Slf4j
public class HibernateRunnerTest {
    @Test
    public void checkHQL() {
        @Cleanup var sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        String name = "Audi";
        session.beginTransaction();
        var users = session.createNamedQuery("findByCompany")
                .setParameter("name", name)
                .getResultList();
        System.out.println("List of users : " + users);
        System.out.println("Number of Users: " + users.size());
        session.flush();
        session.getTransaction().commit();

    }

    @Test
    public void checkUpdateHQL() {
        @Cleanup var sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        String nameToUpdate = "Peter";
        String oldName="Donald";
        session.beginTransaction();
        var user = session.createNamedQuery("update")
                .setParameter("newFirstName",nameToUpdate)
                .setParameter("oldName",oldName)
                .executeUpdate();

        session.getTransaction().commit();

    }

    @Test
    public void checkInheritance() {
        @Cleanup var sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        Company company = Company.builder()
                .name("Porshe")
                .build();
        session.persist(company);


        session.flush();
        session.clear();

        session.getTransaction().commit();
    }

    @Test
    public void testH2() {
        @Cleanup var sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        Company company = Company.builder()
                .name("Mazda")
                .build();
        session.persist(company);
        session.flush();
        log.info("Company {} saved  with ID{}", company.getName(), company.getId());
        session.getTransaction().commit();
    }

    @Test
    public void manyToMany() {
        @Cleanup var sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        Chat chat = session.get(Chat.class, 2);
        User user = session.get(User.class, 4);

        UserChat userChat = new UserChat();
        userChat.setCreatedAt(Instant.now());
        userChat.setCreatedBy("Viktor");

        userChat.setChat(chat);
        userChat.setUser(user);
        session.persist(userChat);

        // Используйте этот код для инициализации лениво загруженного свойства
        Hibernate.initialize(user.getUserChats());

        session.getTransaction().commit();

    }

    @Test
    public void testOneToOne() {
        var user = (User.builder()
                .userName("Ocean")
                .personalInfo(PersonalInfo.builder()
                        .firstName("LOt")
                        .lastName("Maduk")
                        .birthDay(new Birthday(LocalDate.of(1982, 8, 12)))
                        .build())
                .role(Role.USER)
                .build());
        var profile = Profile.builder()
                .language("BY")
                .street("Korpaty")
                .build();

        @Cleanup var sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        session.persist(user);
        profile.setUser(user);
        session.persist(profile);
        log.info("User {} added DB", user.getUserName());

        session.getTransaction().commit();

    }
//
//    @Test
//    public void checkOrphanRemoval() {
//        var user = (User.builder()
//                .userName("M")
//                .personalInfo(PersonalInfo.builder()
//                        .firstName("Lena")
//                        .lastName("Marchuk")
//                        .birthDay(new Birthday(LocalDate.of(1982, 8, 12)))
//                        .build())
//                .role(Role.USER)
//                .build());
//        var profile = Profile.builder()
//                .language("PL")
//                .street("Skoriny")
//                .build();
//
//        @Cleanup var sessionFactory = HibernateUtil.getSessionFactory();
//        @Cleanup var session = sessionFactory.openSession();
//        session.beginTransaction();
//        session.persist(user);
//        profile.setUser(user);
//        session.persist(profile);
//
//
//        session.getTransaction().commit();
//    }
//
//    @Test
//    public void addNewUserAndCompany() {
//        @Cleanup var sessionFactory = HibernateUtil.getSessionFactory();
//        @Cleanup var session = sessionFactory.openSession();
//        Company company = Company.builder()
//                .name("BMW")
//                .build();
//
//        var user = (User.builder()
//                .userName("LM")
//                .personalInfo(PersonalInfo.builder()
//                        .firstName("Lena")
//                        .lastName("Marchuk")
//                        .birthDay(new Birthday(LocalDate.of(1983, 8, 12)))
//                        .build())
//                .role(Role.USER)
//                .company(company)
//                .build());
//        session.beginTransaction();
//        company.addUser(user);
//        session.persist(company);
//        session.getTransaction().commit();
//    }

    @Test
    public void checkOneToMany() {
        @Cleanup var sessionFactory = HibernateUtil.getSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var company = session.get(Company.class, 1);
        System.out.println(company.getUsers());
        session.getTransaction().commit();


    }
//    @Test
//    public void testRunner() throws SQLException, IllegalAccessException
//        var user = User.builder()
//                .userName("Tommy")
//                .firstName("Tomas")
//                .lastName("Bond")
//                .birthDay(new Birthday(LocalDate.of(2000, 12, 19)))
//                .build();
//        var sql = """
//                 INSERT INTO
//                %s
//                (%s)
//                values
//                (%s)
//                 """;
//        var tableName = Optional.ofNullable(user.getClass().getAnnotation(Table.class))
//                .map(table -> table.schema() + "." + table.name())
//                .orElse(user.getClass().getName());
//
//        Field[] fields = user.getClass().getDeclaredFields();
//        var columnsName = Arrays.stream(fields)
//                .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
//                        .map(Column::name)
//                        .orElse(field.getName()))
//                .collect(Collectors.joining(", "));
//        var columnsValues = Arrays.stream(fields)
//                .map(field -> "?")
//                .collect(Collectors.joining(", "));
//        Connection connection;
//        try {
//            connection = DriverManager.getConnection("jdbc:postgresql://flora.db.elephantsql.com:5432/bpjvmqeo",
//                    "bpjvmqeo", "Z7r9RiFcIXBGOkVkROUXpR8XhluwvjzU");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        PreparedStatement preparedStatement = connection.prepareStatement(sql.formatted(tableName, columnsName, columnsValues));
//
//        for (int i = 0; i < fields.length; i++) {
//            fields[i].setAccessible(true);
//            preparedStatement.setObject(i + 1, fields[i].get(user));
//        }
//        preparedStatement.executeUpdate();
//        System.out.println(preparedStatement);
//
//        preparedStatement.close();
//        connection.close();
//    }
}

