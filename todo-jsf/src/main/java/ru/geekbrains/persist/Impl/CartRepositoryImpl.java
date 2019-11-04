package ru.geekbrains.persist.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.CartRepository;
import ru.geekbrains.persist.ContentsOrder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;

@ApplicationScoped
@Named
public class CartRepositoryImpl implements CartRepository, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(CartRepositoryImpl.class);

    @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    @Transactional
    public void insertContentsOrder(ContentsOrder contentsOrder) {
        em.persist(contentsOrder);
    }

}