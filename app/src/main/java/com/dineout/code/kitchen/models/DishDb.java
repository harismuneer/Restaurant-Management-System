package com.dineout.code.kitchen.models;

public class DishDb {

    private String dishName;
    private String estimatedTime;
    private String price;
    private String type;

    public DishDb(String dishName, String estimatedTime, String price, String type) {
        this.dishName = dishName;
        this.estimatedTime = estimatedTime;
        this.price = price;
        this.type = type;
    }

    public DishDb() {
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
