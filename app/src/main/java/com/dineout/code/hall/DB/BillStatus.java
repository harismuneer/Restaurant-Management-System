package com.dineout.code.hall.DB;

/**
 * Created by Khalid on 12/1/2018.
 */

public class BillStatus {
    String tableID;
    String orderID;
    int amount;
    String status;

    public BillStatus() {
    }

    public BillStatus(String tableid, String orderid, int amount, String status) {
        this.tableID = tableid;
        this.orderID = orderid;
        this.amount = amount;
        this.status = status;
    }

    public String getTableID() {
        return tableID;
    }

    public void setTableID(String tableid) {
        this.tableID = tableid;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderid) {
        this.orderID = orderid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int ampunt) {
        this.amount = ampunt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
