package com.dineout.code.order;

public class MenuItem {
    String dishName;
    String ingredientName;
    String quantity;

    public MenuItem(){

    }
    public MenuItem(String dishName, String ingredientName, String quantity) {
        this.dishName = dishName;
        this.ingredientName = ingredientName;
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }


}
