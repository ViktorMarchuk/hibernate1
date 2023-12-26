package com.vm.hibarnate;

import com.vm.hibarnate.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class HibernateRunnerTest {
    @Test
    public void testRunner() {
        var user = User.builder()
                .userName("Tom")
                .firstName("Tomas")
                .lastName("Bond")
                .birthDay(LocalDate.of(2000, 12, 19))
                .age(23)
                .build();
        var sql = """
                 INSERT INTO 
                %s
                (%s)
                values
                %s
                 """;
    }
}
