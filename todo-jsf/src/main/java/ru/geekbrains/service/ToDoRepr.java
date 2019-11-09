package ru.geekbrains.service;

import ru.geekbrains.persist.Category;

import java.util.Date;

public class ToDoRepr {

    private Long id;

    private String description;

    private double price;

    private int count;

    private Boolean active;

    private Date targetDate;

    private Category category;

    public ToDoRepr(Long id, String description, double price, int count, Boolean active, Date targetDate, Category category) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.count = count;
        this.active = active;
        this.targetDate = targetDate;
        this.category = category;
    }

    public ToDoRepr() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
