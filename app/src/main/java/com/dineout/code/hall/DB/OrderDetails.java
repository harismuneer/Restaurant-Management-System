package com.dineout.code.hall.DB;

import java.io.Serializable;

public class OrderDetails implements Serializable {
    String orderid;
    String dishname;
    int estimatedtime;
    int priority;
    int status;
    int servings;

    public OrderDetails() {
        //Default Constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public OrderDetails(String orderid, String dishname, int estimatedtime, int priority, int status, int servings) {
        this.orderid = orderid;
        this.dishname = dishname;
        this.estimatedtime = estimatedtime;
        this.priority = priority;
        this.status = status;
        this.servings = servings;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getDishname() {
        return dishname;
    }

    public void setDishname(String dishname) {
        this.dishname = dishname;
    }

    public int getEstimatedtime() {
        return estimatedtime;
    }

    public void setEstimatedtime(int estimatedtime) {
        this.estimatedtime = estimatedtime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }
}
