package com.delacruzhome.navytracker.repositories;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;

import com.delacruzhome.navytracker.factories.IFactory;
import com.delacruzhome.navytracker.factories.TrainingFactoryImpl;
import com.delacruzhome.navytracker.models.Training;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import static com.mongodb.client.model.Filters.*;

import org.bson.BsonValue;
import org.bson.types.ObjectId;

public class TrainingRepositoryImpl extends RepositoryBase<Training> {

    public TrainingRepositoryImpl(MongoClient client, IFactory<Training> trainingFactory) {
        super(client, trainingFactory, "trainings");
    }

    public static TrainingRepositoryImpl initialize() {
        String connString = System.getenv("MongoURI");
        MongoClient client = MongoClients.create(
            MongoClientSettings.builder()
            .applyConnectionString(
                new ConnectionString(connString))
            .build()
        );
        IFactory<Training> trainingFactory = new TrainingFactoryImpl();
        return new TrainingRepositoryImpl(client, trainingFactory);
    }

    @Override
    public List<Training> getAll() {
        final MongoCursor<Training> cursor = collection.find()
        .map(doc -> trainingFactory.create(doc)).cursor();

        final List<Training> result = new ArrayList<>();
        while (cursor.hasNext()) {
            final Training t = cursor.next();
            result.add(t);
        }

        return result == null ? new ArrayList<>() : result;
    }

    @Override
    public Training findById(final ObjectId id) {
        return collection.find(eq("_id", id))
        .map(doc -> trainingFactory.create(doc)).first();
    }

    @Override
    public Training add(Training training) {
        BsonValue id = collection
        .insertOne(trainingFactory.createDocument(training)).getInsertedId();

        training.setId(id.asObjectId().getValue().toString());
        return training;
    }

    @Override
    public Training update(final Training training) throws IllegalArgumentException {
        long count = collection.replaceOne(eq("_id", new ObjectId(training.getId())),
            trainingFactory.createDocument(training)).getModifiedCount();

        if (count < 1) {
            throw new IllegalArgumentException("Could not modify, the id provided is invalid");
        }

        return training;
    }

    @Override
    public void delete(final ObjectId id) throws IllegalArgumentException {
        long count = collection.deleteOne(eq("_id", id)).getDeletedCount();
        if (count < 1){
            throw new IllegalArgumentException("Could not delete, the id provided is invalid");
        }
    }

}