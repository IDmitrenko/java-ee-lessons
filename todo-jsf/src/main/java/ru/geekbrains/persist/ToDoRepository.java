package ru.geekbrains.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
@Named
public class ToDoRepository {

    private static final Logger logger = LoggerFactory.getLogger(ToDoRepository.class);

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    @PostConstruct
    public void init() {

        if (this.findAllCategory().isEmpty()) {
            this.insertCategory(new Category(-1, "Фрукты"));
            this.insertCategory(new Category(-1, "Овощи"));

            if (this.findAll().isEmpty()) {
                this.insert(new ToDo(-1L, 1, "Apples", LocalDate.now()));
                this.insert(new ToDo(-1L, 1, "Pears", LocalDate.now()));
                this.insert(new ToDo(-1L, 1, "Oranges", LocalDate.now()));
                this.insert(new ToDo(-1L, 1, "Pineapples", LocalDate.now()));
                this.insert(new ToDo(-1L, 1, "Strawberry", LocalDate.now().plusDays(1)));
                this.insert(new ToDo(-1L, 1, "Cherry", LocalDate.now().plusDays(1)));
                this.insert(new ToDo(-1L, 1, "Lemons", LocalDate.now().plusDays(1)));
                this.insert(new ToDo(-1L, 1, "Currant", LocalDate.now().plusDays(1)));
                this.insert(new ToDo(-1L, 1, "Viburnum", LocalDate.now().plusDays(1)));
            }
        }
    }

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

    public void insertContentsOrder(ContentsOrderId contentsOrderId) {
        em.persist(contentsOrderId);
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
                ("SELECT o from Order o order by o.numbers desc",Order.class)
                .getResultList();
        return list.get(1).getNumbers();
    }

    public Long findLastOrderId() {
        List<Order> list = em.createQuery
                ("SELECT o from Order o order by o.id desc",Order.class)
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
