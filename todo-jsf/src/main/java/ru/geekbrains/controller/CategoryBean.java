package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.Category;
import ru.geekbrains.persist.ToDoRepository;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@SessionScoped
@Named
public class CategoryBean implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(CategoryBean.class);

    @Inject
    private ToDoRepository toDoRepository;

    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Category> getAllCategory() throws SQLException {
        return toDoRepository.findAllCategory();
    }

    public String createCategory() {
        this.category = new Category();
        return "/category.xhtml?faces-redirect=true";
    }

    public String saveCategory() throws SQLException {
        if (category.getId() == 0) {
            toDoRepository.insertCategory(category);
        } else{
            toDoRepository.updateCategory(category);
        }
        return "/categoryList.xhtml?faces-redirect=true";
    }

    public void deleteCategory(Category category) throws SQLException {
        logger.info("Deleting Category.");
        toDoRepository.deleteCategory(category.getId());
    }

    public String editCategory(Category category) {
        this.category = category;
        return "/category.xhtml?faces-redirect=true";
    }

    public String showCategory() {
        return "/categoryList.xhtml?faces-redirect=true";
    }
}
