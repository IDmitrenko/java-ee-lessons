package ru.geekbrains.persist;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CategoryRepository {

    void insertCategory(Category category);

    void updateCategory(Category category);

    void deleteCategory(int id);

    Category findCategoryById(int id);

    List<Category> findAllCategory();
}
