package com.dineout.code.hall;

import android.content.Context;
import android.content.Intent;
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



/*View all tables
on each table can be booked(phone), free, occupied


*/


public class Tables extends AppCompatActivity {
    private RecyclerView mrecycleview;
    private Adapter madapter;
    FirebaseDatabase mFirebaseDatabase;
    FirebaseDatabase mFirebaseDatabase2;
    FirebaseDatabase mFirebaseDatabase3;
    FirebaseDatabase mFirebaseDatabase4;
    FirebaseDatabase mFirebaseDatabase5;
    DatabaseReference mtablereference;
    DatabaseReference mtablereference2;
    DatabaseReference mtablereference3;
    DatabaseReference mtablereference4;
    DatabaseReference mtablereference5;
    Assignment a;
    Table table;
    final ArrayList<Tablet> tablets = new ArrayList<>();
    final ArrayList<Table> tables = new ArrayList<>();
    final ArrayList<BillStatus> billstatus = new ArrayList<>();
    final ArrayList<Order> serveorder = new ArrayList<>();
    final ArrayList<Assignment> track = new ArrayList<>();
    Context c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hall_activity_tables);

        //    Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //   myToolbar.setTitle("");
        //  setSupportActionBar(myToolbar);


        //showing tables from firebase
        mrecycleview = (RecyclerView) findViewById(R.id.list);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mrecycleview.addItemDecoration(itemDecoration);
        mrecycleview.setLayoutManager(new LinearLayoutManager(this));
        table = new Table();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mtablereference = mFirebaseDatabase.getReference("Table");//.child("notification").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mtablereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tables.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    table = ds.getValue(Table.class);
                    tables.add(table);
                    madapter.notifyDataSetChanged();
                    System.out.println(table.getStatus());
                    System.out.println(tables.size());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        a = new Assignment();
        mFirebaseDatabase3 = FirebaseDatabase.getInstance();
        mtablereference3 = mFirebaseDatabase3.getReference("Assignment");//.child("notification").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mtablereference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                track.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    a = ds.getValue(Assignment.class);
                    track.add(a);
                    madapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        System.out.println(tables.size());
        madapter = new Adapter(c, 0, tables, tablets, billstatus, serveorder, track);
        mrecycleview.setAdapter(madapter);
        madapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            //showing position only
            @Override
            public void OnItemClick(int position) {
                Toast.makeText(Tables.this, "Single Click on position :" + position, Toast.LENGTH_SHORT).show();
            }

            //Free tables and return to managerinterface

            @Override
            public void OnFreeClick(final int position) {
                Toast.makeText(Tables.this, "Free Click on position :" + position, Toast.LENGTH_SHORT).show();
                mFirebaseDatabase2 = FirebaseDatabase.getInstance();
                mtablereference2 = mFirebaseDatabase2.getReference("Table").child(tables.get(position).getTableID());
                Table t = new Table(tables.get(position).getTableID(), "Free", tables.get(position).getCapacity());
                mtablereference2.setValue(t);
                System.out.println("Size" + track.size());
                System.out.println("TableID" + tables.get(position).getTableID());
                for (int i = 0; i < track.size(); i++) {
                    if (track.get(i).getTableID().equals(tables.get(position).getTableID())) {

                        String eid = track.get(i).getEmployeeid();
                        String tid = track.get(i).getTabletID();
                        System.out.println("TabletID" + tid);
                        System.out.println("eID" + eid);
                        System.out.println("TableID" + tables.get(position).getTableID());
                        mFirebaseDatabase4 = FirebaseDatabase.getInstance();
                        mtablereference4 = mFirebaseDatabase4.getReference("Assignment").child(tables.get(position).getTableID());
                        Assignment b = new Assignment(eid, tables.get(position).getTableID(), "null");
                        mtablereference4.setValue(b);


                        mFirebaseDatabase5 = FirebaseDatabase.getInstance();
                        mtablereference5 = mFirebaseDatabase5.getReference("Tablet").child(tid);
                        Tablet t2 = new Tablet(tid, "Available");
                        mtablereference5.setValue(t2);

                    }
                }
                Intent myIntent = new Intent(Tables.this, ManagerInterface.class);
                startActivity(myIntent);
            }

            @Override
            //ye table book kardaitay heon and opoen tablet actity fo assigning tablet
            //phone pe agar book karwana hai
            public void OnBookClick(int position) {
                Toast.makeText(Tables.this, "Book Click on position :" + position, Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(Tables.this, Tablets.class);
                mFirebaseDatabase2 = FirebaseDatabase.getInstance();
                mtablereference2 = mFirebaseDatabase2.getReference("Table").child(tables.get(position).getTableID());
                myIntent.putExtra("TableID", tables.get(position).getTableID());
                Table t = new Table(tables.get(position).getTableID(), "Booked", tables.get(position).getCapacity());
                mtablereference2.setValue(t);
                startActivity(myIntent);
            }


            ////ye table book kardaitay heon and opoen tablet actity fo assigning tablet
            //
            @Override
            public void OnOccupyClick(int position) {
                Toast.makeText(Tables.this, "Occupy Click on position :" + position, Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(Tables.this, Tablets.class);
                mFirebaseDatabase2 = FirebaseDatabase.getInstance();
                mtablereference2 = mFirebaseDatabase2.getReference("Table").child(tables.get(position).getTableID());
                myIntent.putExtra("TableID", tables.get(position).getTableID());
                Table t = new Table(tables.get(position).getTableID(), "Occupied", tables.get(position).getCapacity());
                mtablereference2.setValue(t);
                startActivity(myIntent);
            }
        });
    }


}
