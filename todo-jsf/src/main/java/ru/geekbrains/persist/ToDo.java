package ru.geekbrains.persist;

import java.time.LocalDate;

public class ToDo {

    private Long id;

    private String description;

    private LocalDate targetDate;

    public ToDo() {
    }

    public ToDo(Long id, String description, LocalDate targetDate) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ToDo toDo = (ToDo) o;

        if (id != null ? !id.equals(toDo.id) : toDo.id != null) return false;
        if (description != null ? !description.equals(toDo.description) : toDo.description != null) return false;
        return targetDate != null ? targetDate.equals(toDo.targetDate) : toDo.targetDate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (targetDate != null ? targetDate.hashCode() : 0);
        return result;
    }
}
