package ru.geekbrains.ws;

import ru.geekbrains.service.ToDoRepr;

import javax.ejb.Local;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@Local
@WebService
public interface ToDoServiceWs {

    @WebMethod
    List<ToDoRepr> findAll();

    @WebMethod
    void insert(ToDoRepr toDo);
}
