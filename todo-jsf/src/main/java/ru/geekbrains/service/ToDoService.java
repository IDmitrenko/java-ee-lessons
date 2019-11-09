package ru.geekbrains.service;

import ru.geekbrains.persist.ToDo;
import ru.geekbrains.persist.ToDoRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@WebService(endpointInterface = "ru.geekbrains.service.ToDoServiceWs", serviceName = "ToDoService")
public class ToDoService {

    @EJB
    private ToDoRepository toDoRepository;

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

}
