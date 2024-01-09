package com.vm.hibarnate.util;

import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class TestDataImporter {
    public static Session importData(SessionFactory sessionFactory){
        @Cleanup var session = sessionFactory.openSession();

        return session;
    }
}
