package ru.geekbrains;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.model.Category;
import ru.geekbrains.model.ToDo;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

@Stateless
public class ToDoServiceImpl implements ToDoService, ToDoServiceRest {

    private static final Logger logger = LoggerFactory.getLogger(ToDoServiceImpl.class);

    private static final Category category = new Category(1, "Fruits");
    private static final List<ToDo> todos = Arrays.asList(
            new ToDo(1L, "Apples", LocalDate.now(), category),
            new ToDo(2L, "Pears", LocalDate.now(), category),
            new ToDo(3L, "Oranges", LocalDate.now(), category),
            new ToDo(4L, "Pineapples", LocalDate.now(), category),
            new ToDo(5L, "Cherry", LocalDate.now(), category)
    );

    @Override
    public void insert(ToDo toDo) {

    }

    public void delete(Long id) {

    }

    public List<ToDo> findByCategory(Integer id) {
        return todos.stream()
                .filter(t -> t.getCategory().getId().equals(id))
                .forEach()
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @Override
    public List<ToDo> findAll() {
        logger.info("findAll() invocation");
        return todos;
    }

    @Override
    public ToDo getToDo(Long id) {
        return todos.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @Override
    @Asynchronous
    public Future<List<ToDo>> findAllAsync() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new AsyncResult<>(todos);
    }
}