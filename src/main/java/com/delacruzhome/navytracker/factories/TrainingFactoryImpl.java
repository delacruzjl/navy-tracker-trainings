package com.delacruzhome.navytracker.factories;

import java.util.Date;

import com.delacruzhome.navytracker.builders.TrainingBuilder;
import com.delacruzhome.navytracker.models.Training;

import org.bson.Document;

public class TrainingFactoryImpl implements IFactory<Training> {

    @Override
    public Training create(Document doc) {
        Training t = new TrainingBuilder()
                .setId(doc.getObjectId("_id").toString())
                .setCategory(doc.getString("category"))
                .setTitle(doc.getString("title"))
                .setDue((Date)doc.get("due"))
                .setRecurrent(doc.getBoolean("recurrent") != null ? doc.getBoolean("recurrent") : null)
                .createTraining();

        return t;
    }

    @Override
    public Document createDocument(Training obj) {
        Document doc = new Document();
        doc.append("category", obj.getCategory());
        doc.append("title", obj.getTitle());
        doc.append("due", obj.getDue());

        if (obj.getRecurrent() != null && obj.getRecurrent() == true) {
            doc.append("recurrent", obj.getRecurrent());
        }

        return doc;
    }
}