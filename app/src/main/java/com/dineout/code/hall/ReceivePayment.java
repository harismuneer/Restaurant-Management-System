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
import com.dineout.code.hall.DB.Receipt;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//ramsha had to add new code

public class ReceivePayment extends AppCompatActivity {
    private RecyclerView mrecycleview;
    private Adapter madapter;
    FirebaseDatabase mFirebaseDatabase;
    FirebaseDatabase mFirebaseDatabase2;
    DatabaseReference mtablereference;
    DatabaseReference mtablereference2;
    FirebaseDatabase mFirebaseDatabase3;
    DatabaseReference mtablereference3;
    FirebaseDatabase mFirebaseDatabase4;
    DatabaseReference mtablereference4;
    Order o;
    Receipt r;
    BillStatus bs;
    final ArrayList<Tablet> tablets = new ArrayList<>();
    final ArrayList<Table> tables = new ArrayList<>();
    final ArrayList<BillStatus> billstatus = new ArrayList<>();
    final ArrayList<Order> serveorder = new ArrayList<>();
    final ArrayList<Assignment> track = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hall_activity_receive_payment);
        //   Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //myToolbar.setTitle("");
        //   setSupportActionBar(myToolbar);


        mrecycleview = (RecyclerView) findViewById(R.id.list);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mrecycleview.addItemDecoration(itemDecoration);
        mrecycleview.setLayoutManager(new LinearLayoutManager(this));

        System.out.println("entered");
        o = new Order();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mtablereference = mFirebaseDatabase.getReference("Order");//.child("notification").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mtablereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    o = ds.getValue(Order.class);
                    if (o.getStatus() == 3) {
                        System.out.println("entered2");
                        serveorder.add(o);
                        madapter.notifyDataSetChanged();
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        r = new Receipt();

        mFirebaseDatabase2 = FirebaseDatabase.getInstance();
        mtablereference2 = mFirebaseDatabase2.getReference("Receipt");//.child("notification").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mtablereference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    System.out.println("hmm" + serveorder.size());
                    r = ds.getValue(Receipt.class);
                    if (r.getPaid() == 0) {
                        System.out.println("entered3");
                        for (int i = 0; i < serveorder.size(); i++) {
                            if (serveorder.get(i).getId().equals(r.getOrderid())) {
                                System.out.println("found");
                                bs = new BillStatus();
                                bs.setTableID(serveorder.get(i).getTableID());
                                bs.setStatus(serveorder.get(i).getTimestamp());
                                bs.setOrderID(r.getOrderid());
                                bs.setAmount(r.getTotalamount());
                                billstatus.add(bs);

                                //break;
                            }
                        }
                        madapter.notifyDataSetChanged();
                    }
                    //

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        System.out.println("entered4");
        madapter = new Adapter(this, 1, tables, tablets, billstatus, serveorder, track);
        mrecycleview.setAdapter(madapter);
        madapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Toast.makeText(ReceivePayment.this, "Single Click on position :" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnFreeClick(int position) {
                Toast.makeText(ReceivePayment.this, "Free Click on position :" + position, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void OnBookClick(int position) {
                Toast.makeText(ReceivePayment.this, "Paid Click on position :" + position, Toast.LENGTH_SHORT).show();
                //  mFirebaseDatabase3 = FirebaseDatabase.getInstance();
                //  mtablereference3 = mFirebaseDatabase3.getReference("Order").child(billstatus.get(position).getOrderID());
                //  Order order=new Order(billstatus.get(position).getTableID(), billstatus.get(position).getOrderID(),4, billstatus.get(position).getStatus());
                //  mtablereference3.setValue(order);

                //   mFirebaseDatabase4 = FirebaseDatabase.getInstance();
                //   mtablereference4 = mFirebaseDatabase4.getReference("Receipt").child(billstatus.get(position).getOrderID());
                //  Receipt receipt=new Receipt(billstatus.get(position).getOrderID(), 1, billstatus.get(position).getAmount());
                //  mtablereference4.setValue(receipt);
            }

            @Override
            public void OnOccupyClick(int position) {
                Toast.makeText(ReceivePayment.this, "Occupy Click on position :" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
