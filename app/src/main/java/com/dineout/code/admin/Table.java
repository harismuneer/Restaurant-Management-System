package com.dineout.code.admin;

public class Table {
    String tableID;
    String status;
    String capacity;

    public Table(String tableID, String status, String capacity) {
        this.tableID = tableID;
        this.status = status;
        this.capacity = capacity;
    }

    public Table() {
    }

    public String getTableID() {
        return tableID;
    }

    public void setTableID(String tableID) {
        this.tableID = tableID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }
}
