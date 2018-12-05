package com.dineout.code.order;

public class NotificationClass {

    private String itemName;
    private String time;
    private boolean read;

    public NotificationClass() {
    }

    public NotificationClass(String itemName, String time, boolean read) {
        this.itemName = itemName;
        this.time = time;
        this.read = read;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
