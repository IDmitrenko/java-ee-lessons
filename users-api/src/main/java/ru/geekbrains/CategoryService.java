package ru.geekbrains;

import ru.geekbrains.model.Category;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface CategoryService {

    void insertCategory(Category category);

    List<Category> findAllCategory();

}