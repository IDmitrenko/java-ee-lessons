package ru.geekbrains.persist.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.Category;
import ru.geekbrains.persist.ToDo;
import ru.geekbrains.persist.ToDoRepository;
import ru.geekbrains.service.LogPlaces;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Stateless
public class ToDoRepositoryImpl implements ToDoRepository, Serializable {

    public ToDoRepositoryImpl() {
    }

    private static final Logger logger = LoggerFactory.getLogger(ToDoRepositoryImpl.class);

    @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    @PostConstruct
    public void init() {
        logger.info("init started");


        if (this.findAll().isEmpty()) {
                Category fruitsCategory = new Category(1, "Fruits");
                this.insert(new ToDo(-1L, fruitsCategory, "Apples", LocalDate.now()));
                this.insert(new ToDo(-1L, fruitsCategory, "Pears", LocalDate.now()));
                this.insert(new ToDo(-1L, fruitsCategory, "Oranges", LocalDate.now()));
                this.insert(new ToDo(-1L, fruitsCategory, "Pineapples", LocalDate.now()));
                this.insert(new ToDo(-1L, fruitsCategory, "Strawberry", LocalDate.now().plusDays(1)));
                this.insert(new ToDo(-1L, fruitsCategory, "Cherry", LocalDate.now().plusDays(1)));
                this.insert(new ToDo(-1L, fruitsCategory, "Lemons", LocalDate.now().plusDays(1)));
                this.insert(new ToDo(-1L, fruitsCategory, "Currant", LocalDate.now().plusDays(1)));
                this.insert(new ToDo(-1L, fruitsCategory, "Viburnum", LocalDate.now().plusDays(1)));
        }
    }

    @Override
    @TransactionAttribute
    @Interceptors({LogPlaces.class})
    public void insert(ToDo toDo) {
        // добавление информации через EntityManager
        em.persist(toDo);
    }

    @Override
    @TransactionAttribute
    @Interceptors({LogPlaces.class})
    public void update(ToDo toDo) {
        em.merge(toDo);
    }

    @Override
    @TransactionAttribute
    @Interceptors({LogPlaces.class})
    public void delete(long id) {
        ToDo toDo = em.find(ToDo.class, id);
        if (toDo != null) {
            em.remove(toDo);
        }
    }

    @Override
    @TransactionAttribute
    @Interceptors({LogPlaces.class})
    public ToDo findById(long id) {
        return em.find(ToDo.class, id);
    }

    @Override
    @TransactionAttribute
    @Interceptors({LogPlaces.class})
    public List<ToDo> findAll() {
        return em.createQuery("from ToDo", ToDo.class).getResultList();
    }
}