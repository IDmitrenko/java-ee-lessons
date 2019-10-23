package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.ToDo;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SessionScoped
@Named
public class CartBean implements Serializable {

    public CartBean() {
    }

    private static final Logger logger = LoggerFactory.getLogger(CartBean.class);

    private List<ToDo> cartList = new LinkedList<>();

    Map<ToDo, Integer> orderMap = new LinkedHashMap<>();

    private String number;

    public String addCartTodo(ToDo toDo) {
        cartList.add(toDo);
        return "/index.xhtml?faces-redirect=true";
    }

    public String orderTodo() {
        Integer value = null;
        for (ToDo toDo : cartList) {
            value = orderMap.get(toDo);
            if (value == null) {
                orderMap.put(toDo, 1);
            } else {
                orderMap.put(toDo, value + 1);
            }
        }
        return "/order.xhtml?faces-redirect=true";
    }

    public String getNumber() {
        if (cartList.size() > 0) {
            return "Selected products : " + cartList.size();
        }
        return "";
    }

    public Map<ToDo, Integer> getOrderMap() {
        return orderMap;
    }

    public List<ToDo> getCartList() {
        return cartList;
    }

    public void setCartList(List<ToDo> cartList) {
        this.cartList = cartList;
    }

    public void setOrderMap(Map<ToDo, Integer> orderMap) {
        this.orderMap = orderMap;
    }
}