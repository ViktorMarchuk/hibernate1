package com.vm.hibarnate.dao;

import com.vm.hibarnate.entity.Payment;
import org.hibernate.SessionFactory;

public class PaymentRepository extends BaseRepository<Integer, Payment> {


    public PaymentRepository(SessionFactory sessionFactory, Class<Payment> clazz) {
        super(sessionFactory, clazz);
    }
}

