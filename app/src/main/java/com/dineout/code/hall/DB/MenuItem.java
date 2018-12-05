package com.dineout.code.hall.DB;

public class MenuItem {
    String dishName;
    String ingredientName;
    String quantity;

    public MenuItem(String dishName, String ingredientName, String quantity) {
        this.dishName = dishName;
        this.ingredientName = ingredientName;
        this.quantity = quantity;
    }

    public MenuItem() {
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
