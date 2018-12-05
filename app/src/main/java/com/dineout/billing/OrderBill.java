package com.dineout.billing;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class OrderBill implements Serializable, Comparable {
    String id;
    String table;
    String time;
    ArrayList<DishOrder> D;

    @Override
    public int compareTo(@NonNull Object o) {
        OrderBill od = (OrderBill) o;
        int compareid = Integer.valueOf(od.getId());
        return Integer.valueOf(this.id) - compareid;
    }

    public OrderBill() {
        id = UUID.randomUUID().toString();
        table = "0";
        D = new ArrayList<DishOrder>();
        time = "0";
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public OrderBill(String id, String table, ArrayList<DishOrder> d, String time) {
        id = UUID.randomUUID().toString();
        this.table = table;
        D = new ArrayList<DishOrder>(d);
        this.time = time;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public ArrayList<DishOrder> getD() {
        return D;
    }

    public void setD(ArrayList<DishOrder> d) {
        D = d;
    }

    public int getTotal() {
        int sum = 0;
        if (!D.isEmpty()) {
            for (int i = 0; i < D.size(); i++) {
                sum += D.get(i).getTotal();
            }
        }
        return sum;
    }


}
