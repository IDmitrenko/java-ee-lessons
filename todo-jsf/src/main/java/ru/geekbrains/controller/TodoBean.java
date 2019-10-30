package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.ToDo;
import ru.geekbrains.persist.ToDoRepositoryImpl;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class TodoBean implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(TodoBean.class);

    @Inject
    private ToDoRepositoryImpl toDoRepository;

    @Inject
    private CategoryBean categoryBean;

    private ToDo toDo;

    private List<ToDo> toDoList;

    public void preloadTodoList(ComponentSystemEvent componentSystemEvent) {
        this.toDoList = toDoRepository.findAll();
    }

    public ToDo getToDo() {
        return toDo;
    }

    public void setToDo(ToDo toDo) {
        this.toDo = toDo;
    }

    public List<ToDo> getAllTodo() {
        return toDoList;
    }

    public String createTodo() {
        this.toDo = new ToDo();
        return "/todo.xhtml?faces-redirect=true";
    }

    public String saveTodo() {
        if (toDo.getId() == null) {
            categoryBean.setShowSelect(false);
            toDoRepository.insert(toDo);
        } else {
            toDoRepository.update(toDo);
        }
        return "/index.xhtml?faces-redirect=true";
    }

    public void deleteTodo(ToDo toDo) {
        logger.info("Deleting ToDo.");
        toDoRepository.delete(toDo.getId());
    }

    public String editTodo(ToDo toDo) {
        this.toDo = toDo;
        return "/todo.xhtml?faces-redirect=true";
    }
}
