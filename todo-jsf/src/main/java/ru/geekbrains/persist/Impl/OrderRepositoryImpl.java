package ru.geekbrains.persist.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.OrderRepository;
import ru.geekbrains.persist.Orders;
import ru.geekbrains.service.LogPlaces;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Stateful
public class OrderRepositoryImpl implements OrderRepository, Serializable {

    public OrderRepositoryImpl() {
    }

    private static final Logger logger = LoggerFactory.getLogger(OrderRepositoryImpl.class);

    @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    @Override
    @TransactionAttribute
    @Interceptors({LogPlaces.class})
    public void insertOrder(Orders orders) {
        em.persist(orders);
    }

    @Override
    @TransactionAttribute
    @Interceptors({LogPlaces.class})
    public int findLastNumber() {
        List<Orders> list = em.createQuery
                ("from Orders order by numbers desc", Orders.class)
                .getResultList();
        return list.get(1).getNumbers();
    }

    @Override
    @TransactionAttribute
    @Interceptors({LogPlaces.class})
    public Long findLastOrderId() {
        List<Orders> list = em.createQuery
                ("from Orders order by id desc", Orders.class)
                .getResultList();
        return list.get(1).getId();
    }
}