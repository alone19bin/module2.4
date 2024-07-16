package org.maxim.RestApi.repository;

import java.util.List;

public interface GenericRepository <T, ID>{
    T create(T object);
    T get(ID id);
    T update(T object);
    void delete(ID id);
    List<T> getAll();
}