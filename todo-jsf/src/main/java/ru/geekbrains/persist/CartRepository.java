package ru.geekbrains.persist;

import javax.ejb.Local;

@Local
public interface CartRepository {

    void insertContentsOrder(ContentsOrder contentsOrder);

}
