package ru.geekbrains.persist.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.CartRepository;
import ru.geekbrains.persist.ContentsOrder;
import ru.geekbrains.service.LogPlaces;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

@Stateful
public class CartRepositoryImpl implements CartRepository, Serializable {

    public CartRepositoryImpl() {
    }

    private static final Logger logger = LoggerFactory.getLogger(CartRepositoryImpl.class);

    @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    @Override
    @TransactionAttribute
    @Interceptors({LogPlaces.class})
    public void insertContentsOrder(ContentsOrder contentsOrder) {
        em.persist(contentsOrder);
    }

}