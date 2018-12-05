package com.dineout.code.hall;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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

public class ManagerInterface extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference memployeereference;
    FirebaseDatabase mFirebaseDatabase2;
    DatabaseReference mtablereference2;
    FirebaseDatabase mFirebaseDatabase3;
    DatabaseReference mAssignmentreference;
    Employee emp;
    Table t;
    final ArrayList<Table> Tables = new ArrayList<>();
    final ArrayList<Assignment> track = new ArrayList<>();
    final ArrayList<Employee> employees = new ArrayList<>();
    int servers=0;
    int tables=0;
    Button button8;
    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hall_activity_manager_interface);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("");
        setSupportActionBar(myToolbar);

        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {
                Intent myIntent = new Intent(ManagerInterface.this, Tables.class);
                startActivity(myIntent);
            }
        });

        Button button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {
                Intent myIntent = new Intent(ManagerInterface.this,ReceivePayment.class);
                startActivity(myIntent);
            }
        });

        Button button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {
                Toast.makeText(ManagerInterface.this, "Notification sent to Reporting Module",Toast.LENGTH_SHORT).show();
            }
        });

        Button button7 = (Button) findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {
                Intent myIntent = new Intent(ManagerInterface.this, ReadytoServeOrders.class);
                startActivity(myIntent);
            }
        });

        button8 = (Button) findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {
                Toast.makeText(ManagerInterface.this, "Available Servers Assigned to Tables",Toast.LENGTH_SHORT).show();

                mFirebaseDatabase = FirebaseDatabase.getInstance();
                memployeereference = mFirebaseDatabase.getReference("Employee");
                emp =new Employee();
                memployeereference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            emp=ds.getValue(Employee.class);

                            if(emp.getType().equals("Waiter"))
                            {
                                servers++;
                                employees.add(emp);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                mFirebaseDatabase3 = FirebaseDatabase.getInstance();

                mFirebaseDatabase2 = FirebaseDatabase.getInstance();
                mtablereference2 = mFirebaseDatabase2.getReference("Table");
                t =new Table();
                mtablereference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            t=ds.getValue(Table.class);

                                tables++;
                                Tables.add(t);
                        }
                        int j = servers;
                        int k=0;
                        for(int i=0;i<Tables.size();i++) {

                            if(j != 0) {
                                mAssignmentreference = mFirebaseDatabase3.getReference("Assignment").child(Tables.get(i).getTableID());
                                String employeeID=employees.get(k).getId();
                                Assignment a = new Assignment(employeeID,Tables.get(i).getTableID(), "null");
                                mAssignmentreference.setValue(a);
                                j--;
                                k++;
                            }
                            else
                            {
                                if((Tables.size()-i) >= employees.size())
                                {
                                    j= employees.size();
                                    k = 0;
                                }
                                else
                                {
                                    j = (Tables.size()-i);
                                    k = 0;
                                }
                            }
                        }
                        button8.setEnabled(false);
                        button4.setEnabled(true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        Button button9 = (Button) findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {
                Intent myIntent = new Intent(ManagerInterface.this, Tracking.class);
                startActivity(myIntent);
            }
        });

      //  Button button10 = (Button) findViewById(R.id.button10);
       // button10.setOnClickListener(new View.OnClickListener() {
       //     public void onClick(View V) {
       //         Toast.makeText(ManagerInterface.this, "Reordered Food",Toast.LENGTH_SHORT).show();
       //     }
       // }

        Button button11 = (Button) findViewById(R.id.button11);
        button11.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {
                button8.setEnabled(true);
                for(int i=0;i<Tables.size();i++) {
                    DatabaseReference del = FirebaseDatabase.getInstance().getReference("Assignment").child(Tables.get(i+1).getTableID());
                    del.removeValue();
                }
                Intent myIntent = new Intent(ManagerInterface.this, SelectUser.class);
                startActivity(myIntent);
            }
        });

        Button button12 = (Button) findViewById(R.id.button12);
        button12.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {

                Intent myIntent = new Intent(ManagerInterface.this, ViewCompletedOrders.class);
                startActivity(myIntent);
            }
        });

    }
}
