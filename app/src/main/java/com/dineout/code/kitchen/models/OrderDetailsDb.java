package com.dineout.code.kitchen.models;

import java.io.Serializable;

public class OrderDetailsDb implements Serializable
{

    private String dishname;
    private int estimatedtime;
    private String orderid;
    private int priority;
    private int servings;
    private int status;
    private String nodeId;

    public OrderDetailsDb() {
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public OrderDetailsDb(String dishname, int estimatedtime, String orderid, int priority, int servings, int status) {
        this.dishname = dishname;
        this.estimatedtime = estimatedtime;
        this.orderid = orderid;
        this.priority = priority;
        this.servings = servings;
        this.status = status;
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

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
