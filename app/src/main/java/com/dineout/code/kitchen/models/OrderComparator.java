package com.dineout.code.kitchen.models;

import java.util.Comparator;

public class OrderComparator implements Comparator<OrderDetailsDb> {

    @Override
    public int compare(OrderDetailsDb o1, OrderDetailsDb o2) {
        if (o2.getStatus() != o1.getStatus())
            return (new Integer(o2.getStatus())).compareTo((new Integer(o1.getStatus())));

        return (new Integer(o2.getPriority())).compareTo((new Integer(o1.getPriority())));
    }
}
