package com.dineout.code.admin;

public class Menu {
    private String dishName;
    private String estimatedTime; //It would be stored in minutes
    String type;
    String price;

    public Menu(String dishName, String estimatedTime, String type, String price) {
        this.dishName = dishName;
        this.estimatedTime = estimatedTime;
        this.type = type;
        this.price = price;
    }

    public Menu() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
