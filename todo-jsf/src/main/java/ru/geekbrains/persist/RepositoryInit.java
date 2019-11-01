package ru.geekbrains.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import java.io.Serializable;
import java.time.LocalDate;

@ApplicationScoped
@Named
public class RepositoryInit implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(RepositoryInit.class);

    @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    @Inject
    private ToDoRepositoryImpl toDoRepository;

    @Resource
    protected UserTransaction userTransaction;

    @PostConstruct
    public void init() throws SystemException{
        logger.info("init started");
        if (toDoRepository.findAllCategory().isEmpty()) {
            try {
                userTransaction.begin();
                em.joinTransaction();
                Category fruitsCategory = new Category(-1, "Фрукты");
                toDoRepository.insertCategory(fruitsCategory);
                toDoRepository.insertCategory(new Category(-1, "Овощи"));

                if (toDoRepository.findAll().isEmpty()) {
                    toDoRepository.insert(new ToDo(-1L, fruitsCategory, "Apples", LocalDate.now()));
                    toDoRepository.insert(new ToDo(-1L, fruitsCategory, "Pears", LocalDate.now()));
                    toDoRepository.insert(new ToDo(-1L, fruitsCategory, "Oranges", LocalDate.now()));
                    toDoRepository.insert(new ToDo(-1L, fruitsCategory, "Pineapples", LocalDate.now()));
                    toDoRepository.insert(new ToDo(-1L, fruitsCategory, "Strawberry", LocalDate.now().plusDays(1)));
                    toDoRepository.insert(new ToDo(-1L, fruitsCategory, "Cherry", LocalDate.now().plusDays(1)));
                    toDoRepository.insert(new ToDo(-1L, fruitsCategory, "Lemons", LocalDate.now().plusDays(1)));
                    toDoRepository.insert(new ToDo(-1L, fruitsCategory, "Currant", LocalDate.now().plusDays(1)));
                    toDoRepository.insert(new ToDo(-1L, fruitsCategory, "Viburnum", LocalDate.now().plusDays(1)));
                }
                userTransaction.commit();
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
}