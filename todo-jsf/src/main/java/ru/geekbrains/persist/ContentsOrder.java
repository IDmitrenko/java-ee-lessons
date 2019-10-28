package ru.geekbrains.persist;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "contents_order")
public class ContentsOrder {

    @EmbeddedId
    private ContentsOrderId id;

}