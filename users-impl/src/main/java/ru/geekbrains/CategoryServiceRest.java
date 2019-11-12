package ru.geekbrains;

import ru.geekbrains.model.Category;
import ru.geekbrains.model.ToDo;

import javax.ejb.Local;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Local
@Path("category")
public interface CategoryServiceRest {

    @GET
    @Path("all")  //../api/user/all
    @Produces(MediaType.APPLICATION_JSON)
    List<Category> findAllCategory();

    @GET
    @Path("{id}/id")  //возврат конкретной категории по Id
    @Produces(MediaType.APPLICATION_JSON)
    Category findByIdCategory(@PathParam("id") Integer id);
}