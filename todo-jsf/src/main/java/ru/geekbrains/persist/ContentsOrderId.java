package ru.geekbrains.persist;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ContentsOrderId implements Serializable {

    private Long idOrder;

    private Long idTodo;

    public ContentsOrderId() {
    }

    public ContentsOrderId(Long idOrder, Long idTodo) {
        this.idOrder = idOrder;
        this.idTodo = idTodo;
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }

    public Long getIdTodo() {
        return idTodo;
    }

    public void setIdTodo(Long idTodo) {
        this.idTodo = idTodo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContentsOrderId that = (ContentsOrderId) o;

        if (idOrder != null ? !idOrder.equals(that.idOrder) : that.idOrder != null) return false;
        return idTodo != null ? idTodo.equals(that.idTodo) : that.idTodo == null;
    }

    @Override
    public int hashCode() {
        int result = idOrder != null ? idOrder.hashCode() : 0;
        result = 31 * result + (idTodo != null ? idTodo.hashCode() : 0);
        return result;
    }
}
