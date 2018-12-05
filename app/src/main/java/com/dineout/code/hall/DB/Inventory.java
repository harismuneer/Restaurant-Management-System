package com.dineout.code.hall.DB;

import java.io.Serializable;

public class Inventory implements Serializable {
    String itemname;
    int price;
    int quantity;
    int minthreshold;

    public Inventory() {
        //Default Constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Inventory(String itemname, int price, int quantity, int minthreshold) {
        this.itemname = itemname;
        this.price = price;
        this.quantity = quantity;
        this.minthreshold = minthreshold;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMinthreshold() {
        return minthreshold;
    }

    public void setMinthreshold(int minthreshold) {
        this.minthreshold = minthreshold;
    }
}
