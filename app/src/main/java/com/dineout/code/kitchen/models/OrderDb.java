package com.dineout.code.kitchen.models;

public class OrderDb {

    private String id;
    private int status;
    private String tableID;
    private String timestamp;
    private String nodeid;

    public OrderDb(String id, int status, String tableID, String timestamp) {
        this.id = id;
        this.status = status;
        this.tableID = tableID;
        this.timestamp = timestamp;
    }

    public OrderDb() {
    }

    public String getId() {
        return id;
    }

    public String getNodeid() {
        return nodeid;
    }

    public void setNodeid(String nodeid) {
        this.nodeid = nodeid;
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

    public String getTableID() {
        return tableID;
    }

    public void setTableID(String tableID) {
        this.tableID = tableID;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
