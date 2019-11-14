package ru.geekbrains;

import ru.geekbrains.model.ToDo;
import ru.geekbrains.model.User;

import javax.ejb.Local;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Local
@Path("todo")
public interface ToDoServiceRest {

    @GET
    @Path("all")  //../api/user/all
    @Produces(MediaType.APPLICATION_JSON)
    List<ToDo> findAll();

    @GET
    @Path("{id}/id")  //возврат конкретного товара по Id
    @Produces(MediaType.APPLICATION_JSON)
    ToDo findById(@PathParam("id") Long id);
}