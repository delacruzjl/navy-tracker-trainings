package com.delacruzhome.navytracker.repositories;

import java.util.List;

public interface IRepository<T> {
    void setCollection();
    List<T> getAll();
    T findById(String id);
    T add(T training);
    T update(T training);
    void delete(String id);
}