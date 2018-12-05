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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/*Spinner to add table
add capacity of table
set state of the table
*/

public class AddTableActivity extends AppCompatActivity {

    private Spinner spinner1;
    ArrayList<Table> t = new ArrayList<Table>();
    Spinner spnStatus;
    static ArrayList<Long> eid = new ArrayList<Long>();
    EditText txtCapacity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_add_table);
        spnStatus = (Spinner) findViewById(R.id.TableStatusDropDown300);
        txtCapacity = (EditText) findViewById(R.id.AddTableCapacity300);
        addItemsOnSpinner();
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner() {

        spinner1 = (Spinner) findViewById(R.id.TableStatusDropDown300);
        List<String> list = new ArrayList<String>();
        list.add("Free");
        list.add("Booked");
        list.add("Occupied");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }

    public void btnBlick(View v) {

        boolean go = true;


        // int capacity = Integer.parseInt(txtCapacity.getText().toString());

        if (txtCapacity.getText().toString().length() <= 0) {
            txtCapacity.setError("Capacity is required");
            go = false;

        }

        //   int i=txtCapacity.getText().toString().length();
        //     int j=Integer.parseInt(txtCapacity.getText().toString());
        if (txtCapacity.getText().toString().length() > 0 && Integer.parseInt(txtCapacity.getText().toString()) <= 0) {
            // Toast.makeText(this, "Capacity should be greater than 0", Toast.LENGTH_SHORT).show();
            txtCapacity.setError("Capacity should be greater than 0");
            go = false;
        }
        if (go) {

            DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference();
            mDatabase1.child("Ids").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                    if (dataSnapshot != null) {

                        if (dataSnapshot != null) {
                            Toast.makeText(AddTableActivity.this, "IDZ=" + String.valueOf(dataSnapshot.getValue(Long.class)), Toast.LENGTH_SHORT).show();

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

                ref1.child("Ids").child("Tableid").setValue(1);
            }


            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            Query ref = null;
            mDatabase.child("Table").addListenerForSingleValueEvent(new ValueEventListener() {
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Table table = new Table(String.valueOf(eid.get(1)), spnStatus.getSelectedItem().toString(), txtCapacity.getText().toString());
                    DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
                    ref1.child("Table").child(String.valueOf(eid.get(1))).setValue(table);
                    ref1.child("Ids").child("Tableid").setValue(eid.get(1) + 1);

                    Toast.makeText(AddTableActivity.this, "Table has been added Successfully", Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(AddTableActivity.this, AdminPanelActivity.class);
                    eid.clear();
                    startActivity(it);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }


            });

            mDatabase.child("Table").addChildEventListener(new ChildEventListener() {
                public void onChildAdded(DataSnapshot dataSnapshot, String previousKey) {
                    Table item = dataSnapshot.getValue(Table.class);
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

}

