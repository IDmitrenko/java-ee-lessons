package ru.geekbrains.persist;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512)
    private String name;

    @Column
    private LocalDate date;

    @Column
    private int numbers;

    @Column(length = 1024)
    private String address;

    @Column
    private String phone;

    public Order() {
    }

    public Order(Long id, String name, LocalDate date, int numbers, String address, String phone) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.numbers = numbers;
        this.address = address;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getNumbers() {
        return numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (numbers != order.numbers) return false;
        if (id != null ? !id.equals(order.id) : order.id != null) return false;
        if (name != null ? !name.equals(order.name) : order.name != null) return false;
        if (date != null ? !date.equals(order.date) : order.date != null) return false;
        if (address != null ? !address.equals(order.address) : order.address != null) return false;
        return phone != null ? phone.equals(order.phone) : order.phone == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + numbers;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }
}
