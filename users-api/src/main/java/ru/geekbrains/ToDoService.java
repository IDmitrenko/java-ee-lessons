package ru.geekbrains;

import ru.geekbrains.model.ToDo;

import javax.ejb.Remote;
import java.util.List;
import java.util.concurrent.Future;

@Remote
public interface ToDoService {

    void insert(ToDo toDo);

    void delete(long id);

    ToDo findById(long id);

    ToDo findByDescription(String description);

    List<ToDo> findAll();

    List<ToDo> findByCategory(Integer id);

    Future<List<ToDo>> findAllAsync();

}