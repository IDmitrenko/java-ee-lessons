package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.Category;
import ru.geekbrains.persist.CategoryRepository;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class CategoryBean implements Serializable {

    public CategoryBean() {
    }

    private static final Logger logger = LoggerFactory.getLogger(CategoryBean.class);

    private boolean showSelect = false;

    public boolean isShowSelect() {
        return showSelect;
    }

    public void setShowSelect(boolean showSelect) {
        this.showSelect = showSelect;
    }

    @EJB
    private CategoryRepository categoryRepository;

    @Inject
    private TodoBean todoBean;

    @Inject
    private HttpServletRequest request;

    private Category category;

    private List<Category> categoryList;

    public void preloadCategoryList(ComponentSystemEvent componentSystemEvent) {
        this.categoryList = categoryRepository.findAllCategory();
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
        return "/category/category.xhtml?faces-redirect=true";
    }

    public String saveCategory() {
        if (category.getId() == 0) {
            categoryRepository.insertCategory(category);
        } else {
            categoryRepository.updateCategory(category);
        }
        return "/category/categoryList.xhtml?faces-redirect=true";
    }

    public String selectCategory(Category category) {
        todoBean.getToDo().setCategory(category);
        this.category = category;
        return "/todo.xhtml?faces-redirect=true";
    }

    public void deleteCategory(Category category) {
        logger.info("Deleting Category.");
        categoryRepository.deleteCategory(category.getId());
    }

    public String editCategory(Category category) {
        this.category = category;
        return "/category/category.xhtml?faces-redirect=true";
    }

    public String showCategory(boolean showSelect) {
        this.showSelect = showSelect;
        return "/category/categoryList.xhtml?faces-redirect=true";
    }
}
