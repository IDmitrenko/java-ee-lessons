package ru.geekbrains.persist;

public interface OrderRepository {

    void insertOrder(Order order);

    int findLastNumber();

    Long findLastOrderId();

}
