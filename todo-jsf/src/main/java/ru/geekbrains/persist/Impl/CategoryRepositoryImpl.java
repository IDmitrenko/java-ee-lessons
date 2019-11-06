package ru.geekbrains.persist.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.Category;
import ru.geekbrains.persist.CategoryRepository;
import ru.geekbrains.service.LogPlaces;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Stateless
public class CategoryRepositoryImpl implements CategoryRepository, Serializable {

    public CategoryRepositoryImpl() {
    }

    private static final Logger logger = LoggerFactory.getLogger(CategoryRepositoryImpl.class);

    @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    @PostConstruct
    public void init() {
        logger.info("init started");

        if (this.findAllCategory().isEmpty()) {
            this.insertCategory(new Category(-1, "Fruits"));
            this.insertCategory(new Category(-1, "Vegetables"));
        }
    }

    @Override
    @TransactionAttribute
    @Interceptors({LogPlaces.class})
    public void insertCategory(Category category) {
        em.persist(category);
    }

    @Override
    @TransactionAttribute
    @Interceptors({LogPlaces.class})
    public void updateCategory(Category category) {
        em.merge(category);
    }

    @Override
    @TransactionAttribute
    @Interceptors({LogPlaces.class})
    public void deleteCategory(int id) {
        Category category = em.find(Category.class, id);
        if (category != null) {
            em.remove(category);
        }
    }

    @Override
    @TransactionAttribute
    @Interceptors({LogPlaces.class})
    public Category findCategoryById(int id) {
        return em.find(Category.class, id);
    }

    @Override
    @TransactionAttribute
    @Interceptors({LogPlaces.class})
    public List<Category> findAllCategory() {
        return em.createQuery("from Category ", Category.class).getResultList();
    }

}
