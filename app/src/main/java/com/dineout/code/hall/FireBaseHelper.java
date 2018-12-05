package com.dineout.code.hall;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dineout.code.hall.DB.Assignment;
import com.dineout.code.hall.DB.BillStatus;
import com.dineout.code.hall.DB.NotificationClass;
import com.dineout.code.hall.DB.Order;
import com.dineout.code.hall.DB.OrderDetails;
import com.dineout.code.hall.DB.Table;
import com.dineout.code.hall.DB.Tablet;
import com.dineout.code.hall.DB.Receipt;
import com.dineout.code.hall.DB.Employee;
import com.dineout.code.hall.DB.Item;
import com.dineout.code.hall.DB.MenuItem;
import com.dineout.code.hall.DB.Menu;

import com.dineout.R;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;


public class FireBaseHelper {

    private static DatabaseReference mDatabase;

    public FireBaseHelper() {
        if(mDatabase==null)
            mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public static DatabaseReference getmDatabase() {
        return mDatabase;
    }

    public void NewOrderDetails(String orderid, String dishname, int estimatedtime, int priority, int status, int servings) {
        OrderDetails orderDetails = new OrderDetails(orderid,dishname,estimatedtime,priority,status,servings);
        mDatabase.child("OrderDetails").push().setValue(orderDetails);
    }

    public void NewOrder(String id, String timestamp, String tableID, int status) {
        Order order = new Order(tableID,id,status,timestamp);
        mDatabase.child("Order").child(id).setValue(order);
    }

    public void NewMenu(String dishname, int estimatedtime, String typeofdish, int price) {
        Menu menu = new Menu(dishname,String.valueOf(estimatedtime),typeofdish,String.valueOf(price));
        mDatabase.child("Menu").child(dishname).setValue(menu);
    }

    public void NewMenuItems(String dishname, String itemname, int quantity) {
        MenuItem menuItems = new MenuItem(dishname,itemname,String.valueOf(quantity));
        mDatabase.child("MenuItem").child(dishname).child(itemname).setValue(menuItems);
    }

    public void NewInventory(String itemname, int price, int quantity, int minthreshold) {
        Item inventory = new Item(itemname,String.valueOf(price),String.valueOf(quantity),String.valueOf(minthreshold));
        mDatabase.child("Inventory").child(itemname).setValue(inventory);
    }

    public void NewNotification(String itemName, boolean read, String time) {
        NotificationClass notification = new NotificationClass(itemName,read,time);
        mDatabase.child("notification").push().setValue(notification);
    }

    public void NewReceipt(String orderid, int paid, int totalamount) {
        Receipt receipt = new Receipt(orderid,paid,totalamount);
        mDatabase.child("Receipt").child(orderid).setValue(receipt);
    }

    public void UpdateOrderStatus(String orderid, final int status) {
        mDatabase.getDatabase().getReference("Order").child(orderid).child("status").setValue(status);
    }

    public void UpdateOrderDetails(final String orderid, final String dishname, final int estimatedtime, final int servings, final int count) {
        mDatabase.getDatabase().getReference("OrderDetails").runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                    OrderDetails order = mutableData.getValue(OrderDetails.class);
                    if(order==null)
                        return Transaction.success(mutableData);
                    if(order.getOrderid().compareTo(orderid)==0 && order.getDishname().compareTo(dishname)==0) {
                        if(count==servings)
                            mDatabase.child("OrderDetails").child(mutableData.getKey()).removeValue();
                        else
                        {
                            order.setServings(order.getServings()-count);
                            mutableData.setValue(order);
                        }
                    }
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

                NewOrderDetails(orderid,dishname,estimatedtime,1,0,count);
                UpdateOrderStatus(orderid,0);
            }
        });
    }



}
