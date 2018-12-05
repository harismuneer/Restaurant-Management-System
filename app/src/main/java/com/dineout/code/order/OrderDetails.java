package com.dineout.code.order;

public class OrderDetails {
    String dishname;
    int estimatedtime;
    String orderid;
    int priority;
    int servings;
    int status;

public OrderDetails()
{

}
    public OrderDetails(String dishname, int estimatedtime, String orderid, int priority, int servings, int status) {
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

    public int getEstimatedtime() {
        return estimatedtime;
    }

    public String getOrderid() {
        return orderid;
    }

    public int getPriority() {
        return priority;
    }

    public int getServings() {
        return servings;
    }

    public int getStatus() {
        return status;
    }

    public void setDishname(String dishname) {
        this.dishname = dishname;
    }

    public void setEstimatedtime(int estimatedtime) {
        this.estimatedtime = estimatedtime;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
