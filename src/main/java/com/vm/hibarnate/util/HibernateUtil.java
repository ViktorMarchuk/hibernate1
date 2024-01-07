package com.vm.hibarnate.util;

import com.vm.hibarnate.converter.BirthdayConverter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addAttributeConverter(new BirthdayConverter(), true);
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        return configuration.buildSessionFactory();
    }
}
