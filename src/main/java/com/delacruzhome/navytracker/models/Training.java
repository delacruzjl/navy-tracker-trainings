package com.delacruzhome.navytracker.models;

import java.util.Date;


public class Training {
    private String id;
    private String category;
    private String title;
    private Date due;
    private Boolean recurrent;

    public Training(String id, String category, String title, Date due, Boolean recurrent) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.due = due;
        this.recurrent = recurrent;
    }

    public String getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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