package ru.geekbrains.persist.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.Category;
import ru.geekbrains.persist.ToDo;
import ru.geekbrains.persist.ToDoRepository;

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
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
@Named
public class ToDoRepositoryImpl implements ToDoRepository, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ToDoRepositoryImpl.class);

    @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    @Resource
    protected UserTransaction userTransaction;

    @PostConstruct
    public void init() {
        logger.info("init started");


        if (this.findAll().isEmpty()) {
            try {
                try {
                    userTransaction.begin();
                } catch (SystemException ex) {
                    logger.error("SystemException begin", ex);
                }
                em.joinTransaction();
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
    public void insert(ToDo toDo) {
        // добавление информации через EntityManager
        em.persist(toDo);
    }

    @Transactional
    public void update(ToDo toDo) {
        em.merge(toDo);
    }

    @Transactional
    public void delete(long id) {
        ToDo toDo = em.find(ToDo.class, id);
        if (toDo != null) {
            em.remove(toDo);
        }
    }

    public ToDo findById(long id) {
        return em.find(ToDo.class, id);
    }

    public List<ToDo> findAll() {
        return em.createQuery("SELECT t from ToDo t ", ToDo.class).getResultList();
    }
}