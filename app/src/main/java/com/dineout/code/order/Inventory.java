package com.dineout.code.order;

import java.util.Date;

//Contains details about ingridients ,their status,expiry e.t.c

public class Inventory {
    public String name;
    public Date expiryDate;
    public int price;
    public int quantity;

    public Inventory(String name, Date expiryDate, int price, int quantity) {
        this.name = name;
        this.expiryDate = expiryDate;
        this.price = price;
        this.quantity = quantity;
    }
}
