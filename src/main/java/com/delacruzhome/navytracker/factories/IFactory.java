package com.delacruzhome.navytracker.factories;

import org.bson.Document;

public interface IFactory<T> {
    T create(Document doc);
    Document creaDocument(T obj);
}