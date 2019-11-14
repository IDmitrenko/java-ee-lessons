package ru.geekbrains.model;

import java.io.Serializable;
import java.time.LocalDate;

public class ToDo implements Serializable {

    private Long id;

    private String description;

    private LocalDate targetDate;

    private Category category;

    public ToDo(Long id, String description, LocalDate targetDate, Category category) {
        this.id = id;
        this.description = description;
        this.targetDate = targetDate;
        this.category = category;
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

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}