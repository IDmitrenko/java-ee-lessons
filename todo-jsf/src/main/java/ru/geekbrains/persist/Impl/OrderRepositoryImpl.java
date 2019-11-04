package ru.geekbrains.persist.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.ContentsOrder;
import ru.geekbrains.persist.Order;
import ru.geekbrains.persist.OrderRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@ApplicationScoped
@Named
public class OrderRepositoryImpl implements OrderRepository, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(OrderRepositoryImpl.class);

    @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    @Transactional
    public void insertOrder(Order order) {
        em.persist(order);
    }

    @Transactional
    public void insertContentsOrder(ContentsOrder contentsOrder) {
        em.persist(contentsOrder);
    }

    public int findLastNumber() {
        List<Order> list = em.createQuery
                ("SELECT o from Orders o order by o.numbers desc", Order.class)
                .getResultList();
        return list.get(1).getNumbers();
    }

    public Long findLastOrderId() {
        List<Order> list = em.createQuery
                ("SELECT o from Orders o order by o.id desc", Order.class)
                .getResultList();
        return list.get(1).getId();
    }
}