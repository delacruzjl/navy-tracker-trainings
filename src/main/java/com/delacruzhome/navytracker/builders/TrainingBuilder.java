package com.delacruzhome.navytracker.builders;

import com.delacruzhome.navytracker.models.Training;

import java.util.Date;

public class TrainingBuilder {
    private String id;
    private String category;
    private String title;
    private Date due;
    private Boolean recurrent;

    public TrainingBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public TrainingBuilder setCategory(String category) {
        this.category = category;
        return this;
    }

    public TrainingBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public TrainingBuilder setDue(Date due) {
        this.due = due;
        return this;
    }

    public TrainingBuilder setRecurrent(Boolean recurrent) {
        this.recurrent = recurrent;
        return this;
    }

    public Training createTraining() {
        return new Training(id, category, title, due, recurrent);
    }
}