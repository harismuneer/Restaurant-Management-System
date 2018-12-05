package com.dineout.code.hall;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.dineout.R;

import com.dineout.code.hall.DB.Table;
import com.dineout.code.hall.DB.Assignment;
import com.dineout.code.hall.DB.BillStatus;
import com.dineout.code.hall.DB.Order;
import com.dineout.code.hall.DB.Tablet;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/*Get ready to server orders from firebase and each order
has a button to serve. when serving it cahnges its status
*/


public class ReadytoServeOrders extends AppCompatActivity {

    private RecyclerView mrecycleview;
    private Adapter madapter;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mtablereference;
    FirebaseDatabase mFirebaseDatabase2;
    DatabaseReference mtablereference2;
    Order o;
    final ArrayList<Tablet> tablets = new ArrayList<>();
    final ArrayList<Table> tables = new ArrayList<>();
    final ArrayList<BillStatus> billstatus = new ArrayList<>();
    final ArrayList<Order> serveorder = new ArrayList<>();
    final ArrayList<Assignment> track = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hall_activity_ready_to_serve_orders);
        // Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //myToolbar.setTitle("");
        //   setSupportActionBar(myToolbar);

        mrecycleview = (RecyclerView) findViewById(R.id.list);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mrecycleview.addItemDecoration(itemDecoration);
        mrecycleview.setLayoutManager(new LinearLayoutManager(this));

        o = new Order();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mtablereference = mFirebaseDatabase.getReference("Order");//.child("notification").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mtablereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    o = ds.getValue(Order.class);
                    if (o.getStatus() == 2) {
                        serveorder.add(o);
                    }
                    madapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        madapter = new Adapter(this, 2, tables, tablets, billstatus, serveorder, track);
        mrecycleview.setAdapter(madapter);
        madapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Toast.makeText(ReadytoServeOrders.this, "Single Click on position :" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnFreeClick(int position) {
                Toast.makeText(ReadytoServeOrders.this, "Free Click on position :" + position, Toast.LENGTH_SHORT).show();

            }


            //misleading name 
            //this onclicklistener will change the status of the order to served in 
            //    firebase db
            //
            @Override
            public void OnBookClick(int position) {
                Toast.makeText(ReadytoServeOrders.this, "Served Click on position :" + position, Toast.LENGTH_SHORT).show();
                mFirebaseDatabase2 = FirebaseDatabase.getInstance();
                mtablereference2 = mFirebaseDatabase2.getReference("Order").child(serveorder.get(position).getId());
                Order order = new Order(serveorder.get(position).getTableID(), serveorder.get(position).getId(), 3, serveorder.get(position).getTimestamp());
                mtablereference2.setValue(order);
                //notify adapter
            }

            @Override
            public void OnOccupyClick(int position) {
                Toast.makeText(ReadytoServeOrders.this, "Occupy Click on position :" + position, Toast.LENGTH_SHORT).show();
            }

        });
    }
}
