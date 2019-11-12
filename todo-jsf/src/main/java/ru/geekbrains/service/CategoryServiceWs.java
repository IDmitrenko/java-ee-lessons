package ru.geekbrains.service;

import ru.geekbrains.persist.Category;

import javax.ejb.Local;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@Local
@WebService
interface CategoryServiceWs {

    @WebMethod
    void insertCategory(Category category);

    @WebMethod
    List<Category> findAllCategory();
}