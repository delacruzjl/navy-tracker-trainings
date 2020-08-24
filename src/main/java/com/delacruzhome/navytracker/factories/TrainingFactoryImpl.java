package com.delacruzhome.navytracker.factories;

import com.delacruzhome.navytracker.models.Training;
import com.google.gson.Gson;

import org.bson.Document;

public class TrainingFactoryImpl implements IFactory<Training> {
    public Training create(Document doc) {
        Training t = new Training();
        t.setCategory(doc.getString("category"));
        t.setTitle(doc.getString("title"));
        t.setDue(doc.getDate("due"));
        t.setRecurrent(doc.getBoolean("recurrent"));
        
        return t;
    }

    @Override
    public Document creaDocument(Training obj) {
        Gson gson = new Gson();
        return Document.parse(gson.toJson(obj));
    }
}