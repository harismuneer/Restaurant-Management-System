package com.dineout.code.admin;

import com.dineout.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddTabletActivity extends AppCompatActivity {

    private Spinner spinner1;
    EditText txtTabletID;
    Spinner spnStatus;
    static ArrayList<Long> eid = new ArrayList<Long>();
    ArrayList<Tablet> t = new ArrayList<Tablet>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_add_tablet);
        addItemsOnSpinner();
    }

    public void addItemsOnSpinner() {

        spinner1 = (Spinner) findViewById(R.id.TabletStatusDropDown300);
        List<String> list = new ArrayList<String>();
        list.add("In Use");
        list.add("Not Use");
        list.add("Not Working");
        list.add("Broken");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }

    public void btnBlick(View v) {

        spnStatus = (Spinner) findViewById(R.id.TabletStatusDropDown300);


        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference();
        mDatabase1.child("Ids").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                if (dataSnapshot != null) {

                    if (dataSnapshot != null) {
                        Toast.makeText(AddTabletActivity.this, "IDZ=" + String.valueOf(dataSnapshot.getValue(Long.class)), Toast.LENGTH_SHORT).show();

                        eid.add(dataSnapshot.getValue(Long.class));

                    }
                }

                //    Toast.makeText(AddTableActivity.this, "Value of it  "+ eid.get(1), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
        if (t.size() == 0) {

            ref1.child("Ids").child("Tabletid").setValue(1);
        }


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Tablet").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {

                Tablet table = new Tablet(String.valueOf(eid.get(2)), spnStatus.getSelectedItem().toString());
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                ref.child("Tablet").child(String.valueOf(eid.get(2))).setValue(table);
                ref.child("Ids").child("Tabletid").setValue(eid.get(2) + 1);
                Toast.makeText(AddTabletActivity.this, "Tablet has been added successfully", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(AddTabletActivity.this, AdminPanelActivity.class);
                eid.clear();
                startActivity(it);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

        mDatabase.child("Tablet").addChildEventListener(new ChildEventListener() {
            public void onChildAdded(DataSnapshot dataSnapshot, String previousKey) {
                Tablet item = dataSnapshot.getValue(Tablet.class);
                t.add(item);

            }

            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

