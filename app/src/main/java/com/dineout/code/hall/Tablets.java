package com.dineout.code.hall;

import android.content.Intent;
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

public class Tablets extends AppCompatActivity {

    private RecyclerView mrecycleview;
    private Adapter madapter;
    FirebaseDatabase mFirebaseDatabase;
    FirebaseDatabase mFirebaseDatabase2;
    DatabaseReference mtablereference;
    Tablet tablet;
    final ArrayList<Tablet> tablets = new ArrayList<>();
    final ArrayList<Table> tables = new ArrayList<>();
    final ArrayList<BillStatus> billstatus = new ArrayList<>();
    final ArrayList<Order> serveorder = new ArrayList<>();
    final ArrayList<Assignment> track = new ArrayList<>();
    final ArrayList<String> ServerID = new ArrayList<>();
    DatabaseReference mtablereference2;
    DatabaseReference mtablereference3;
    DatabaseReference mtablereference4;
    Assignment a2;
    String TableID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hall_activity_tablets);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("");
        //setSupportActionBar(myToolbar);

        mrecycleview = (RecyclerView) findViewById(R.id.list);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mrecycleview.addItemDecoration(itemDecoration);
        mrecycleview.setLayoutManager(new LinearLayoutManager(this));

        tablet= new Tablet();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mtablereference = mFirebaseDatabase.getReference("Tablet");//.child("notification").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mtablereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tablets.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    tablet=ds.getValue(Tablet.class);
                    tablets.add(tablet);

                    System.out.println(tablet.getStatus());
                    System.out.println(tablets.size());
                    a2 = new Assignment();
                    mtablereference3 = FirebaseDatabase.getInstance().getReference("Assignment");
                    mtablereference3.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ServerID.clear();
                            for(DataSnapshot ds: dataSnapshot.getChildren()) {
                                a2=ds.getValue(Assignment.class);
                                if (a2.getTableID().equals(TableID))
                                {
                                    ServerID.add(a2.getEmployeeid());
                                    System.out.println(ServerID);
                                    System.out.println(TableID);
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                madapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        madapter = new Adapter(this, 4, tables,tablets, billstatus, serveorder, track);
        mrecycleview.setAdapter(madapter);
        madapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Toast.makeText(Tablets.this, "Single Click on position :"+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnFreeClick(int position) {
                Toast.makeText(Tablets.this, "Free Click on position :"+position,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void OnBookClick(final int position) {
                Toast.makeText(Tablets.this, "Assigned Click on position :"+position,Toast.LENGTH_SHORT).show();
                TableID=getIntent().getExtras().getString("TableID");

                String TabletID=tablets.get(position).getTabletID();
                System.out.println("sid"+ServerID);
                mFirebaseDatabase2 = FirebaseDatabase.getInstance();
                mtablereference2 = mFirebaseDatabase2.getReference("Assignment").child(TableID);
                if(ServerID.size() == 0 )
                {
                    ServerID.add("1");
                }
                else if(ServerID.get(0).equals(null))
                {
                    ServerID.add("1");
                }
                System.out.print("server id ka size "+ServerID.size());
                Assignment a = new Assignment(ServerID.get(0),TableID,tablets.get(position).getTabletID());
                mtablereference2.setValue(a);

                mtablereference4 = FirebaseDatabase.getInstance().getReference("Tablet").child(tablets.get(position).getTabletID());
                Tablet t = new Tablet(tablets.get(position).getTabletID(),"In Use");
                mtablereference4.setValue(t);
                Intent myIntent = new Intent(Tablets.this, ManagerInterface.class);
                startActivity(myIntent);
            }

            @Override
            public void OnOccupyClick(int position) {
                Toast.makeText(Tablets.this, "Occupy Click on position :"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
