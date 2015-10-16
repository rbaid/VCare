package com.me.i8080.employee;

import java.io.Serializable;

/**
 * Created by rishabh.baid on 4/22/2015.
 */
public class UserHistory implements Serializable{

    private long id;
    private long date;
    private float rating;
    private String Description;
    private String happy;
    private String sad;
    private String remember;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getHappy() {
        return happy;
    }

    public void setHappy(String happy) {
        this.happy = happy;
    }

    public String getSad() {
        return sad;
    }

    public void setSad(String sad) {
        this.sad = sad;
    }

    public String getRemember() {
        return remember;
    }

    public void setRemember(String remember) {
        this.remember = remember;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return "date: " + String.valueOf(date) + " Description: " + Description +
                " happy: " + happy + " sad:  " + sad + " remember: " + remember +
                " rating: " + String.valueOf(rating);
    }
}
