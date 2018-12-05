package com.dineout.code.kitchen.models;

import java.io.Serializable;

public class Order implements Serializable
{
    private String mStatus;
    private String mItemName;

    public Order() {
    }

    public Order(String status, String itemName)
    {
        mStatus = status;
        mItemName = itemName;
    }

    public String getStatus()
    {
        return mStatus;
    }

    public void setStatus(String status)
    {
        mStatus = status;
    }

    public String getItemName()
    {
        return mItemName;
    }

    public void setItemName(String itemName)
    {
        mItemName = itemName;
    }
}
