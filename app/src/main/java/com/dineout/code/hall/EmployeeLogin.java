package com.dineout.code.hall;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dineout.code.hall.DB.Assignment;
import com.dineout.code.hall.DB.BillStatus;
import com.dineout.code.hall.DB.Order;
import com.dineout.code.hall.DB.OrderDetails;
import com.dineout.code.hall.DB.Table;
import com.dineout.code.hall.DB.Tablet;
import com.dineout.code.hall.DB.Receipt;
import com.dineout.code.hall.DB.Employee;

import com.dineout.R;



import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeeLogin extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mtablereference;
    Employee emp;
    String e;
    String p;
    AutoCompleteTextView email;
    EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hall_activity_employee_login);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("");
        //setSupportActionBar(myToolbar);

        email = (AutoCompleteTextView) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);

        Button button = (Button) findViewById(R.id.LogInbtn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {

                mFirebaseDatabase = FirebaseDatabase.getInstance();
                mtablereference = mFirebaseDatabase.getReference("Employee");
                mtablereference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            emp=ds.getValue(Employee.class);

                            if(emp.getType().equals("Hall Manager"))
                            {
                                e=emp.getEmail();
                                p=emp.getPassword();

                                if(e.equals(email.getText().toString()) && p.equals(pass.getText().toString()))
                                {
                                    Intent myIntent = new Intent(EmployeeLogin.this, ManagerInterface.class);
                                    startActivity(myIntent);
                                }
                                else
                                {
                                    Toast.makeText(EmployeeLogin.this, "Incorrect Login or Password",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




            }
        });
    }
}
