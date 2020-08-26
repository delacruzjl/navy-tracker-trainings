package com.delacruzhome.navytracker.repositories;

import org.bson.types.ObjectId;

import java.util.List;

public interface IRepository<T> {
    void setCollection();
    List<T> getAll();
    T findById(ObjectId id);
    T add(T training);
    T update(T training);
    void delete(ObjectId id);
}