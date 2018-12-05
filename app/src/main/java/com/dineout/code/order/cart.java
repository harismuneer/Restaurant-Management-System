package com.dineout.code.order;
//Cart class defines skeleton of cart in db .It contains dishName that was ordered and its quantity
public class cart {

    String addedname;                      //Dish name
    int quantity;                         //Quantity of Dish ordered

    public cart(){

    }

    public cart(String addedname, int Quantity) {
        this.addedname = addedname;
        this.quantity = Quantity;
    }

    public String getAddedname() {
        return addedname;
    }

    public void setAddedname(String addedname) {
        this.addedname = addedname;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
       this.quantity = quantity;
    }
}
