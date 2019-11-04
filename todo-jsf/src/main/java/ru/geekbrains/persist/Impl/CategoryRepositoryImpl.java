package ru.geekbrains.persist.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.Category;
import ru.geekbrains.persist.CategoryRepository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.io.Serializable;
import java.util.List;

@ApplicationScoped
@Named
public class CategoryRepositoryImpl implements CategoryRepository, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(CategoryRepositoryImpl.class);

    @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    @Resource
    protected UserTransaction userTransaction;

    @PostConstruct
    public void init() {
        logger.info("init started");


        if (this.findAllCategory().isEmpty()) {
            try {
                try {
                    userTransaction.begin();
                } catch (SystemException ex) {
                    logger.error("SystemException begin", ex);
                }
                em.joinTransaction();
                this.insertCategory(new Category(-1, "Fruits"));
                this.insertCategory(new Category(-1, "Vegetables"));

                try {
                    userTransaction.commit();
                } catch (SystemException ex) {
                    logger.error("SystemException commit", ex);
                }

            } catch (NotSupportedException ex) {
                logger.error("Transaction not supported", ex);
                try {
                    userTransaction.rollback();
                } catch (SystemException e) {
                    logger.error("Transaction error", e);
                }
            } catch (HeuristicMixedException ex) {
                logger.error("HeuristicMixedException", ex);
            } catch (HeuristicRollbackException ex) {
                logger.error("HeuristicRollbackException", ex);
            } catch (RollbackException ex) {
                logger.error("RollbackException", ex);
            }
        }
    }

    @Transactional
    public void insertCategory(Category category) {
        em.persist(category);
    }

    @Transactional
    public void updateCategory(Category category) {
        em.merge(category);
    }

    @Transactional
    public void deleteCategory(int id) {
        Category category = em.find(Category.class, id);
        if (category != null) {
            em.remove(category);
        }
    }

    public Category findCategoryById(int id) {
        return em.find(Category.class, id);
    }

    public List<Category> findAllCategory() {
        return em.createQuery("from Category ", Category.class).getResultList();
    }

}
