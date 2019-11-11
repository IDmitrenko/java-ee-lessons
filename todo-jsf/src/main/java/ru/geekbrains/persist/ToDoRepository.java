package ru.geekbrains.persist;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ToDoRepository {

    void insert(ToDo toDo);

    void update(ToDo toDo);

    void delete(long id);

    ToDo findById(long id);

    ToDo findByDescription(String description);

    List<ToDo> findAll();

    List<ToDo> findByCategory(Category category);
}
