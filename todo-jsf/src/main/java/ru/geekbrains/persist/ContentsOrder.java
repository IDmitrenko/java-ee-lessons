package ru.geekbrains.persist;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "contents_order")
public class ContentsOrder {

    @EmbeddedId
    private ContentsOrderId id;

    @Column
    private int count;

    public ContentsOrder() {
    }

    public ContentsOrder(ContentsOrderId id, int count) {
        this.id = id;
        this.count = count;
    }

}