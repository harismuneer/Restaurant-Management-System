package com.dineout.code.hall.DB;

public class Receipt {
    String orderid;
    int paid;
    int totalamount;

    public Receipt() {
    }

    public Receipt(String orderid, int paid, int totalamount) {
        this.orderid = orderid;
        this.paid = paid;
        this.totalamount = totalamount;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public int getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(int totalamount) {
        this.totalamount = totalamount;
    }
}
