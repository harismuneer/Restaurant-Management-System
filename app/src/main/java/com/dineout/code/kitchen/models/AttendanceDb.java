package com.dineout.code.kitchen.models;

import java.io.Serializable;

public class AttendanceDb implements Serializable
{
    private String id;
    private Boolean present;

    public AttendanceDb(String id, Boolean present) {
        this.id = id;
        this.present = present;
    }

    public AttendanceDb() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getPresent() {
        return present;
    }

    public void setPresent(Boolean present) {
        this.present = present;
    }
}
