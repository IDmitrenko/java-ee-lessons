package ru.geekbrains;

import ru.geekbrains.model.User;

import javax.ejb.Local;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Local
@Path("user")
public interface UserServiceRest {

    @GET
    @Path("all")  //../api/user/all
    @Produces(MediaType.APPLICATION_JSON)
    List<User> findAll();

    @GET
    @Path("{id}/id")  //возврат конкретного пользователя
    @Produces(MediaType.APPLICATION_JSON)
    User getUser(@PathParam("id") Long id);
}