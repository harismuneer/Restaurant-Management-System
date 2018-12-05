package com.dineout.code.kitchen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.dineout.R;

import com.dineout.code.kitchen.models.AttendanceDb;
import com.dineout.code.kitchen.models.Chef;
import com.dineout.code.kitchen.models.DishDb;
import com.dineout.code.kitchen.models.EmployeeDb;
import com.dineout.code.kitchen.models.Order;
import com.dineout.code.kitchen.models.OrderDb;
import com.dineout.code.kitchen.models.OrderDetailsDb;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    public static int WAITING = 0;
    public static int COOKING = 1;
    public static int READY = 2;
    public static int HIGHPRIORITY = 1;
    public static int LOWPRIORITY = 0;
  //  RecyclerViewAdapterCook adapter;

    static ArrayList<OrderDetailsDb> orderDetails = new ArrayList<>();
    static ArrayList<AttendanceDb> attendanceDbs = new ArrayList<>();
    static ArrayList<Chef> mChefs;
    static ArrayList<DishDb> dishes = new ArrayList<>();
    static RecyclerViewAdapterCook adapter;
    public static ArrayList<OrderDb> orders = new ArrayList<>();
    //public static ArrayList<Chef> allChefs = new ArrayList<>();
    int x = 1;
    static int chefNo = 0;
    static FirebaseDatabase mDatabase;
    static Boolean selected = false;
    DatabaseReference myDbRef;
    ActionBar actionBar;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

       // Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();

        setContentView(R.layout.kitchen_activity_main);
        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffff8800")));
        actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>AROS </font>"));
        progressDialog = new ProgressDialog(MainActivity.this);

        //Initializing Database
        mDatabase = FirebaseDatabase.getInstance();
        myDbRef = mDatabase.getReference("Employee");

        // mChefs.clear();
        // dishes.clear();

        //getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.cardview_dark_background)));
        /*if(!selected)
        {*/
            ExtractChefsFromDb();
            mChefs = new ArrayList<>();
            selected = false;
        // }
    }

    public static int getDishCookingTime(String dish){
        for(DishDb dis:dishes){
            if(dis.getDishName().equals(dish))
                return Integer.parseInt(dis.getEstimatedTime());
        }

        return 0;
    }

    public static String getDishType(String dish){
        for(DishDb dis:dishes){
            if(dis.getDishName().equals(dish))
                return dis.getType();
        }

        return "";
    }

    public void onLoadBalancingClicked(View view){
        for(Chef chef:mChefs){
            for (OrderDetailsDb dish:chef.getChefQueue()){
                if(dish.getStatus() == WAITING)
                    orderDetails.add(dish);
            }

        }

        for (Chef chef:mChefs){
            boolean removed = true;
            while(removed){
                removed = false;
                for (int i=0;i<chef.getChefQueue().size();i++){
                    if (chef.getChefQueue().get(i).getStatus() == WAITING){
                        chef.getChefQueue().remove(i);
                        removed = true;
                    }
                }
            }
        }

        boolean assigned = true;

        for(OrderDetailsDb dish:orderDetails){
            assigned = assignDishToChef(dish);
        }
        if(assigned)
            orderDetails = new ArrayList<>();

        adapter.notifyDataSetChanged();
    }

    private boolean assignDishToChef(OrderDetailsDb dish){
        Chef temp = null;
        int time = -1;

        for(Chef chef:mChefs){
            if (chef.isPresent()){
                if(chef.getSpecialty().equals(getDishType(dish.getDishname()))){
                    if (time == -1){
                        temp = chef;
                        time = chef.returnWaitingTime();
                    }
                    else{
                        if(chef.returnWaitingTime() < temp.returnWaitingTime()){
                            time = chef.returnWaitingTime();
                            temp = chef;
                        }
                    }
                }
            }
        }

        if(time != -1){
            temp.addDish(dish);
            //temp.addOrder(new Order(( new Integer(dish.getStatus())).toString(), dish.getDishname()));
            updateDishTime(dish, temp.returnCookingTime() + temp.returnWaitingTime() + getDishCookingTime(dish.getDishname()));
            return true;
        }


        for(Chef chef:mChefs){
            if (chef.isPresent()){

                if (time == -1){
                    temp = chef;
                    time = chef.returnWaitingTime();
                }
                else{
                    if(chef.returnWaitingTime() < temp.returnWaitingTime()){
                        time = chef.returnWaitingTime();
                        temp = chef;
                    }
                }

            }
        }

        if (time != -1)
        {
            temp.addDish(dish);
            //temp.addOrder(new Order(( new Integer(dish.getStatus())).toString(), dish.getDishname()));
            updateDishTime(dish, temp.returnCookingTime() + temp.returnWaitingTime() + getDishCookingTime(dish.getDishname()));
            return true;
        }
        else
            return false;
    }

    public void reinitializeCookAdapter()
    {
        adapter = new RecyclerViewAdapterCook(this, mChefs);
        recyclerView.setAdapter(adapter);
    }

    public static void removeOrderDetailsFromDb(OrderDetailsDb dish){
        mDatabase.getReference("OrderDetails").child(dish.getNodeId()).removeValue();
    }

    public static void updateDishStatus(OrderDetailsDb dish, int newStatus)
    {
        mDatabase.getReference("OrderDetails").child(dish.getNodeId()).child("status").setValue(newStatus);
        dish.setStatus(newStatus);
    }

    public static void updateDishServings(OrderDetailsDb dish, int newServings)
    {
        mDatabase.getReference("OrderDetails").child(dish.getNodeId()).child("servings").setValue(newServings);
        dish.setServings(newServings);
    }

    public static void updateDishTime(OrderDetailsDb dish, int newTime)
    {
        mDatabase.getReference("OrderDetails").child(dish.getNodeId()).child("estimatedtime").setValue(newTime);
        dish.setEstimatedtime(newTime);
    }

    private void ExtractOrders()
    {
        mDatabase.getReference("OrderDetails").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                startProgressBar("Adding new dish...");

                OrderDetailsDb dish = dataSnapshot.getValue(OrderDetailsDb.class);
                dish.setNodeId(dataSnapshot.getKey());

                if(dish.getStatus() == WAITING || dish.getStatus() == COOKING)
                {
                    if (!assignDishToChef(dish))
                    {
                        // If dish is in waiting or cooking state then show it
                        orderDetails.add(dish);
                    }
                    else
                    {
                        reinitializeCookAdapter();
                        // if (dish.getDishname().equals("Soup"))
                        //   updateDishStatus(dish, 9);
                    }
                }

                dismissProgressBar();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void startProgressBar(String message)
    {
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    private void dismissProgressBar()
    {
        progressDialog.dismiss();
    }

    public void ExtractMenuFromDb()
    {
        mDatabase.getReference("Menu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                startProgressBar("Loading Menu...");

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DishDb dish = postSnapshot.getValue(DishDb.class);
                        dishes.add(dish);
                }

                dismissProgressBar();

                ExtractOrders();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void openAttendance(View view)
    {
        Intent intent = new Intent(MainActivity.this, AttendanceActivity.class);
        intent.putExtra("chefs", new ArrayList<>(mChefs));
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == 123){
                boolean assigned = true;
                for (OrderDetailsDb dish:orderDetails){
                    assigned = assignDishToChef(dish);
                }
                orderDetails = new ArrayList<>();
                recyclerView.notify();
            }

            recyclerView.notify();
        }
    }

    private void loadAttendance(){
        progressDialog.setMessage("Loading availability info...");
        mDatabase.getReference("Attendance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                for (DataSnapshot postSnapshot : datasnapshot.getChildren()) {
                    AttendanceDb attendance = postSnapshot.getValue(AttendanceDb.class);

                    for (Chef chef: mChefs){
                        if(chef.getId().equals(attendance.getId()))
                            chef.setPresent(new Boolean(attendance.getPresent().booleanValue()));
                    }
                }

                progressDialog.dismiss();
                initRecyclerView();

                ExtractMenuFromDb();
                getOrdersFromDb();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }

    private void ExtractChefsFromDb()
    {
        /*
        // Testing
        mChefs.clear();
        mChefs.add(new Chef("Chef 1"));
        mChefs.add(new Chef("Chef 2"));
        mChefs.add(new Chef("Chef 3"));
        mChefs.add(new Chef("Chef 4"));
        mChefs.add(new Chef("Chef 5"));
        mChefs.add(new Chef("Chef 6"));
        mChefs.add(new Chef("Chef 7"));
        mChefs.add(new Chef("Chef 8"));
        mChefs.add(new Chef("Chef 9"));
        mChefs.add(new Chef("Chef 10"));*/

        progressDialog.setMessage("Loading Chefs...");
        progressDialog.show();

        myDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    EmployeeDb employee = postSnapshot.getValue(EmployeeDb.class);

                    if (employee.getType().equals("Chef")){
                        mChefs.add(new Chef(employee.getName(), employee.getId(), new ArrayList<Order>(), employee.getSpeciality(), new ArrayList<OrderDetailsDb>(), true));
                    }
                }
                //progressDialog.dismiss();
                ExtractOrdersOfChefsFromDb();
                loadAttendance();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
                progressDialog.dismiss();
            }
        });
    }

    private void ExtractOrdersOfChefsFromDb()
    {
        /*
        // Testing
        mChefs.get(0).addOrder(new Order("Ready","Burger"));
        mChefs.get(0).addOrder(new Order("Ready","Fries"));
        mChefs.get(0).addOrder(new Order("Cooking","Rice"));
        mChefs.get(0).addOrder(new Order("Cooking","Chicken Maslaa"));
        mChefs.get(0).addOrder(new Order("Cooking","Egg Fried Rice"));
        mChefs.get(0).addOrder(new Order("Cooking","Chinese Rice"));
        mChefs.get(0).addOrder(new Order("Cooking","0"));
        mChefs.get(0).addOrder(new Order("Cooking","1"));
        mChefs.get(0).addOrder(new Order("Cooking","2"));
        mChefs.get(0).addOrder(new Order("Cooking","3"));
        mChefs.get(0).addOrder(new Order("Cooking","8"));
        mChefs.get(0).addOrder(new Order("Waiting","Pasta"));
        mChefs.get(0).addOrder(new Order("Waiting","Mutton handi"));*/
       /* mChefs.get(1).addOrder(new Order("Cooking","Zinger"));
        mChefs.get(1).addOrder(new Order("Cooking","Fries"));
        mChefs.get(2).addOrder(new Order("Cooking","Biryani"));
        mChefs.get(2).addOrder(new Order("Waiting","Egg"));
        mChefs.get(3).addOrder(new Order("Cooking","Pizza"));
        mChefs.get(4).addOrder(new Order("Cooking","Rice"));
        mChefs.get(5).addOrder(new Order("Cooking","Chicken Maslaa"));
        mChefs.get(6).addOrder(new Order("Cooking","Egg Fried Rice"));
        mChefs.get(6).addOrder(new Order("Cooking","Chinese Rice"));
        mChefs.get(7).addOrder(new Order("Ready","Chinese Rice"));
        mChefs.get(7).addOrder(new Order("Ready","Chinese Rice"));
        mChefs.get(7).addOrder(new Order("Waiting","Chinese Rice"));*/
    }

    private void initRecyclerView()
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerViewAdapterCook(this, mChefs);
        recyclerView.setAdapter(adapter);
    }

    public static void updateOrderStatus(OrderDb order, int newStatus){

        mDatabase.getReference("Order").child(order.getNodeid()).child("status").setValue(newStatus);
        order.setStatus(newStatus);

    }

    private void getOrdersFromDb(){
        mDatabase.getReference("Order").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                startProgressBar("Adding orders...");

                OrderDb order = dataSnapshot.getValue(OrderDb.class);
                order.setNodeid(dataSnapshot.getKey());

                if(order.getStatus() == WAITING || order.getStatus() == COOKING){
                    orders.add(order);
                }

                dismissProgressBar();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static boolean isOrderCompleted(String orderId){
        for(Chef chef:mChefs){
            for(OrderDetailsDb dish:chef.getChefQueue()){

                if(dish.getOrderid().equals(orderId) && (dish.getStatus() == WAITING || dish.getStatus() == COOKING))
                    return false;
            }
        }

        return true;
    }

    public static OrderDb getOrderFromId(String id){
        for (OrderDb order:orders){
            if(order.getId().equals(id))
                return order;
        }

        return null;
    }

    public static void updateOrderStatusCooking(String orderId){
        for(OrderDb order:orders){
            if(order.getStatus() == WAITING){
                updateOrderStatus(order, COOKING);
                order.setStatus(COOKING);
            }
        }
    }
}
