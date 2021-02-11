package com.mokhovav.goodcare_scheduler.SQL;

import java.util.List;

public interface DBService<T> {
    Long save(T object);
    Object update(T object);
    boolean delete(T object);
    T getById(Long id, Class<T> c);
    T findObject(String text);
    List<T> getAll(Class<T> c);
}
