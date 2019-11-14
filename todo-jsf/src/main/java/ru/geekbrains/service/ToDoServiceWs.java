package ru.geekbrains.service;

import javax.ejb.Local;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@Local
@WebService
interface ToDoServiceWs {

    @WebMethod
    List<ToDoRepr> findAll();

    @WebMethod
    void insert(ToDoRepr toDo);

    @WebMethod
    void delete(Long id);

    @WebMethod
    ToDoRepr findById(Long id);

    @WebMethod
    ToDoRepr findByDescription(String description);

    @WebMethod
    List<ToDoRepr> findByCategory(Integer id);
}