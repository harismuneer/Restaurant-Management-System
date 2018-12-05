package com.dineout.code.order;

public class Order {
    private String id;
    private String tabletID;
    private String timestamp;
    private int status;

    public Order(){

    }

    public Order(String id, String tabletID, String timestamp, int status) {
        this.id = id;
        this.tabletID = tabletID;
        this.timestamp = timestamp;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTabletID() {
        return tabletID;
    }

    public void setTabletID(String tabletID) {
        this.tabletID = tabletID;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
