package com.dineout.code.billing;

import com.dineout.R;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static java.lang.String.valueOf;

public class PendingPayments extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<OrderBill> data;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference ref = db.getReference();

    HashMap<String, DishPrice> prices = new HashMap<>();
    HashMap<String, OrderBill> orderlist = new HashMap<>();

    DatabaseReference refOrders;
    DatabaseReference refMenu;
    DatabaseReference refOrderDetails;

    Context c = this;

    //Refresh is called to populate the recyclerview with data of all unpaid orders from firebase. It is called in onCreate and onResume
    public void Refresh() {
        prices = new HashMap<>();
        orderlist = new HashMap<>();
        data = new ArrayList<>();


        refMenu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //This listener gets all the prices and details of menu dishes. Upon completion, it attaches a listener to the Order table to get all orders.
                //Log.e("Price", valueOf(dataSnapshot.getChildrenCount()));
                for (DataSnapshot dishSnapshot : dataSnapshot.getChildren()) {
                    DishPrice dish = dishSnapshot.getValue(DishPrice.class);
                    //Log.e("Get Data", dish.toString());
                    prices.put(dish.dishName, dish);
                    //prices is a hashmap that stores the name of the dish as key and Dishprice() is a class that contains the name and price of the dish.
                }

                //When menu is loaded, this listener gets the list of all the orders with unpaid status (NOT 4)
                refOrders.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            String id = postSnapshot.child("id").getValue(String.class);
                            int status = postSnapshot.child("status").getValue(int.class);
                            String time = postSnapshot.child("timestamp").getValue(String.class);
                            String table = postSnapshot.child("tableID").getValue(String.class);

                            if (status != 4) {
                                //Orderbill o contains details of the order + the arraylist of all dishes and quantities associated with the order.
                                //The arraylist is empty atm because the dishes will be retrieved from firebase in the next listener from OrderDetails table
                                OrderBill o = new OrderBill();
                                o.table = table;
                                o.id = id;
                                o.time = time;
                                o.D = new ArrayList<>();
                                Log.e("ID", id);
                                orderlist.put(o.id, o);
                                //All unpaid orders are put into a hashmap orderlist that takes order id as key and Orderbill() as value. The details read from Orderdetails will be input into this list
                            }
                        }

                        refOrderDetails.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    String id = postSnapshot.child("orderid").getValue(String.class);
                                    String dishname = postSnapshot.child("dishname").getValue(String.class);
                                    int quantity = postSnapshot.child("servings").getValue(int.class);
                                    int status = postSnapshot.child("status").getValue(int.class);

                                    //Log.e("Dish",dishname);
                                    if (status != 4) {
                                        DishOrder d = new DishOrder();
                                        d.quantity = quantity;
                                        DishPrice p = new DishPrice();
                                        if (prices.containsKey(dishname)) {//if details of this item are present in the prices hashmap then add this item to the total orderbill of associated order

                                            //Get prices of the dishname from the prices hashmap and construct a DishOrder() object
                                            //that contains prices + quantity + associated orderid of each
                                            //individual dish order in orderdetails table
                                            p = prices.get(dishname);
                                            d.price = p.price;
                                            d.dishName = p.dishName;

                                            try {
                                                orderlist.get(id).D.add(d);//orderlist is a hashmap. orderlist.get(id) will get the orderbill object for the giver orderid
                                                //D.add(d) will add the dishorder to the arraylist of dishorders of that orderbill.
                                            } catch (Exception e) {

                                            }
                                        } else {
                                            //if the menu item is not found in our hashmap/firebase, create a dishorder with price 0 to prevent crash errors later on
                                            d.dishName = dishname;
                                            d.quantity = quantity;
                                            if (orderlist.containsKey(id)) {
                                                orderlist.get(id).D.add(d);
                                            }
                                        }
                                    }
                                }


                                //Insert the values into the recyclerview adapter
                                data = new ArrayList<>(orderlist.values());
                                Collections.sort(data);
                                if (mAdapter != null) {
                                    mAdapter.notifyDataSetChanged();
                                } else {
                                    createRecyclerView(data);
                                }
                                createRecyclerView(data);
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value
                                Log.w("Billing Module, Test", "Failed to read value.", error.toException());
                            }
                        });


                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("Test", "Failed to read value.", error.toException());
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Test", "Failed to read value.", error.toException());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Refresh();
    }

    //constructor
    public PendingPayments() {
        data = new ArrayList<>();
        refMenu = ref.child("Menu");
        refOrders = ref.child("Order");
        refOrderDetails = ref.child("OrderDetails");
        Refresh();
    }

    protected void createRecyclerView(ArrayList<OrderBill> d) {
        mRecyclerView = (RecyclerView) findViewById(R.id.ordersRecView);
        ProgressBar spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.INVISIBLE);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new OrdersAdapter(data, R.layout.billing_pending_view, this);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                mRecyclerView.getContext(),
                mLayoutManager.getOrientation()
        );
        mRecyclerView.addItemDecoration(mDividerItemDecoration);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billing_activity_pending_payments);
        ref.keepSynced(true);
    }
}
