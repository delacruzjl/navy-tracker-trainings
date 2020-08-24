package com.delacruzhome.navytracker.models;

import java.util.Date;

import org.bson.BsonObjectId;

public class Training {
    private BsonObjectId id;
    private String category;
    private String title;
    private Date due;
    private Boolean recurrent;

    public String getCategory() {
        return category;
    }

    public BsonObjectId getId() {
        return id;
    }

    public void setId(BsonObjectId id) {
        this.id = id;
    }

    public Boolean getRecurrent() {
        return recurrent;
    }

    public void setRecurrent(Boolean recurrent) {
        this.recurrent = recurrent;
    }

    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}