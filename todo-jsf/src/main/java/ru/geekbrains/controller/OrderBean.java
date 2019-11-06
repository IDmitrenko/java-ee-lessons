package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.*;
import ru.geekbrains.persist.Impl.CartRepositoryImpl;
import ru.geekbrains.persist.Impl.OrderRepositoryImpl;

import javax.ejb.EJB;
import javax.ejb.Stateful;
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

    @EJB
    private OrderRepository orderRepository;

    @EJB
    private CartRepository cartRepository;

    @Inject
    private CartBean cartBean;

    private Orders orders;

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public String saveOrder() throws SQLException {
        orders.setId(-1L);
        orders.setDate( LocalDate.now());
        orders.setNumbers(orderRepository.findLastNumber() + 1);
        orderRepository.insertOrder(orders);

        for (ToDo toDo : cartBean.getOrderMap().keySet()) {
            cartRepository.insertContentsOrder(
                    new ContentsOrder(
                            new ContentsOrderId(
                            orderRepository.findLastOrderId(),
                            toDo.getId()),
                            cartBean.getOrderMap().get(toDo)));
        }

        cartBean.setCartList(new LinkedList<>());
        cartBean.setOrderMap(new LinkedHashMap<>());

        return "/index.xhtml?faces-redirect=true";
    }

    public String registrationOrder() {
        this.orders = new Orders();
        return "/registration.xhtml?faces-redirect=true";
    }


}
