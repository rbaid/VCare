package com.me.i8080.employee;

import java.io.Serializable;

/**
 * Created by rishabh.baid on 5/8/2015.
 */
public class PeopleList implements Serializable {

    private String name;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
