package com.dineout.billing;

import java.io.Serializable;

public class DishOrder extends DishPrice implements Serializable {
    int quantity;

    public DishOrder() {
        quantity = 0;
        price = "0";
    }

    public DishOrder(int quantity) {
        this.quantity = quantity;
    }

    public DishOrder(String dishname, String price, String estimatedtime, String typeofdish, int quantity) {
        super(dishname, price, estimatedtime, typeofdish);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotal() {
        return this.quantity * Integer.valueOf(this.price);
    }
}
