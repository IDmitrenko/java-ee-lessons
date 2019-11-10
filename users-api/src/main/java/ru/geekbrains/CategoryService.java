package ru.geekbrains;

import ru.geekbrains.model.Category;

import javax.ejb.Remote;

@Remote
public interface CategoryService {

    void insertCategory(Category category);

}