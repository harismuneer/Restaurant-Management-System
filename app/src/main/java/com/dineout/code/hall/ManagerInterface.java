package com.dineout.code.hall;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dineout.R;

import com.dineout.code.hall.DB.Table;
import com.dineout.code.hall.DB.Assignment;
import com.dineout.code.hall.DB.Employee;

import com.dineout.code.admin.LoginActivity;


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

    int servers = 0;
    int tables = 0;
    Button button8;
    Button button4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hall_activity_manager_interface);
        //  Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        // myToolbar.setTitle("");
//        setSupportActionBar(myToolbar);


        //view tables and assign tablets
        button4 = (Button) findViewById(R.id.ManagerInterfacebutton204);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {
                Intent myIntent = new Intent(ManagerInterface.this, Tables.class);
                startActivity(myIntent);
            }
        });


        //reveive payments for served orders
        Button button5 = (Button) findViewById(R.id.ManagerInterfacebutton205);
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {
                Intent myIntent = new Intent(ManagerInterface.this, ReceivePayment.class);
                startActivity(myIntent);
            }
        });


        //reporting module
        Button button6 = (Button) findViewById(R.id.ManagerInterfacebutton206);
        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {
                //kuch ho tou hai nae raha idhar :/
                Toast.makeText(ManagerInterface.this, "Notification sent to Reporting Module", Toast.LENGTH_SHORT).show();
            }
        });


        //ready to serve orders
        Button button7 = (Button) findViewById(R.id.ManagerInterfacebutton207);
        button7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {
                Intent myIntent = new Intent(ManagerInterface.this, ReadytoServeOrders.class);
                startActivity(myIntent);
            }
        });


        //assigning waiters to tables
        button8 = (Button) findViewById(R.id.ManagerInterfacebutton208);
        button8.setOnClickListener(new View.OnClickListener() {


            //get all the waiters from firebase
            //and assing to tables
            public void onClick(View V) {
                Toast.makeText(ManagerInterface.this, "Available Servers Assigned to Tables", Toast.LENGTH_SHORT).show();

                mFirebaseDatabase = FirebaseDatabase.getInstance();
                memployeereference = mFirebaseDatabase.getReference("Employee");
                emp = new Employee();
                memployeereference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            emp = ds.getValue(Employee.class);

                            if (emp.getType().equals("Waiter")) {
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
                t = new Table();
                mtablereference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            t = ds.getValue(Table.class);

                            tables++;
                            Tables.add(t);
                        }
                        int j = servers;
                        int k = 0;
                        for (int i = 0; i < Tables.size(); i++) {

                            if (j != 0) {
                                mAssignmentreference = mFirebaseDatabase3.getReference("Assignment").child(Tables.get(i).getTableID());
                                String employeeID = employees.get(k).getId();
                                Assignment a = new Assignment(employeeID, Tables.get(i).getTableID(), "null");
                                mAssignmentreference.setValue(a);
                                j--;
                                k++;
                            } else {
                                if ((Tables.size() - i) >= employees.size()) {
                                    j = employees.size();
                                    k = 0;
                                } else {
                                    j = (Tables.size() - i);
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



        /*traking which table assignd to which tablet and waiter*/
        Button button9 = (Button) findViewById(R.id.ManagerInterfacebutton209);
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


        //paid orders list
        Button button12 = (Button) findViewById(R.id.ManagerInterfacebutton212);
        button12.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {

                Intent myIntent = new Intent(ManagerInterface.this, ViewCompletedOrders.class);
                startActivity(myIntent);
            }
        });

    }


    //------------------------options menu for Logout-------------------------------


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            button8.setEnabled(true);
            for (int i = 1; i < Tables.size(); i++) {
                DatabaseReference del = FirebaseDatabase.getInstance().getReference("Assignment").child(Tables.get(i).getTableID());
                del.removeValue();
            }
            Intent intent;
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
