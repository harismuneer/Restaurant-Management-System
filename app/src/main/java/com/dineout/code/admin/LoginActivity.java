package com.dineout.code.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dineout.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText txtEmailLogin;
    private EditText txtPwd;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(); //firebase database ka object get kia
    DatabaseReference databaseReference = firebaseDatabase.getReference("Employee"); //table lia
    
    /*
    login screen for chef, hall manager and admin
    authentication from firebase and rediect to corresponding interface
    

    chef ka code in kitchen module
    abhi toast laga hwa
    add intent for chef(in kitchen module)


    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_login);
        txtEmailLogin = (EditText) findViewById(R.id.EmployeeEmail301);
        txtPwd = (EditText) findViewById(R.id.EmployeePassword301);
        firebaseAuth = FirebaseAuth.getInstance();
    }


    public void btnUserLogin_Click(View v) {
        boolean go = true;
        if (txtEmailLogin.getText().toString().matches("")) {
            txtEmailLogin.setError("Email is required");
            // Toast.makeText(this, "You did not enter a Email Address", Toast.LENGTH_SHORT).show();
            go = false;

        }
        if (txtPwd.getText().toString().matches("")) {
            txtPwd.setError("Password is Reqiuired");
            // Toast.makeText(this, "You did not enter a Password", Toast.LENGTH_SHORT).show();
            go = false;
        }
        if (go) {
            final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Please wait...", "Proccessing...", true);

            (firebaseAuth.signInWithEmailAndPassword(txtEmailLogin.getText().toString(), txtPwd.getText().toString()))
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();

                            if (task.isSuccessful()) {
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    Employee employee;

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                            employee = dsp.getValue(Employee.class);
                                            if (employee != null) {
                                                if (employee.getType().equals("Hall Manager") && txtEmailLogin.getText().toString().equals(employee.getEmail())) {
                                                    databaseReference.removeEventListener(this);
                                                    Intent i = new Intent(LoginActivity.this, com.dineout.code.hall.ManagerInterface.class);
                                                    i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                                    startActivity(i);
                                                }
                                            }
                                            if (employee != null) {
                                                if (employee.getType().equals("Head Chef") && txtEmailLogin.getText().toString().equals(employee.getEmail())) {
                                                    databaseReference.removeEventListener(this);

                                                    Intent i = new Intent(LoginActivity.this, com.dineout.code.kitchen.MainActivity.class);
                                                    i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                                    startActivity(i);
                                                }
                                            }

                                            if (txtEmailLogin.getText().toString().equals("admin@gmail.com")) {
                                                databaseReference.removeEventListener(this);
                                                Intent i = new Intent(LoginActivity.this, AdminPanelActivity.class);
                                                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                                startActivity(i);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                //SyncStateContract.Helpers.getAds(SignIn.this);
                                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                                //Intent i = new Intent(LoginActivity.this, com.example.khalid.lettuceeat.ManagerInterface.class);
                                //Session.setID(txtEmailLogin.getText().toString());
                                //i.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
                                //startActivity(i);
                                //finish();
                            } else {
                                Log.e("ERROR", task.getException().toString());
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                            }
                        }
                    });
        }
    }

}
