package com.dineout.code.hall;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.dineout.code.hall.DB.Assignment;
import com.dineout.code.hall.DB.BillStatus;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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

        mrecycleview = (RecyclerView) findViewById(R.id.list);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mrecycleview.addItemDecoration(itemDecoration);
        mrecycleview.setLayoutManager(new LinearLayoutManager(this));

        o= new Order();
        r= new Receipt();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mtablereference = mFirebaseDatabase.getReference("Order");//.child("notification").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mtablereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                serveorder.clear();
                billstatus.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    o=ds.getValue(Order.class);
                    if(o.getStatus() == 3 && !serveorder.contains(o)) {
                        serveorder.add(o);
                    }
                }

                mFirebaseDatabase2 = FirebaseDatabase.getInstance();
                mFirebaseDatabase2.getReference("Receipt").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            r = ds.getValue(Receipt.class);
                            if (r.getPaid() == 0) {
                                for(int i=0;i<serveorder.size();i++)
                                {
                                    if(r.getOrderid().compareTo(serveorder.get(i).getId())==0) {
                                        bs = new BillStatus();
                                        bs.setTableID(o.getTableID());
                                        bs.setStatus(o.getTimestamp());
                                        bs.setOrderID(r.getOrderid());
                                        bs.setAmount(r.getTotalamount());
                                        billstatus.add(bs);
                                    }
                                }
                            }
                        }

                        madapter = new Adapter(ReceivePayment.this, 1, tables, tablets, billstatus, serveorder, track);
                        mrecycleview.setAdapter(madapter);
                        madapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
                            @Override
                            public void OnItemClick(int position) {

                            }

                            @Override
                            public void OnFreeClick(int position) {
                             //   Toast.makeText(ReceivePayment.this, "Free Click on position :"+position,Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void OnBookClick(int position) {
                                //Toast.makeText(ReceivePayment.this, "Paid Click on position :"+position,Toast.LENGTH_SHORT).show();
                                mFirebaseDatabase3 = FirebaseDatabase.getInstance();
                                mtablereference3 = mFirebaseDatabase3.getReference("Order").child(billstatus.get(position).getOrderID());
                                Order order=new Order(billstatus.get(position).getTableID(), billstatus.get(position).getOrderID(),4, billstatus.get(position).getStatus());
                                mtablereference3.setValue(order);

                                mFirebaseDatabase4 = FirebaseDatabase.getInstance();
                                mtablereference4 = mFirebaseDatabase4.getReference("Receipt").child(billstatus.get(position).getOrderID());
                                Receipt receipt=new Receipt(billstatus.get(position).getOrderID(), 1, billstatus.get(position).getAmount());
                                mtablereference4.setValue(receipt);
                            }

                            @Override
                            public void OnOccupyClick(int position) {
                              //  Toast.makeText(ReceivePayment.this, "Occupy Click on position :"+position,Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
