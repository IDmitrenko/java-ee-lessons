package ru.geekbrains.persist;

import java.util.List;

public interface CategoryRepository {

    void insertCategory(Category category);

    void updateCategory(Category category);

    void deleteCategory(int id);

    Category findCategoryById(int id);

    List<Category> findAllCategory();
}
