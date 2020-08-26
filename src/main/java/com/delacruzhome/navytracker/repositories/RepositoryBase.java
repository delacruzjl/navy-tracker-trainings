package com.delacruzhome.navytracker.repositories;

import java.util.List;

import com.delacruzhome.navytracker.factories.IFactory;
import com.mongodb.client.*;

import org.bson.Document;
import org.bson.types.ObjectId;

public abstract class RepositoryBase<T> implements IRepository<T> {
    protected MongoClient mongoClient;
    protected MongoCollection<Document> collection;
    protected final IFactory<T> trainingFactory;
    private String collectionName;

    public RepositoryBase(MongoClient client, final IFactory<T> trainingFactory, String collectionName) {
        mongoClient = client;
        this.trainingFactory = trainingFactory;
        this.collectionName = collectionName;
        setCollection();
    }

    @Override
    public void setCollection() {
        MongoDatabase db = mongoClient.getDatabase("navy-tracker");
        collection = db.getCollection(collectionName);
    }

    @Override
    public abstract List<T> getAll();

    @Override
    public abstract T findById(ObjectId id);

    @Override
    public abstract T add(T training);

    @Override
    public abstract T update(T training);

    @Override
    public abstract void delete(ObjectId id);
    
}