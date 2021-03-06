package ru.geekbrains.persist;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(name = "todos")
public class ToDo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, length = 4096)
    private String description;

    @Column(scale = 2, precision = 10)
    private Double price;

    @Column
    private Integer count;

    @Column
    private Boolean active;

    @Column
    private LocalDate targetDate;

    @ManyToOne
    @JoinColumn(name = "idCategory")
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ToDo() {
    }

    public ToDo(Long id, Category category, String description, LocalDate targetDate) {
        this.id = id;
        this.category = category;
        this.description = description;
        this.targetDate = targetDate;
    }

    public ToDo(@NotNull String description, Double price, Integer count, Boolean active, LocalDate targetDate, Category category) {
        this.description = description;
        this.price = price;
        this.count = count;
        this.active = active;
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

    public void setTargetDateAsLocal(Date date) {
        if (date != null) {
            targetDate = date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }
    }

    public Date getTargetDateAsLocal() {
        if (targetDate != null) {
            return Date.from(targetDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        return null;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ToDo toDo = (ToDo) o;

        if (!id.equals(toDo.id)) return false;
        if (description != null ? !description.equals(toDo.description) : toDo.description != null) return false;
        if (targetDate != null ? !targetDate.equals(toDo.targetDate) : toDo.targetDate != null) return false;
        return category != null ? category.equals(toDo.category) : toDo.category == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (targetDate != null ? targetDate.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }
}
