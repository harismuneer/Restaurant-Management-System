package com.dineout.code.admin;

import com.dineout.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/*Add emplyee

Chef: speiality, name, salary,0
Headchef: login 
waiter: sirf name and salary
hall manger:  login

*/

public class AddEmployeeActivity extends AppCompatActivity {

    EditText name, email, password, specialty, salary;
    Spinner type;
    TextView special;
    Button addButton;
    Long it;
    static ArrayList<Long> eid = new ArrayList<Long>();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    boolean check = false;
    boolean check1 = true;
    ArrayList<Employee> E = new ArrayList<Employee>();
    String e;
    String p;


    private ListView listView;
    DatabaseReference mDatabase;
    IngredientsListAdapter ingredientsListAdapter;
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<IngredientRow> ingredients = new ArrayList<>();
    private ArrayList<String> notifications1 = new ArrayList<>();
    private ArrayList<String> notifications2 = new ArrayList<>();

    int idz = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_add_employee);


        name = (EditText) findViewById(R.id.AddEmployeeName300);
        type = (Spinner) findViewById(R.id.DropDownMenu300);
        email = (EditText) findViewById(R.id.AddEmployeeEmail300);
        password = (EditText) findViewById(R.id.AddEmployeePassword300);
        specialty = (EditText) findViewById(R.id.AddSpeciality300);
        salary = (EditText) findViewById(R.id.AddEmployeeSalary300);
        special = (TextView) findViewById(R.id.specialityLabel300);
        addButton = (Button) findViewById(R.id.AddNewEmployeeButton300);
        type.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedItem = parent.getItemAtPosition(position).toString();
                        if (selectedItem.equals("Chef") || selectedItem.equals("Head Chef")) {
                            specialty.setEnabled(true);
                            specialty.setInputType(InputType.TYPE_CLASS_TEXT);
                            specialty.setFocusable(true);
                            specialty.setFocusableInTouchMode(true);

                            special.setEnabled(true);
                        } else {
                            specialty.setEnabled(false);
                            specialty.setInputType(InputType.TYPE_NULL);
                            specialty.setFocusable(false);

                            special.setEnabled(false);
                        }
                        if (selectedItem.equals("Hall Manager") || selectedItem.equals("Head Chef")) {
                            email.setEnabled(true);
                            email.setInputType(InputType.TYPE_CLASS_TEXT);
                            email.setFocusable(true);
                            email.setFocusableInTouchMode(true);

                            password.setEnabled(true);
                            password.setInputType(InputType.TYPE_CLASS_TEXT);
                            password.setFocusable(true);
                            password.setFocusableInTouchMode(true);
                            check = true;

                            //special.setEnabled(true);
                        } else {
                            email.setEnabled(false);
                            email.setInputType(InputType.TYPE_NULL);
                            email.setFocusable(false);

                            password.setEnabled(false);
                            password.setInputType(InputType.TYPE_NULL);
                            password.setFocusable(false);

                            //special.setEnabled(false);
                        }
                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String specialtyText = "";
                boolean go = true;
                //boolean check=false;
                if (!type.getSelectedItem().toString().equals("Chef")) {
                    specialtyText = "None";
                } else {
                    specialtyText = type.getSelectedItem().toString();
                }

                if (name.getText().toString().length() <= 0) {
                    name.setError("Name is Required");
                    go = false;
                }
                if (email.getText().toString().length() <= 0 && (type.getSelectedItem().toString().equals("Head Chef") || type.getSelectedItem().toString().equals("Hall Manager"))) {
                    email.setError("Email Address is Required");
                    go = false;
                    //check= true;
                }
                if (password.getText().toString().length() <= 0 && (type.getSelectedItem().toString().equals("Head Chef") || type.getSelectedItem().toString().equals("Hall Manager"))) {
                    password.setError("Password is Required");
                    go = false;
                }
                if (salary.getText().toString().length() > 0 && Integer.parseInt(salary.getText().toString()) < 1) {
                    salary.setError("Salary must be greater than 0");
                    go = false;
                }
                if (specialty.getText().toString().length() <= 0 && type.getSelectedItem().toString().equals("Chef")) {
                    specialty.setError("Specialty is Required");
                    go = false;
                }
                if (go) {

                    e = email.getText().toString();
                    p = password.getText().toString();
                    // String selectedItem = type.getItemAtPosition(position).toString();
                    //check1=true;
                    if (check) {


                        firebaseAuth.createUserWithEmailAndPassword(e, p).addOnCompleteListener(AddEmployeeActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(AddEmployeeActivity.this, "User is Registered", Toast.LENGTH_SHORT).show();

                                } else {
                                    check1 = false;
                                    Toast.makeText(AddEmployeeActivity.this, "Error " + task.getException().toString(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    }
                    if (check1) {
                        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference();
                        mDatabase1.child("Ids").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                                if (dataSnapshot != null) {
                                    Toast.makeText(AddEmployeeActivity.this, "IDZ=" + String.valueOf(dataSnapshot.getValue(Long.class)), Toast.LENGTH_SHORT).show();

                                    eid.add(dataSnapshot.getValue(Long.class));

                                }

                                Toast.makeText(AddEmployeeActivity.this, "Value of it  " + eid.get(0), Toast.LENGTH_SHORT).show();

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


                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                        Query ref = null;
                        mDatabase.child("Employee").addListenerForSingleValueEvent(new ValueEventListener() {
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Toast.makeText(AddEmployeeActivity.this, "First in ", Toast.LENGTH_SHORT).show();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                if (E.size() == 0) {
                                    Toast.makeText(AddEmployeeActivity.this, "First id ", Toast.LENGTH_SHORT).show();
                                    ref.child("Ids").child("Employeeid").setValue(1);
                                }


                                Toast.makeText(AddEmployeeActivity.this, "it value= " + eid.get(0), Toast.LENGTH_SHORT).show();
                                Employee e1 = new Employee(String.valueOf(eid.get(0)), name.getText().toString(), e, p, specialty.getText().toString(), salary.getText().toString(), type.getSelectedItem().toString());

                                ref.child("Employee").child(String.valueOf(eid.get(0))).setValue(e1);
                                ref.child("Ids").child("Employeeid").setValue(eid.get(0) + 1);
                                Toast.makeText(AddEmployeeActivity.this, "Employee Added Successfully", Toast.LENGTH_SHORT).show();
                                eid.clear();
                                Intent i = new Intent(AddEmployeeActivity.this, AdminPanelActivity.class);
                                startActivity(i);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }


                        });
                        mDatabase.child("Employee").addChildEventListener(new ChildEventListener() {
                            public void onChildAdded(DataSnapshot dataSnapshot, String previousKey) {
                                Employee item = dataSnapshot.getValue(Employee.class);
                                E.add(item);

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
        });


    }
}


