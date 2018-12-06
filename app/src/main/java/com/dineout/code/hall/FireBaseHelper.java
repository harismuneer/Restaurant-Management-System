package com.dineout.code.hall;


import android.support.annotation.NonNull;
import android.widget.Toast;

import com.dineout.code.hall.DB.Item;
import com.dineout.code.hall.DB.Menu;
import com.dineout.code.hall.DB.MenuItem;
import com.dineout.code.hall.DB.NotificationClass;
import com.dineout.code.hall.DB.Order;
import com.dineout.code.hall.DB.OrderDetails;
import com.dineout.code.hall.DB.Receipt;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


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

    public boolean done = false;

    public void UpdateOrderDetails(final String orderid, final String dishname, final int estimatedtime, final int servings, int count) {
        final int save = count;
        mDatabase.getDatabase().getReference("OrderDetails").orderByChild("orderid").equalTo(orderid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    if(!done) {
                        OrderDetails order = snapshot.getValue(OrderDetails.class);
                        if (order == null)
                            return;
                        if (order.getOrderid().compareTo(orderid) == 0 && order.getDishname().compareTo(dishname) == 0) {
                            if (save == servings) {
                                DeleteOrderDetails(snapshot.getKey());
                            } else {
                                UpdateOrderDetails(snapshot.getKey(), order.getServings() - save);
                            }
                            UpdateOrderStatus(orderid, 0);
                            NewOrderDetails(orderid, dishname, estimatedtime, 1, 0, save);
                            done = true;
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void UpdateOrderDetails(String key, int i) {
        mDatabase.getDatabase().getReference("OrderDetails").child(key).child("servings").setValue(i);
    }

    public void DeleteAll() {
        mDatabase.getDatabase().getReference("OrderDetails").removeValue();
    }

    private void DeleteOrderDetails(String key) {
        mDatabase.getDatabase().getReference("OrderDetails").child(key).removeValue();
    }

}
