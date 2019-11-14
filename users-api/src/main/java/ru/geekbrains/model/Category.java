package ru.geekbrains.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Category implements Serializable {

    private Integer id;

    private String description;

    public Category(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}