package com.dineout.code.hall.DB;

import java.io.Serializable;

public class NotificationClass implements Serializable {
    String itemName;
    boolean read;
    String time;

    public NotificationClass() {
        //Default Constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public NotificationClass(String itemName, boolean read, String time) {
        this.itemName = itemName;
        this.read = read;
        this.time = time;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
