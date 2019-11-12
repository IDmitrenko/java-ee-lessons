package ru.geekbrains;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.model.Category;

import javax.ejb.Stateless;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

@Stateless
public class CategotyServiceImpl implements CategoryService, CategoryServiceRest {

    private static final Logger logger = LoggerFactory.getLogger(CategotyServiceImpl.class);

    private static final List<Category> categories = Arrays.asList(
            new Category(1, "Fruits"),
            new Category(2, "Veggie")
    );

    @Override
    public void insertCategory(Category category) {

    }

    @Override
    public List<Category> findAllCategory() {
        logger.info("findAll() invocation");
        return categories;
    }

    @Override
    public Category findByIdCategory(Integer id) {
        return categories.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }
}