package com.dineout.code.hall.DB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuItems implements Serializable {
    List<MenuItem> items = new ArrayList<>();

    public MenuItems() {
    }

    public MenuItems(List<MenuItem> items) {
        this.items = items;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

}
