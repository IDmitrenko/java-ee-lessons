package ru.geekbrains.persist;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ToDo {

    private Long id;

    @NotNull
    private String description;

    private LocalDate targetDate;

    private int idCategory;
    private String categoryDescription;

    public ToDo() {
    }

    public ToDo(Long id, int idCategory, String description, LocalDate targetDate, String categoryDescription) {
        this.id = id;
        this.idCategory = idCategory;
        this.description = description;
        this.targetDate = targetDate;
        this.categoryDescription = categoryDescription;
    }

    public ToDo(Long id, int idCategory, String description, LocalDate targetDate) {
        this.id = id;
        this.idCategory = idCategory;
        this.description = description;
        this.targetDate = targetDate;
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

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ToDo toDo = (ToDo) o;

        if (idCategory != toDo.idCategory) return false;
        if (id != null ? !id.equals(toDo.id) : toDo.id != null) return false;
        if (description != null ? !description.equals(toDo.description) : toDo.description != null) return false;
        return targetDate != null ? targetDate.equals(toDo.targetDate) : toDo.targetDate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (targetDate != null ? targetDate.hashCode() : 0);
        result = 31 * result + idCategory;
        return result;
    }
}
