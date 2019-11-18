package ru.geekbrains.service;

import ru.geekbrains.persist.Category;
import ru.geekbrains.persist.CategoryRepository;
import ru.geekbrains.persist.ToDo;
import ru.geekbrains.persist.ToDoRepository;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@PermitAll
@WebService(endpointInterface = "ru.geekbrains.service.ToDoServiceWs", serviceName = "ToDoService")
public class ToDoService {

    @EJB
    private ToDoRepository toDoRepository;

    @EJB
    private CategoryRepository categoryRepository;

    public List<ToDoRepr> findAll() {
        return toDoRepository.findAll()
                .stream()
                .map(t -> new ToDoRepr(t.getId(), t.getDescription(), t.getPrice(),
                        t.getCount(), t.getActive(),
                        Date.from(t.getTargetDate().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        t.getCategory()))
                .collect(Collectors.toList());
    }

    public void insert(ToDoRepr toDo) {
        ToDo toDo1 = new ToDo();
        toDo1.setDescription(toDo.getDescription());
        toDo1.setPrice(toDo.getPrice());
        toDo1.setCount(toDo.getCount());
        toDo1.setActive(toDo.getActive());
        toDo1.setTargetDate(toDo.getTargetDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        toDo1.setCategory(toDo.getCategory());
        toDoRepository.insert(toDo1);
    }

    public void delete(Long id) {
        toDoRepository.delete(id);
    }

    public ToDoRepr findById(Long id) {
        ToDo toDo =toDoRepository.findById(id);
        ToDoRepr toDoRepr = new ToDoRepr();
        toDoRepr.setDescription(toDo.getDescription());
        toDoRepr.setPrice(toDo.getPrice());
        toDoRepr.setCount(toDo.getCount());
        toDoRepr.setActive(toDo.getActive());
        toDoRepr.setTargetDate(Date.from(
                toDo.getTargetDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        toDoRepr.setCategory(toDo.getCategory());
        return toDoRepr;
    }

    public ToDoRepr findByDescription(String description) {
        ToDo toDo = toDoRepository.findByDescription(description);
        ToDoRepr toDoRepr = new ToDoRepr();
        toDoRepr.setDescription(toDo.getDescription());
        toDoRepr.setPrice(toDo.getPrice());
        toDoRepr.setCount(toDo.getCount());
        toDoRepr.setActive(toDo.getActive());
        toDoRepr.setTargetDate(Date.from(
                toDo.getTargetDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        toDoRepr.setCategory(toDo.getCategory());
        return toDoRepr;
    }

    public List<ToDoRepr> findByCategory(Integer id) {
        Category category = categoryRepository.findCategoryById(id);
        List<ToDo> toDoList = toDoRepository.findByCategory(category);
        List<ToDoRepr> toDoReprList = new ArrayList<>(toDoList.size());
        for (ToDo toDo : toDoList) {
            ToDoRepr toDoRepr = new ToDoRepr();
            toDoRepr.setDescription(toDo.getDescription());
            toDoRepr.setPrice(toDo.getPrice());
            toDoRepr.setCount(toDo.getCount());
            toDoRepr.setActive(toDo.getActive());
            toDoRepr.setTargetDate(Date.from(
                    toDo.getTargetDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            toDoRepr.setCategory(toDo.getCategory());
            toDoReprList.add(toDoRepr);
        }
        return toDoReprList;
    }
}
