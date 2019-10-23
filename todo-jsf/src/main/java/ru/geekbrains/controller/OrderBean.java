package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.ContentsOrder;
import ru.geekbrains.persist.Order;
import ru.geekbrains.persist.ToDo;
import ru.geekbrains.persist.ToDoRepository;

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
    private ToDoRepository toDoRepository;

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

        for (ToDo toDo : cartBean.getCartList()) {
            toDoRepository.insertContentsOrder(new ContentsOrder(
                    toDoRepository.findLastOrderId(),
                    toDo.getId()));
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
