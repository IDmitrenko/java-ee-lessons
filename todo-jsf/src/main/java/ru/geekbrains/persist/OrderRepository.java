package ru.geekbrains.persist;

import javax.ejb.Local;

@Local
public interface OrderRepository {

    void insertOrder(Orders orders);

    int findLastNumber();

    Long findLastOrderId();

}
