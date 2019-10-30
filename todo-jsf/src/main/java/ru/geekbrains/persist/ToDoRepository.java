package ru.geekbrains.persist;

public interface ToDoRepository {

    void insert(ToDo toDo);

    void insertCategory(Category category);

    void insertOrder(Order order);

    void insertContentsOrder(ContentsOrder contentsOrder);

    void update(ToDo toDo);

    void updateCategory(Category category);

    void delete(long id);

    void deleteCategory(int id);
}
