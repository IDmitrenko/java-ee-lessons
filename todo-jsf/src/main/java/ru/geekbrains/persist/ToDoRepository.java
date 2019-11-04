package ru.geekbrains.persist;

import java.util.List;

public interface ToDoRepository {

    void insert(ToDo toDo);

    void update(ToDo toDo);

    void delete(long id);

    ToDo findById(long id);

    List<ToDo> findAll();
}
