package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.*;
import ru.geekbrains.persist.Impl.ToDoRepositoryImpl;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.LinkedList;

@SessionScoped
@Named
public class OrderBean implements Serializable {

    public OrderBean() {
    }

    private static final Logger logger = LoggerFactory.getLogger(OrderBean.class);

    @Inject
    private ToDoRepositoryImpl toDoRepository;

    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Inject
    private CartBean cartBean;

    public String saveOrder() throws SQLException {
        order.setId(-1L);
        order.setDate( LocalDate.now());
        order.setNumbers(toDoRepository.findLastNumber() + 1);
        toDoRepository.insertOrder(order);

        for (ToDo toDo : cartBean.getOrderMap().keySet()) {
            toDoRepository.insertContentsOrder(
                    new ContentsOrder(
                            new ContentsOrderId(
                            toDoRepository.findLastOrderId(),
                            toDo.getId()),
                            cartBean.getOrderMap().get(toDo)));
        }

        cartBean.setCartList(new LinkedList<>());
        cartBean.setOrderMap(new LinkedHashMap<>());

        return "/index.xhtml?faces-redirect=true";
    }

    public String registrationOrder() {
        this.order = new Order();
        return "/registration.xhtml?faces-redirect=true";
    }


}
