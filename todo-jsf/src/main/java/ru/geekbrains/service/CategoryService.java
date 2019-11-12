package ru.geekbrains.service;

import ru.geekbrains.persist.Category;
import ru.geekbrains.persist.CategoryRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@WebService(endpointInterface = "ru.geekbrains.service.CategoryServiceWs", serviceName = "CategoryService")
public class CategoryService {

    @EJB
    private CategoryRepository categoryRepository;

    public List<Category> findAllCategory() {
        return categoryRepository.findAllCategory()
                .stream()
                .map(c -> new Category(c.getId(), c.getDescription()))
                .collect(Collectors.toList());
    }

    public void insertCategory(Category category) {
        categoryRepository.insertCategory(category);
    }

}
