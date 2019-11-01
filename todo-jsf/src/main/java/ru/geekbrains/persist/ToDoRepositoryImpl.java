package ru.geekbrains.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@ApplicationScoped
@Named
public class ToDoRepositoryImpl implements ToDoRepository, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ToDoRepositoryImpl.class);

    @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    @Transactional
    public void insert(ToDo toDo) {
        // добавление информации через EntityManager
        em.persist(toDo);
    }

    @Transactional
    public void insertCategory(Category category) {
        em.persist(category);
    }

    @Transactional
    public void insertOrder(Order order) {
        em.persist(order);
    }

    @Transactional
    public void insertContentsOrder(ContentsOrder contentsOrder) {
        em.persist(contentsOrder);
    }

    @Transactional
    public void update(ToDo toDo) {
        em.merge(toDo);
    }

    @Transactional
    public void updateCategory(Category category) {
        em.merge(category);
    }

    @Transactional
    public void delete(long id) {
        ToDo toDo = em.find(ToDo.class, id);
        if (toDo != null) {
            em.remove(toDo);
        }
    }

    @Transactional
    public void deleteCategory(int id) {
        Category category = em.find(Category.class, id);
        if (category != null) {
            em.remove(category);
        }
    }

    public ToDo findById(long id) {
        return em.find(ToDo.class, id);
    }

    public Category findCategoryById(int id) {
        return em.find(Category.class, id);
    }

    public int findLastNumber() {
        List<Order> list = em.createQuery
                ("SELECT o from Order o order by o.numbers desc", Order.class)
                .getResultList();
        return list.get(1).getNumbers();
    }

    public Long findLastOrderId() {
        List<Order> list = em.createQuery
                ("SELECT o from Order o order by o.id desc", Order.class)
                .getResultList();
        return list.get(1).getId();
    }

    public List<ToDo> findAll() {
        return em.createQuery("from ToDo ", ToDo.class).getResultList();
    }

    public List<Category> findAllCategory() {
        return em.createQuery("from Category ", Category.class).getResultList();
    }

}
