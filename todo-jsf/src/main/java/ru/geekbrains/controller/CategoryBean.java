package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.Category;
import ru.geekbrains.persist.ToDoRepositoryImpl;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.UserTransaction;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class CategoryBean implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(CategoryBean.class);

    private boolean showSelect = false;

    public boolean isShowSelect() {
        return showSelect;
    }

    public void setShowSelect(boolean showSelect) {
        this.showSelect = showSelect;
    }

    @Inject
    private ToDoRepositoryImpl toDoRepository;

    @Inject
    private TodoBean todoBean;

    @Resource
    protected UserTransaction userTransaction;

    private Category category;

    private List<Category> categoryList;

    public void preloadCategoryList(ComponentSystemEvent componentSystemEvent) {
        this.categoryList = toDoRepository.findAllCategory();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Category> getAllCategory() {
        return categoryList;
    }

    public String createCategory() {
        this.category = new Category();
        return "/category.xhtml?faces-redirect=true";
    }

    public String saveCategory() {
        if (category.getId() == 0) {
            toDoRepository.insertCategory(category);
        } else{
            toDoRepository.updateCategory(category);
        }
        return "/categoryList.xhtml?faces-redirect=true";
    }

    public String selectCategory(Category category) {
        todoBean.getToDo().setCategory(category);
        this.category = category;
        return "/todo.xhtml?faces-redirect=true";
    }

    public void deleteCategory(Category category) {
        logger.info("Deleting Category.");
        toDoRepository.deleteCategory(category.getId());
    }

    public String editCategory(Category category) {
        this.category = category;
        return "/category.xhtml?faces-redirect=true";
    }

    public String showCategory(boolean showSelect) {
        this.showSelect = showSelect;
        return "/categoryList.xhtml?faces-redirect=true";
    }
}
