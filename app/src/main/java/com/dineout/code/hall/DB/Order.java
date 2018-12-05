package com.dineout.code.hall.DB;

/**
 * Created by Khalid on 12/1/2018.
 */

public class Order {
    String tableID;
    String id;
    int status;
    String timestamp;

    public Order() {
    }

    public Order(String tableID, String id, int status, String timestamp) {
        this.tableID = tableID;
        this.id = id;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getTableID() {
        return tableID;
    }

    public void setTableID(String tableID) {
        this.tableID = tableID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
